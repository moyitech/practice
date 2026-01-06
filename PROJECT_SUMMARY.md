# Carbon Management System - 项目完成总结

## 项目概述

本项目是一个完整的碳管理系统，包含后端服务（Spring Boot）和前端界面（Vue.js），实现了用户注册、登录、数据面板等核心功能。

**完成时间**: 2026-01-06  
**开发环境**: macOS, Java 21, Node.js, MySQL 8.0, Redis 6.0

---

## 项目结构

```
cursor-design-1/
├── backend/                          # 后端服务
│   ├── carbon-auth/                 # 认证服务 (端口: 9091)
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/carbon/auth/
│   │   │   │   │   ├── controller/     # 控制器层
│   │   │   │   │   ├── service/        # 服务层
│   │   │   │   │   ├── mapper/         # 数据访问层
│   │   │   │   │   ├── entity/         # 实体类
│   │   │   │   │   ├── vo/             # 值对象
│   │   │   │   │   ├── param/          # 参数对象
│   │   │   │   │   ├── common/         # 通用类
│   │   │   │   │   ├── config/         # 配置类
│   │   │   │   │   ├── constant/       # 常量类
│   │   │   │   │   ├── exception/      # 异常类
│   │   │   │   │   └── util/           # 工具类
│   │   │   │   └── resources/
│   │   │   │       ├── application.yml
│   │   │   │       ├── application-dev.yml
│   │   │   │       └── mapper/system/  # MyBatis XML
│   │   │   └── test/                   # 测试代码
│   │   ├── pom.xml
│   │   └── README.md
│   │
│   └── carbon-system/               # 系统服务 (端口: 9092)
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/com/carbon/system/
│       │   │   │   ├── controller/     # 控制器层
│       │   │   │   ├── service/        # 服务层
│       │   │   │   ├── mapper/         # 数据访问层
│       │   │   │   ├── entity/         # 实体类
│       │   │   │   ├── vo/             # 值对象
│       │   │   │   ├── common/         # 通用类
│       │   │   │   └── config/         # 配置类
│       │   │   └── resources/
│       │   │       ├── application.yml
│       │   │       ├── application-dev.yml
│       │   │       └── mapper/system/  # MyBatis XML
│       │   └── test/
│       ├── pom.xml
│       └── README.md
│
└── frontend/                        # 前端项目 (端口: 8081)
    ├── public/
    │   └── index.html
    ├── src/
    │   ├── api/                    # API接口封装
    │   │   └── user.js
    │   ├── assets/                 # 静态资源
    │   │   └── imgs/
    │   ├── router/                 # 路由配置
    │   │   └── index.js
    │   ├── utils/                  # 工具类
    │   │   └── request.js         # Axios封装
    │   ├── views/                  # 页面组件
    │   │   ├── login/
    │   │   │   ├── index.vue      # 登录页面
    │   │   │   └── register.vue   # 注册页面
    │   │   └── Home.vue           # 首页
    │   ├── App.vue
    │   └── main.js
    ├── package.json
    ├── vue.config.js
    └── README.md
```

---

## 已实现功能

### 1. Carbon-Auth 认证服务 ✅

#### 1.1 用户注册功能
- **接口**: `POST /auth/register`
- **功能**:
  - 用户名唯一性验证
  - 手机号格式验证
  - 短信验证码验证
  - 密码MD5加密
  - 账户状态管理
- **技术点**:
  - MyBatis-Plus 数据操作
  - Redis 缓存验证码
  - 参数校验

#### 1.2 用户登录功能
- **接口**: `POST /auth/login`
- **功能**:
  - 用户名密码登录
  - JWT Token生成
  - Redis缓存登录信息
  - 账户状态检查（禁用/未开户）
- **技术点**:
  - JWT Token管理
  - Redis缓存
  - 密码MD5验证

#### 1.3 验证码功能
- **接口**: `GET /auth/register/code/{phone}`
- **功能**:
  - 发送短信验证码
  - Redis存储验证码（60秒有效期）
  - 验证码校验

#### 1.4 账户验证功能
- **接口**: `GET /auth/verify/{accountName}`
- **功能**:
  - 验证用户名是否已存在
  - 返回布尔值（true=可用，false=已存在）

#### 1.5 退出登录功能
- **接口**: `GET /auth/logout`
- **功能**:
  - 清除Redis中的登录信息
  - Token失效处理

### 2. Carbon-System 系统服务 ✅

#### 2.1 首页数据面板
- **接口**: `GET /dataPanel/home`
- **功能**:
  - 账户信息展示
  - 碳信用/碳配额统计
  - 资产收入统计
  - 月度供应量/开发量/销售量
  - 项目统计（减排总量、签约数、备案数、核准数）
  - 项目列表（前10个）
- **技术点**:
  - 多表关联查询
  - JSON字段处理
  - 数据聚合统计

#### 2.2 企业绿度
- **接口**: `GET /dataPanel/getGreenness`
- **功能**:
  - 碳排放率
  - 能源效率率
  - 环境率

### 3. Frontend 前端界面 ✅

