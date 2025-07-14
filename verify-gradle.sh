#!/bin/bash

echo "=== Gradle项目验证脚本 ==="
echo

# 检查Gradle文件是否存在
echo "1. 检查Gradle配置文件..."
files=("build.gradle" "settings.gradle" "gradle.properties" "gradlew")
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "✓ $file 存在"
    else
        echo "✗ $file 不存在"
    fi
done
echo

# 检查Gradle wrapper
echo "2. 检查Gradle Wrapper..."
if [ -f "gradle/wrapper/gradle-wrapper.properties" ]; then
    echo "✓ gradle-wrapper.properties 存在"
else
    echo "✗ gradle-wrapper.properties 不存在"
fi

if [ -x "gradlew" ]; then
    echo "✓ gradlew 可执行"
else
    echo "✗ gradlew 不可执行"
    chmod +x gradlew
    echo "  已设置gradlew为可执行"
fi
echo

# 检查项目结构
echo "3. 检查项目结构..."
dirs=("src/main/java" "src/test/java")
for dir in "${dirs[@]}"; do
    if [ -d "$dir" ]; then
        echo "✓ $dir 目录存在"
    else
        echo "✗ $dir 目录不存在"
    fi
done
echo

# 尝试运行Gradle任务
echo "4. 尝试运行Gradle任务..."
echo "运行: gradle tasks --all"
if command -v gradle &> /dev/null; then
    gradle tasks --all | head -20
    echo "..."
    echo "✓ Gradle命令可用"
else
    echo "✗ 系统未安装Gradle，请使用 ./gradlew"
fi
echo

echo "=== 验证完成 ==="
echo "如果所有检查都通过，可以尝试运行以下命令："
echo "  ./gradlew clean compileJava  # 编译Java代码"
echo "  ./gradlew build             # 完整构建"
echo "  ./gradlew publishToMavenLocal # 发布到本地仓库"
