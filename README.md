# ai-rest-proxy

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/Fusient/ai-rest-proxy)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Java Version](https://img.shields.io/badge/java-1.8+-orange.svg)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/gradle-7.6-green.svg)](https://gradle.org/)

RESTful API 代理工具，支持多种 HTTP 方法和数据格式的轻量级 Java 库。

## 🚀 功能特性

- ✅ **多协议支持** - 支持 POST、GET、PUT、DELETE 等 HTTP 方法
- ✅ **多格式支持** - 支持 JSON 和 XML 数据格式
- ✅ **配置灵活** - 单一依赖，支持多个不同服务地址
- ✅ **配置中心** - 支持配置中心依赖下载
- ✅ **注解驱动** - 基于注解的简洁 API 设计
- ✅ **Spring 集成** - 无缝集成 Spring 框架

## 📦 快速开始

### 添加依赖

**Gradle**:

```gradle
dependencies {
    implementation 'com.ijson:ai-rest-proxy:1.0.0-SNAPSHOT'
}
```

**Maven**:

```xml
<dependency>
    <groupId>com.ijson</groupId>
    <artifactId>ai-rest-proxy</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## 🛠️ 构建项目

### 环境要求

- Java 1.8+
- Gradle 7.6+ (推荐使用项目自带的 Gradle Wrapper)

### 构建命令

```bash
# 编译项目
./gradlew compileJava

# 运行测试
./gradlew test

# 完整构建（包含测试、打包、文档生成）
./gradlew build

# 清理并重新构建
./gradlew clean build

# 发布到本地Maven仓库
./gradlew publishToMavenLocal

# 发布到远程仓库（需要配置GPG签名）
./gradlew publish

# 查看所有可用任务
./gradlew tasks

# 查看项目依赖
./gradlew dependencies
```

### 构建产物

构建成功后，在 `build/libs/` 目录下会生成：

- `ai-rest-proxy-1.0.0-SNAPSHOT.jar` - 主 JAR 包
- `ai-rest-proxy-1.0.0-SNAPSHOT-sources.jar` - 源码包
- `ai-rest-proxy-1.0.0-SNAPSHOT-javadoc.jar` - 文档包

# 使用方式

`案例列举以微信支付接口及淘宝接口进行介绍`

[微信支付-统一下单](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1):

1. 根据接口定义自己的 model,请求及返回参数定义

```
详见:Unifiedorder.java
其中:
    @INField 注解主要校验当前字段是否为必填字段,可根据此字段生成接口文档
    @XStreamAlias("xml") 定义xml最外层标签
    @CDATA 如果是xml 则会自动添加 <![CDATA[" 开始，由 "]]> 标签
    @Data 自动生成get和set方法,idea的话需要安装lombok插件
```

2. 定义数据拼装解析类

```
详见:WeixinRestCodeC.java
主要是解决不同数据格式带来的问题,由开发者自定义

继承 AbstractRestCodeC.java

encodeArg为拼装数据结构

XmlUtil.toXml(T) 将对象转换成xml

decodeResult(int,map,byte,T) 解析返回的数据结构

XmlUtil.toBean(xml, T); 将xml转换成对象

validateResult 校验返回结果是否正确

```

3. 定义接口地址资源

```
详见:WeixinResource.java

@RestResource 定义资源信息

其中
   value : 配置文件中的Key
   desc :描述信息
   codec : 数据拼装解析类
   contentType : 数据返回类型

@POST
    value 地址 ,配置文件中的url+此路径为服务整体地址
    desc 描述

@GET

    value 地址 ,配置文件中的url+此路径为服务整体地址
    desc 描述


```
