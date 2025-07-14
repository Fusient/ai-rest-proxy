# AI REST Proxy

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/Fusient/ai-rest-proxy)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Java Version](https://img.shields.io/badge/java-1.8+-orange.svg)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/gradle-7.6-green.svg)](https://gradle.org/)

**Language**: English | [中文](README.md)

**Language**: English | [中文](README.md)

🚀 **AI REST Proxy** is a high-performance, secure Java REST client proxy library designed for modern microservice architectures. It makes REST API calls simple and elegant through annotation-driven approach, supporting multiple HTTP methods and data formats.

## ✨ Core Features

### 🎯 **Easy to Use**

- ✅ **Annotation-Driven** - Clean API design with zero learning curve
- ✅ **Multi-Protocol Support** - Supports POST, GET, PUT, DELETE and other HTTP methods
- ✅ **Type Safety** - Compile-time type checking to avoid runtime errors
- ✅ **Spring Integration** - Seamless integration with Spring Framework

### 🔒 **Secure & Reliable**

- ✅ **XStream Security** - Upgraded to latest version, prevents deserialization attacks
- ✅ **Thread Safety** - Full support for multi-threaded concurrent access
- ✅ **Resource Management** - Automatic resource cleanup, prevents memory leaks
- ✅ **Input Validation** - Strict parameter validation and exception handling

### ⚡ **High Performance**

- ✅ **Connection Pool Management** - Smart HTTP connection pool for improved concurrency
- ✅ **Singleton Optimization** - Optimized object creation, reduced memory overhead
- ✅ **Flexible Configuration** - Single dependency, supports multiple service addresses
- ✅ **Configuration Center** - Supports configuration center dependency download

### 🛠️ **Flexible Extension**

- ✅ **Multi-Format Support** - Supports JSON and XML data formats
- ✅ **Custom Codec** - Extensible encoder/decoder architecture
- ✅ **Timeout Control** - Fine-grained timeout configuration

## 📦 Quick Start

### Add Dependency

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

## 📚 API Usage Examples

### Basic Usage

```java
// 1. Define REST Interface
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

// 2. Use Proxy
try (RestServiceProxyFactory factory = new RestServiceProxyFactory()) {
    factory.setConfigName("rest-proxy-config.xml");
    factory.init();

    UserService userService = factory.newRestServiceProxy(UserService.class);

    // Call API
    User user = userService.getUserById(1L);
    System.out.println("Username: " + user.getName());
}
```

### Supported Annotations

| Annotation     | Description                 | Example                                     |
| -------------- | --------------------------- | ------------------------------------------- |
| `@RestService` | Mark REST service interface | `@RestService(serviceName = "userService")` |
| `@GET`         | HTTP GET request            | `@GET("/users/{id}")`                       |
| `@POST`        | HTTP POST request           | `@POST("/users")`                           |
| `@PUT`         | HTTP PUT request            | `@PUT("/users/{id}")`                       |
| `@DELETE`      | HTTP DELETE request         | `@DELETE("/users/{id}")`                    |
| `@PathParam`   | Path parameter              | `@PathParam("id") Long id`                  |
| `@QueryParam`  | Query parameter             | `@QueryParam("page") int page`              |
| `@RequestBody` | Request body                | `@RequestBody User user`                    |
| `@Consumes`    | Request content type        | `@Consumes("application/json")`             |
| `@Produces`    | Response content type       | `@Produces("application/xml")`              |

## 🛠️ Build Project

### Requirements

- Java 1.8+
- Gradle 7.6+ (Recommended to use project's Gradle Wrapper)

### Build Commands

```bash
# Compile project
./gradlew compileJava

# Run tests
./gradlew test

# Full build (including tests, packaging, documentation)
./gradlew build

# Clean and rebuild
./gradlew clean build

# Publish to local Maven repository
./gradlew publishToMavenLocal

# Publish to remote repository (requires GPG signing)
./gradlew publish
```

## 🧪 Test Coverage

The project includes comprehensive unit tests covering core functionality:

```bash
# Run tests
./gradlew test

# Generate coverage report
./gradlew jacocoTestReport

# View coverage report
open build/reports/jacoco/test/html/index.html
```

## 📊 Performance Features

- **Connection Reuse**: Smart HTTP connection pool, reduces connection overhead
- **Memory Optimization**: Singleton pattern and object pool, reduces GC pressure
- **Concurrent Safety**: Thread-safe design, supports high concurrency access
- **Resource Management**: Automatic resource cleanup, prevents memory leaks

## 🛡️ Security Features

- **XStream Security**: Upgraded to latest version, prevents deserialization attacks
- **Input Validation**: Strict parameter validation and exception handling
- **Connection Security**: Supports HTTPS and certificate validation

## 🤝 Contributing

We welcome all forms of contributions!

1. Fork the project
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Thanks to all contributors for their efforts
- Built on Apache HttpClient
- Uses XStream for XML processing
- Uses Jackson for JSON processing

## 📞 Contact Us

- **Issues**: [GitHub Issues](https://github.com/Fusient/ai-rest-proxy/issues)
- **Discussions**: [GitHub Discussions](https://github.com/Fusient/ai-rest-proxy/discussions)
- **Email**: support@ijson.com

---

⭐ If this project helps you, please give us a star!