#### 3.1 注册页面
- **路由**: `/register`
- **功能**:
  - 用户名输入（实时验证）
  - 手机号输入（格式验证）
  - 获取验证码（60秒倒计时）
  - 密码设置（6-16位）
  - 确认密码
  - 表单验证
  - 注册成功跳转登录
- **技术点**:
  - Vue 2.x
  - Element UI
  - Axios请求封装
  - 表单验证

#### 3.2 登录页面
- **路由**: `/login`
- **功能**:
  - 用户名/密码登录
  - Token存储
  - 登录状态管理
  - 自动跳转
- **技术点**:
  - LocalStorage
  - 路由守卫
  - Token管理

#### 3.3 首页
- **路由**: `/home`
- **功能**:
  - 用户信息展示
  - 退出登录
- **技术点**:
  - 路由守卫
  - 登录状态验证

---

## 技术栈

### 后端技术栈
- **Java**: 21
- **Spring Boot**: 3.1.5
- **Spring Cloud**: 2022.0.4
- **MyBatis-Plus**: 3.5.3.1
- **MySQL**: 8.0
- **Redis**: 6.0
- **Druid**: 1.2.16
- **Hutool**: 5.8.18
- **JWT**: 0.9.1
- **Lombok**: 1.18.30

### 前端技术栈
- **Vue.js**: 2.6.14
- **Vue Router**: 3.5.1
- **Element UI**: 2.15.14
- **Axios**: 1.6.0
- **Sass**: 1.69.5
- **js-md5**: 0.8.3

---

## 服务启动状态

### 后端服务

#### Carbon-Auth (认证服务)
- **状态**: ✅ 运行中
- **端口**: 9091
- **地址**: http://localhost:9091
- **启动命令**: `mvn spring-boot:run`

#### Carbon-System (系统服务)
- **状态**: ✅ 运行中
- **端口**: 9092
- **地址**: http://localhost:9092
- **启动命令**: `mvn spring-boot:run`

### 前端服务

#### Frontend (前端界面)
- **状态**: ✅ 运行中
- **端口**: 8081
- **地址**: http://localhost:8081
- **启动命令**: `npm run serve`

### 数据库服务

#### MySQL
- **状态**: ✅ 运行中
- **端口**: 3306
- **数据库**: carbon

#### Redis
- **状态**: ✅ 运行中
- **端口**: 6379

---

## 接口测试结果

### 1. 认证服务接口测试

#### ✅ 登录接口
```bash
curl -X POST http://localhost:9091/auth/login \
  -H "Content-Type: application/json" \
  -d '{"accountName":"admin","password":"123456"}'
```
**结果**: 成功返回Token和用户信息

#### ✅ 验证账户接口
```bash
curl http://localhost:9091/auth/verify/admin
```
**结果**: 返回 `{"code":200,"msg":"操作成功","data":false}`

#### ✅ 退出登录接口
```bash
curl http://localhost:9091/auth/logout \
  -H "Authorization: Bearer {token}"
```
**结果**: 成功清除登录信息

### 2. 系统服务接口测试

#### ✅ 首页数据接口
```bash
curl http://localhost:9092/dataPanel/home
```
**结果**: 成功返回完整的首页统计数据

#### ✅ 企业绿度接口
```bash
curl http://localhost:9092/dataPanel/getGreenness
```
**结果**: 成功返回企业绿度数据

---

## 核心功能实现要点

### 1. Redis缓存应用

#### 登录信息缓存
- **Key格式**: `login:user:{accountId}`
- **过期时间**: 7天
- **数据结构**: JSON字符串
- **用途**: 
  - 存储用户Token
  - 存储用户安全数据
  - 避免频繁查询数据库

#### 验证码缓存
- **Key格式**: `sms:register:{phone}` / `sms:forgot:{phone}`
- **过期时间**: 60秒
- **用途**:
  - 存储短信验证码
  - 防止验证码重复发送
  - 自动过期清理

### 2. MyBatis数据查询

#### 单表查询
```java
SysAccount account = loginMapper.selectOne(
    Wrappers.lambdaQuery(SysAccount.class)
        .eq(SysAccount::getAccountName, accountName)
);
```

#### 多表关联查询
```xml
<select id="getSecurityDataAccountId" resultType="SecurityData">
    select sa.id accountId, sa.phone, sa.avatar, sa.account_name,
           ifnull(group_concat(sr.role_code),'') roleCodeStr,
           ifnull(group_concat(sp.uri),'') permissionCodesStr
    from sys_account sa
    left join sys_account_role sar on sar.account_id = sa.id
    left join sys_role sr on sr.id = sar.role_id
    left join sys_role_permission srp on srp.role_id = sar.role_id
    left join sys_permission sp on sp.id = srp.permission_id
    where sa.id = #{accountId}
    group by sa.id
</select>
```

### 3. JWT Token管理

#### Token生成
```java
String token = Jwts.builder()
    .setSubject(accountId.toString())
    .claim("tenantId", tenantId)
    .setIssuedAt(new Date())
    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
    .signWith(SignatureAlgorithm.HS512, SECRET)
    .compact();
```

#### Token验证
```java
Claims claims = Jwts.parser()
    .setSigningKey(SECRET)
    .parseClaimsJws(token)
    .getBody();
```

