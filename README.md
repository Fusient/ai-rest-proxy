# ai-rest-proxy

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/Fusient/ai-rest-proxy)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Java Version](https://img.shields.io/badge/java-1.8+-orange.svg)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/gradle-7.6-green.svg)](https://gradle.org/)

🚀 **AI REST Proxy** 是一个高性能、安全的 Java REST 客户端代理库，专为现代微服务架构设计。通过注解驱动的方式，让 REST API 调用变得简单而优雅，支持多种 HTTP 方法和数据格式的轻量级 Java 库。

## ✨ 核心特性

### 🎯 **简单易用**

- ✅ **注解驱动** - 基于注解的简洁 API 设计，零学习成本
- ✅ **多协议支持** - 支持 POST、GET、PUT、DELETE 等 HTTP 方法
- ✅ **类型安全** - 编译时类型检查，避免运行时错误
- ✅ **Spring 集成** - 无缝集成 Spring 框架

### 🔒 **安全可靠**

- ✅ **XStream 安全** - 升级到最新版本，防止反序列化攻击
- ✅ **线程安全** - 完全支持多线程并发访问
- ✅ **资源管理** - 自动资源清理，防止内存泄漏
- ✅ **输入验证** - 严格的参数验证和异常处理

### ⚡ **高性能**

- ✅ **连接池管理** - 智能 HTTP 连接池，提升并发性能
- ✅ **单例优化** - 优化对象创建，减少内存开销
- ✅ **配置灵活** - 单一依赖，支持多个不同服务地址
- ✅ **配置中心** - 支持配置中心依赖下载

### 🛠️ **灵活扩展**

- ✅ **多格式支持** - 支持 JSON 和 XML 数据格式
- ✅ **自定义编解码** - 可扩展的编解码器架构
- ✅ **超时控制** - 细粒度的超时配置

## 📦 快速开始

### 添加依赖

**Gradle**:

```gradle
dependencies {
    implementation 'com.ijson.ai:ai-rest-proxy:1.0.0-SNAPSHOT'
}
```

**Maven**:

```xml
<dependency>
    <groupId>com.ijson.ai</groupId>
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

## 📚 API 使用示例

### 基础用法

```java
// 1. 定义REST接口
@RestService(serviceName = "userService")
public interface UserService {

    @GET("/users/{id}")
    User getUserById(@PathParam("id") Long id);

    @POST("/users")
    @Consumes("application/json")
    User createUser(@RequestBody User user);

    @PUT("/users/{id}")
    User updateUser(@PathParam("id") Long id, @RequestBody User user);

    @DELETE("/users/{id}")
    void deleteUser(@PathParam("id") Long id);
}

// 2. 使用代理调用
try (RestServiceProxyFactory factory = new RestServiceProxyFactory()) {
    factory.setConfigName("rest-proxy-config.xml");
    factory.init();

    UserService userService = factory.newRestServiceProxy(UserService.class);

    // 调用API
    User user = userService.getUserById(1L);
    System.out.println("用户名: " + user.getName());
}
```

### 支持的注解

| 注解           | 描述               | 示例                                        |
| -------------- | ------------------ | ------------------------------------------- |
| `@RestService` | 标记 REST 服务接口 | `@RestService(serviceName = "userService")` |
| `@GET`         | HTTP GET 请求      | `@GET("/users/{id}")`                       |
| `@POST`        | HTTP POST 请求     | `@POST("/users")`                           |
| `@PUT`         | HTTP PUT 请求      | `@PUT("/users/{id}")`                       |
| `@DELETE`      | HTTP DELETE 请求   | `@DELETE("/users/{id}")`                    |
| `@PathParam`   | 路径参数           | `@PathParam("id") Long id`                  |
| `@QueryParam`  | 查询参数           | `@QueryParam("page") int page`              |
| `@RequestBody` | 请求体             | `@RequestBody User user`                    |
| `@Consumes`    | 请求内容类型       | `@Consumes("application/json")`             |
| `@Produces`    | 响应内容类型       | `@Produces("application/xml")`              |

## 📖 详细使用方式

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

## 🧪 测试覆盖

项目包含完整的单元测试，覆盖核心功能：

```bash
# 运行测试
./gradlew test

# 生成覆盖率报告
./gradlew jacocoTestReport

# 查看覆盖率报告
open build/reports/jacoco/test/html/index.html
```

## 📊 性能特性

- **连接复用**: 智能 HTTP 连接池，减少连接开销
- **内存优化**: 单例模式和对象池，降低 GC 压力
- **并发安全**: 线程安全设计，支持高并发访问
- **资源管理**: 自动资源清理，防止内存泄漏

## 🛡️ 安全特性

- **XStream 安全**: 升级到最新版本，防止反序列化攻击
- **输入验证**: 严格的参数验证和异常处理
- **连接安全**: 支持 HTTPS 和证书验证

## 🤝 贡献指南

我们欢迎所有形式的贡献！

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- 感谢所有贡献者的努力
- 基于 Apache HttpClient 构建
- 使用 XStream 进行 XML 处理
- 使用 Jackson 进行 JSON 处理

## 📞 联系我们

- **Issues**: [GitHub Issues](https://github.com/Fusient/ai-rest-proxy/issues)
- **讨论**: [GitHub Discussions](https://github.com/Fusient/ai-rest-proxy/discussions)
- **邮箱**: support@ijson.com

---

⭐ 如果这个项目对您有帮助，请给我们一个星标！
