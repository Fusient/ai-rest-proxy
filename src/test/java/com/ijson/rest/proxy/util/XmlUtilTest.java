package com.ijson.rest.proxy.util;

import com.ijson.rest.proxy.annotation.CDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * XmlUtil单元测试
 */
public class XmlUtilTest {

    @Test
    public void testToXml() {
        TestObject obj = new TestObject();
        obj.setName("测试");
        obj.setDescription("这是一个测试描述");
        
        String xml = XmlUtil.toXml(obj);
        
        assertNotNull(xml);
        assertTrue(xml.contains("<name>测试</name>"));
        assertTrue(xml.contains("<![CDATA[这是一个测试描述]]>"));
    }

    @Test
    public void testToBean() {
        String xml = "<test-object><name>测试</name><description><![CDATA[这是一个测试描述]]></description></test-object>";
        
        TestObject obj = XmlUtil.toBean(xml, TestObject.class);
        
        assertNotNull(obj);
        assertEquals("测试", obj.getName());
        assertTrue(obj.getDescription().contains("这是一个测试描述"));
    }

    @Test
    public void testToXmlWithNullObject() {
        try {
            XmlUtil.toXml(null);
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期的异常
        }
    }

    @Test
    public void testToBeanWithInvalidXml() {
        try {
            XmlUtil.toBean("invalid xml", TestObject.class);
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期的异常
        }
    }

    @Test
    public void testToBeanWithNullXml() {
        try {
            XmlUtil.toBean(null, TestObject.class);
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期的异常
        }
    }

    @Data
    @XStreamAlias("test-object")
    public static class TestObject {
        private String name;
        
        @CDATA
        private String description;
    }
}
