# Carbon Auth 启动指南

## ⚠️ 启动前准备

在启动应用之前，请确保以下服务已经启动并正常运行：

### 1. MySQL数据库 ✅

**检查MySQL是否运行：**
```bash
mysql -u root -p -e "SELECT 1"
```

**如果MySQL未启动，请启动：**
```bash
# macOS
mysql.server start

# 或使用brew services
brew services start mysql
```

**初始化数据库：**
```bash
cd /Users/mengxianze/Desktop/project/cursor-design-1/backend/carbon-auth
mysql -u root -p < sql/init.sql
```

**修改密码：**
编辑 `src/main/resources/application-dev.yml`，修改第9行的密码：
```yaml
password: 你的MySQL密码  # 修改这里
```

### 2. Redis ✅

**检查Redis是否运行：**
```bash
redis-cli ping
# 应该返回: PONG
```

**如果Redis未启动，请启动：**
```bash
# macOS
redis-server

# 或使用brew services
brew services start redis
```

### 3. Nacos（可选） ⚠️

Nacos用于服务注册与发现。如果不需要微服务功能，可以暂时禁用。

**检查Nacos是否运行：**
```bash
curl http://localhost:8848/nacos
```

**启动Nacos：**
```bash
# 下载Nacos（如果还没有）
# https://github.com/alibaba/nacos/releases

# 启动Nacos（单机模式）
sh startup.sh -m standalone
```

**禁用Nacos（如果不需要）：**
在 `src/main/resources/application-dev.yml` 中注释掉Nacos配置：
```yaml
#  cloud:
#    nacos:
#      discovery:
#        server-addr: http://127.0.0.1:8848
```

### 4. RocketMQ（可选） ⚠️

RocketMQ用于异步消息处理。如果不需要注册功能，可以暂时禁用。

**启动RocketMQ：**
```bash
# 启动NameServer
sh mqnamesrv

# 启动Broker
sh mqbroker -n localhost:9876
```

**禁用RocketMQ（如果不需要）：**
在 `src/main/resources/application-dev.yml` 中注释掉RocketMQ配置：
```yaml
# rocketmq:
#   name-server: 127.0.0.1:9876
```

## 🚀 启动应用

### 方式一：使用Maven启动（推荐）

```bash
cd /Users/mengxianze/Desktop/project/cursor-design-1/backend/carbon-auth
mvn spring-boot:run
```

### 方式二：使用JAR包启动

```bash
cd /Users/mengxianze/Desktop/project/cursor-design-1/backend/carbon-auth
java -jar target/carbon-auth-1.0.0.jar
```

### 方式三：在IDE中启动

1. 打开IDEA或其他IDE
2. 找到 `CarbonAuthApplication.java`
3. 右键 → Run 'CarbonAuthApplication'

## ✅ 验证启动成功

### 1. 查看启动日志

如果看到以下日志，说明启动成功：

```
=================================
Carbon Auth Service Started Successfully!
=================================
```

### 2. 访问Swagger文档

打开浏览器访问：
```
http://localhost:9091/swagger-ui.html
```

### 3. 测试健康检查

```bash
curl http://localhost:9091/actuator/health
```

### 4. 测试登录接口

```bash
curl -X POST "http://localhost:9091/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "accountName": "admin",
    "password": "123456"
  }'
```

## 🔧 常见问题

### 问题1：无法连接MySQL

**错误信息：**
```
Failed to configure a DataSource: 'url' attribute is not specified
```

**解决方案：**
1. 检查MySQL是否启动：`mysql -u root -p -e "SELECT 1"`
2. 检查数据库是否创建：`mysql -u root -p -e "SHOW DATABASES LIKE 'carbon'"`
3. 检查用户名密码是否正确
4. 检查配置文件中的密码是否已修改

### 问题2：无法连接Redis

**错误信息：**
```
Unable to connect to Redis
```

**解决方案：**
1. 检查Redis是否启动：`redis-cli ping`
2. 如果Redis设置了密码，在配置文件中添加密码配置

### 问题3：Nacos连接失败

**错误信息：**
```
Nacos server is not available
```

**解决方案：**
1. 如果不需要微服务功能，注释掉Nacos配置
2. 如果需要，确保Nacos已启动：访问 http://localhost:8848/nacos

### 问题4：端口被占用

**错误信息：**
```
Port 9091 was already in use
```

**解决方案：**
1. 查找占用端口的进程：`lsof -i:9091`
2. 杀死进程：`kill -9 <PID>`
3. 或修改配置文件中的端口号

## 📝 最小启动配置

如果只想快速测试，只需要启动 **MySQL** 和 **Redis**：

### 1. 启动MySQL
```bash
mysql.server start
```

### 2. 初始化数据库
```bash
mysql -u root -p < sql/init.sql
```

### 3. 修改密码
编辑 `src/main/resources/application-dev.yml`，修改MySQL密码

### 4. 启动Redis
```bash
redis-server
```

### 5. 禁用Nacos和RocketMQ
编辑 `src/main/resources/application-dev.yml`，注释掉以下配置：

```yaml
#  cloud:
#    nacos:
#      discovery:
#        server-addr: http://127.0.0.1:8848

# rocketmq:
#   name-server: 127.0.0.1:9876
#   producer:
#     group: carbon-auth
```

### 6. 启动应用
```bash
mvn spring-boot:run
```

## 🎯 测试接口

### 登录
```bash
curl -X POST "http://localhost:9091/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "accountName": "admin",
    "password": "123456"
  }'
```

### 获取验证码
```bash
curl -X GET "http://localhost:9091/auth/register/code/13800138000"
```

### 验证账户
```bash
curl -X GET "http://localhost:9091/auth/verify/admin"
```

## 📚 更多信息

- 完整文档：`README.md`
- 实现说明：`项目实现说明.md`
- 构建说明：`BUILD_SUCCESS.md`
- API测试：导入 `postman/Carbon-Auth-API.postman_collection.json`

## ⚡ 快速启动脚本

创建一个启动脚本 `start.sh`：

```bash
#!/bin/bash

echo "==================================="
echo "启动 Carbon Auth 服务"
echo "==================================="

# 检查MySQL
echo "检查MySQL..."
if ! mysql -u root -p13579MXZmxz... -e "SELECT 1" > /dev/null 2>&1; then
    echo "❌ MySQL未启动或密码错误"
    exit 1
fi
echo "✅ MySQL正常"

# 检查Redis
echo "检查Redis..."
if ! redis-cli ping > /dev/null 2>&1; then
    echo "❌ Redis未启动"
    exit 1
fi
echo "✅ Redis正常"

# 启动应用
echo "启动应用..."
mvn spring-boot:run
```

使用方法：
```bash
chmod +x start.sh
./start.sh
```

---

**祝您使用愉快！** 🎉

