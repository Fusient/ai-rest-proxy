package com.ijson.rest.proxy.util;

import com.ijson.rest.proxy.annotation.CDATA;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;

/**
 * 输出xml和解析xml的工具类
 */
@Slf4j
public class XmlUtil {

    private static final String PREFIX_CDATA = "<![CDATA[";
    private static final String SUFFIX_CDATA = "]]>";
    private static final String DEFAULT_CHARSET = "UTF-8";

    // 线程安全的单例XStream实例
    private static final XStream XSTREAM_WITH_CDATA = initXstream(true);
    private static final XStream XSTREAM_WITHOUT_CDATA = initXstream(false);

    /**
     * 初始化XStream
     * 可支持某一字段可以加入CDATA标签
     * 如果需要某一字段使用原文
     * <code> 就需要在String类型的text的头加上&quot;&lt;![CDATA[&quot;和结尾处加上&quot;]]&gt;&quot;标签， </code>
     * 以供XStream输出时进行识别
     *
     * @param isAddCdata 是否支持CDATA标签
     * @return XStream instance
     */

    public static XStream initXstream(boolean isAddCdata) {
        XStream xstream;
        if (isAddCdata) {
            xstream = new XStream(
                    new XppDriver() {
                        @Override
                        public HierarchicalStreamWriter createWriter(Writer out) {
                            return new PrettyPrintWriter(out) {
                                // 修复__问题
                                @Override
                                public String encodeNode(String name) {
                                    return name;
                                }

                                @Override
                                protected void writeText(QuickWriter writer, String text) {
                                    if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {
                                        writer.write(text);
                                    } else {
                                        super.writeText(writer, text);
                                    }
                                }
                            };
                        }
                    });
        } else {
            xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")));
        }

        // 添加安全配置
        configureXStreamSecurity(xstream);

        return xstream;
    }

    /**
     * 配置XStream安全设置
     *
     * @param xstream XStream实例
     */
    private static void configureXStreamSecurity(XStream xstream) {
        // 设置默认安全配置
        XStream.setupDefaultSecurity(xstream);

        // 允许特定包下的类
        xstream.allowTypesByWildcard(new String[] {
                "com.ijson.rest.proxy.example.model.**",
                "com.ijson.rest.proxy.model.**",
                "java.lang.**",
                "java.util.**"
        });

        // 允许基本类型
        xstream.allowTypeHierarchy(Object.class);
    }

    /**
     * java 转换成xml
     *
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj) {
        // 使用线程安全的单例实例
        XStream xstream = XSTREAM_WITH_CDATA;

        Class tClass = obj.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(CDATA.class) != null) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) != null) {
                        field.set(obj, PREFIX_CDATA + field.get(obj) + SUFFIX_CDATA);
                    }
                } catch (IllegalAccessException e) {
                    log.error("CDATA处理失败", e);
                }
            }
        }

        // 同步处理注解，确保线程安全
        synchronized (xstream) {
            xstream.processAnnotations(obj.getClass());
            return xstream.toXML(obj);
        }
    }

    /**
     * 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     * 将传入xml文本转换成Java对象
     *
     * @param <T>    返回对象的类型
     * @param xmlStr 要转成对象的xml
     * @param cls    xml对应的class类
     * @return T xml对应的class类的实例对象
     */

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        // 使用线程安全的单例实例
        XStream xstream = XSTREAM_WITH_CDATA;

        // 同步处理注解，确保线程安全
        synchronized (xstream) {
            xstream.processAnnotations(cls);
            T obj = (T) xstream.fromXML(xmlStr);
            return obj;
        }
    }

    /**
     * 写到xml文件中去
     *
     * @param obj      对象
     * @param absPath  绝对路径
     * @param fileName 文件名
     * @return boolean
     */
    public static boolean toXmlFile(Object obj, String absPath, String fileName) {
        String strXml = toXml(obj);
        String filePath = absPath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("error:{}", e);
                return false;
            }
        } // end if
        OutputStream ous = null;
        try {
            ous = new FileOutputStream(file);
            ous.write(strXml.getBytes());
            ous.flush();
        } catch (Exception e1) {
            log.error("error:{}", e1);
            return false;
        } finally {
            if (ous != null) {
                try {
                    ous.close();
                } catch (IOException e) {
                    log.error("error:{}", e);
                }
            }
        }
        return true;
    }

    /**
     * 从xml文件读取报文
     * 
     * @param absPath  绝对路径
     * @param fileName 文件名
     * @param cls      类
     * @param <T>      对象
     * @return 转换后的对象
     * @throws Exception 当文件不存在或XML解析失败时抛出异常
     */
    public static <T> T toBeanFromFile(String absPath, String fileName, Class<T> cls) throws Exception {
        String filePath = absPath + fileName;

        // 使用try-with-resources确保资源正确关闭
        try (InputStream ins = new FileInputStream(new File(filePath))) {
            XStream xstream = XSTREAM_WITH_CDATA;

            // 同步处理注解，确保线程安全
            synchronized (xstream) {
                xstream.processAnnotations(cls);
                return (T) xstream.fromXML(ins);
            }
        } catch (FileNotFoundException e) {
            throw new Exception("文件不存在: " + filePath, e);
        } catch (Exception e) {
            throw new Exception("解析XML文件失败: " + filePath, e);
        }
    }

}
