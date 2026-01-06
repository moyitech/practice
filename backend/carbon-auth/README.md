# Carbon Auth Service - 认证服务

## 项目简介

Carbon Auth Service 是一个基于 Spring Boot + MyBatis-Plus + Redis 的认证服务，提供用户登录、注册、忘记密码等功能。

## 技术栈

- **Spring Boot 2.6.13** - 基础框架
- **Spring Cloud 2021.0.5** - 微服务框架
- **Spring Cloud Alibaba 2021.0.4.0** - 阿里巴巴微服务组件
- **MyBatis-Plus 3.5.2** - ORM框架
- **Redis** - 缓存数据库
- **MySQL** - 关系型数据库
- **RocketMQ** - 消息队列
- **JWT** - Token认证
- **Swagger 2.9.2** - API文档

## 项目结构

```
carbon-auth/
├── main/
│   ├── java/com/carbon/auth/
│   │   ├── common/              # 公共类
│   │   │   ├── ApiCode.java     # API响应码
│   │   │   ├── ApiResult.java   # API统一返回结果
│   │   │   └── ExpCodeEnum.java # 异常代码枚举
│   │   ├── config/              # 配置类
│   │   │   ├── MyBatisPlusConfig.java
│   │   │   └── SwaggerConfig.java
│   │   ├── constant/            # 常量类
│   │   │   ├── AccountConstant.java
│   │   │   ├── CommonRedisKey.java
│   │   │   ├── RedisKeyName.java
│   │   │   ├── RocketDelayLevelConstant.java
│   │   │   └── RocketMqName.java
│   │   ├── controller/          # 控制器层
│   │   │   └── LoginController.java
│   │   ├── entity/              # 实体类
│   │   │   ├── BaseEntity.java
│   │   │   └── SysAccount.java
│   │   ├── exception/           # 异常类
│   │   │   └── CommonBizException.java
│   │   ├── mapper/              # Mapper层
│   │   │   └── LoginMapper.java
│   │   ├── param/               # 参数类
│   │   │   ├── ChangePasswordParam.java
│   │   │   ├── ForgotPasswordParam.java
│   │   │   ├── LoginParam.java
│   │   │   ├── RegisterParam.java
│   │   │   └── SysAccountParam.java
│   │   ├── service/             # 服务层
│   │   │   ├── feign/           # Feign客户端
│   │   │   │   └── SystemServiceApi.java
│   │   │   ├── impl/            # 服务实现
│   │   │   │   ├── LoginServiceImpl.java
│   │   │   │   ├── RedisServiceImpl.java
│   │   │   │   └── SmsServiceImpl.java
│   │   │   ├── LoginService.java
│   │   │   ├── RedisService.java
│   │   │   └── SmsService.java
│   │   ├── util/                # 工具类
│   │   │   ├── HttpContextUtils.java
│   │   │   └── JwtUtil.java
│   │   ├── vo/                  # 视图对象
│   │   │   ├── LoginInfoVo.java
│   │   │   ├── OpenRegisterAccount.java
│   │   │   └── SecurityData.java
│   │   └── CarbonAuthApplication.java
│   └── resources/
│       ├── mapper/system/       # MyBatis XML映射文件
│       │   └── LoginMapper.xml
│       ├── application.yml      # 主配置文件
│       └── application-dev.yml  # 开发环境配置
└── pom.xml                      # Maven依赖配置
```

## 核心功能

### 1. 用户登录 (byLoginName)

**业务流程：**
1. 根据用户名查询账号信息
2. 验证密码是否正确
3. 检查账号状态（是否禁用、是否开户）
4. 从Redis查询登录信息，如果已登录则直接返回
5. 生成JWT Token并保存到Redis
6. 返回登录信息（Token + 安全数据）

**接口地址：** `POST /auth/login`

**请求参数：**
```json
{
  "accountName": "admin",
  "password": "123456"
}
```

### 2. 用户注册 (register)

**业务流程：**
1. 校验短信验证码
2. 判断两次密码是否一致
3. 调用系统服务添加账号
4. 发送开户审批MQ消息到RocketMQ

**接口地址：** `POST /auth/register`