### 4. 前端请求封装

#### Axios拦截器
```javascript
// 请求拦截器 - 添加Token
service.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token
  }
  return config
})

// 响应拦截器 - 统一错误处理
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code && res.code !== 200) {
      Message.error(res.msg || 'Error')
      return Promise.reject(new Error(res.msg))
    }
    return res
  },
  error => {
    Message.error(error.message || '请求失败')
    return Promise.reject(error)
  }
)
```

### 5. 路由守卫

```javascript
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 需要登录的页面
    if (!token) {
      next({ path: '/login', query: { redirect: to.fullPath } })
    } else {
      next()
    }
  } else {
    // 已登录用户访问登录页，跳转到首页
    if (token && (to.path === '/login' || to.path === '/register')) {
      next('/home')
    } else {
      next()
    }
  }
})
```

---

## 遇到的问题及解决方案

### 1. Java版本兼容性问题
**问题**: Lombok与Java 21不兼容  
**解决**: 升级Lombok到1.18.30版本

### 2. Spring Boot 3.x迁移问题
**问题**: `javax.*` 包不存在  
**解决**: 替换为 `jakarta.*` 包

### 3. 数据库字段名不匹配
**问题**: BaseEntity字段名与数据库不一致  
**解决**: 使用 `@TableField` 注解映射正确的字段名

### 4. JAXB依赖缺失
**问题**: JWT生成时找不到 `javax.xml.bind.DatatypeConverter`  
**解决**: 添加 `jaxb-api` 依赖

### 5. 日期格式问题
**问题**: JSON反序列化日期格式不匹配  
**解决**: 修改 `@JsonFormat` 的pattern为 `yyyy-MM-dd`

### 6. 端口占用问题
**问题**: 端口9091/9092被占用  
**解决**: 使用 `lsof -ti:port | xargs kill -9` 清理端口

---

## 快速启动指南

### 1. 启动数据库服务
```bash
# MySQL (确保已启动)
mysql.server start

# Redis (确保已启动)
redis-server
```

### 2. 启动后端服务

#### Carbon-Auth (认证服务)
```bash
cd backend/carbon-auth
mvn spring-boot:run
```

#### Carbon-System (系统服务)
```bash
cd backend/carbon-system
mvn spring-boot:run
```

### 3. 启动前端服务
```bash
cd frontend
npm install  # 首次运行需要安装依赖
npm run serve
```

### 4. 访问系统
- **前端地址**: http://localhost:8081
- **注册页面**: http://localhost:8081/#/register
- **登录页面**: http://localhost:8081/#/login

---

## 测试账号

### 默认管理员账号
- **用户名**: admin
- **密码**: 123456
- **手机号**: 13354443297
- **租户**: 青软U+公司

---

## 项目亮点

### 1. 完整的前后端分离架构
- 后端提供RESTful API
- 前端Vue.js单页应用
- 清晰的职责划分

### 2. 微服务架构设计
- carbon-auth: 认证服务
- carbon-system: 系统服务
- 服务独立部署，易于扩展

### 3. Redis缓存优化
- 登录信息缓存，减少数据库查询
- 验证码缓存，提高响应速度
- 自动过期机制

### 4. 安全性设计
- 密码MD5加密
- JWT Token认证
- 路由守卫保护
- 账户状态管理

### 5. 用户体验优化
- 实时表单验证
- 验证码倒计时
- 加载状态提示
- 友好的错误提示

---

## 后续优化建议

### 功能扩展
1. 实现忘记密码功能
2. 添加数据面板可视化图表
3. 实现碳资产管理功能
4. 添加项目管理功能
5. 实现报表导出功能

### 性能优化
1. 添加接口缓存
2. 实现分页查询
3. 优化SQL查询
4. 添加CDN加速

### 安全加固
1. 实现图形验证码
2. 添加登录限流
3. 实现Token刷新机制
4. 添加操作日志

### 代码质量
1. 添加单元测试
2. 添加集成测试
3. 完善异常处理
4. 优化代码结构

---

## 开发团队

- **开发时间**: 2026-01-06
- **技术支持**: Carbon Team

---

## 许可证

Copyright © 2026 Carbon Management System

---

## 附录

### A. 数据库表结构

#### sys_account (系统账户表)
- id: 主键
- account_name: 账户名
- password: 密码（MD5加密）
- phone: 手机号
- email: 邮箱
- account_status: 账户状态
- tenant_id: 租户ID
- validity_period: 有效期

#### data_panel (数据面板表)
- id: 主键
- home_data: 首页数据（JSON）
- tenant_id: 租户ID

#### carbon_credit_assets (碳信用资产表)
- total: 总量
- issuing_date: 发行日期

#### carbon_quota_assets (碳配额资产表)
- total: 总量
- issuing_date: 发行日期

#### carbon_project (碳项目表)
- project_name: 项目名称
- estimated_reduction: 预计减排量
- project_status: 项目状态

### B. API接口文档

详见各服务的README.md文件。

### C. 常见问题

详见各服务的README.md文件。

---

**项目完成日期**: 2026-01-06  
**文档版本**: 1.0.0