**请求参数：**
```json
{
  "accountName": "testuser",
  "password": "123456",
  "confirmPassword": "123456",
  "phone": "13800138000",
  "email": "test@example.com",
  "code": "123456"
}
```

### 3. 忘记密码 (forgotPassword)

**业务流程：**
1. 校验短信验证码
2. 判断两次密码是否一致
3. 根据手机号查询用户
4. 调用系统服务更新密码

**接口地址：** `POST /auth/forgotPassword`

**请求参数：**
```json
{
  "phone": "13800138000",
  "code": "123456",
  "password": "newpassword",
  "confirmPassword": "newpassword"
}
```

### 4. 退出登录 (logout)

**业务流程：**
1. 从请求头获取当前用户ID
2. 删除Redis中的登录信息

**接口地址：** `GET /auth/logout`

### 5. 判断是否登录 (isLogin)

**业务流程：**
1. 从请求头获取Token
2. 从Redis获取登录信息
3. 对比Token是否一致

### 6. 获取验证码

**注册验证码：** `GET /auth/register/code/{phone}`

**忘记密码验证码：** `GET /auth/forgotPassword/code/{phone}`

## Redis缓存设计

### 登录信息缓存

**Key格式：** `login:user:{accountId}`

**Value：** LoginInfoVo对象的JSON字符串

**过期时间：** 7天

**数据结构：**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "securityData": {
    "accountId": 1,
    "accountName": "admin",
    "phone": "13800138000",
    "tenantId": 1,
    "roleCodes": ["admin"],
    "permissionCodes": ["/api/user/list"]
  }
}
```

### 验证码缓存

**注册验证码Key：** `sms:register:{phone}`

**忘记密码验证码Key：** `sms:forgot:password:{phone}`

**过期时间：** 5分钟

## 数据库设计

### sys_account 表

```sql
CREATE TABLE `sys_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_name` varchar(50) NOT NULL COMMENT '账户名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account_status` varchar(10) NOT NULL COMMENT '账户状态 1-启用 0-禁用 2-未开户',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '所属租户',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除 1-已删除 0-未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account_name` (`account_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统账号表';
```

## 配置说明

### application.yml

```yaml
server:
  port: 9091

spring:
  application:
    name: carbon-auth
  profiles:
    active: dev
```

### application-dev.yml

```yaml
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://127.0.0.1:3306/carbon?useUnicode=true&characterEncoding=utf8
      username: root
      password: your_password
  
  redis:
    host: 127.0.0.1
    port: 6379
  
  cloud:
    nacos:
      discovery:
        server-addr: http://127.0.0.1:8848

rocketmq:
  name-server: 127.0.0.1:9876
  producer:
    group: carbon-auth
```

## 启动步骤

### 1. 环境准备

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+
- RocketMQ 4.9+
- Nacos 2.0+

### 2. 数据库初始化

执行SQL脚本创建数据库和表结构。

### 3. 修改配置

修改 `application-dev.yml` 中的数据库、Redis、Nacos、RocketMQ配置。

### 4. 启动服务

```bash
mvn clean package
java -jar target/carbon-auth-1.0.0.jar
```

或者在IDE中直接运行 `CarbonAuthApplication.java`

### 5. 访问接口文档

启动成功后访问：http://localhost:9091/swagger-ui.html

## 接口测试

### 登录接口测试

```bash
curl -X POST "http://localhost:9091/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "accountName": "admin",
    "password": "123456"
  }'
```

### 注册接口测试

```bash
# 1. 获取验证码
curl -X GET "http://localhost:9091/auth/register/code/13800138000"

# 2. 注册
curl -X POST "http://localhost:9091/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "accountName": "testuser",
    "password": "123456",
    "confirmPassword": "123456",
    "phone": "13800138000",
    "email": "test@example.com",
    "code": "123456"
  }'
```

## 注意事项

1. **密码加密：** 密码使用MD5加密存储
2. **Token有效期：** JWT Token有效期为7天
3. **验证码有效期：** 短信验证码有效期为5分钟
4. **Redis缓存：** 登录信息缓存在Redis中，过期时间与Token一致
5. **微服务调用：** 注册和修改密码功能需要调用carbon-system服务
6. **消息队列：** 注册成功后会发送MQ消息到RocketMQ

## 开发者

Carbon Team

## 版本

1.0.0

