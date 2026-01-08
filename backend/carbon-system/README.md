# Carbon System Service - 系统服务

## 项目简介

Carbon System 是碳管理系统的核心服务模块，负责系统首页数据面板的展示，包括碳资产统计、项目统计、企业绿度等功能。

## 技术栈

- **Java**: 21
- **Spring Boot**: 3.1.5
- **Spring Cloud**: 2022.0.4
- **MyBatis-Plus**: 3.5.3.1
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Maven**: 3.8+

## 项目结构

```
carbon-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/carbon/system/
│   │   │       ├── CarbonSystemApplication.java    # 主启动类
│   │   │       ├── common/                         # 通用类
│   │   │       │   ├── ApiCode.java               # API响应码
│   │   │       │   ├── ApiResult.java             # API统一返回结果
│   │   │       │   └── BaseController.java        # 控制器基类
│   │   │       ├── config/                         # 配置类
│   │   │       │   └── MyBatisPlusConfig.java     # MyBatis-Plus配置
│   │   │       ├── controller/                     # 控制器层
│   │   │       │   └── DataPanelController.java   # 数据面板控制器
│   │   │       ├── entity/                         # 实体类
│   │   │       │   ├── DataPanel.java             # 数据面板实体
│   │   │       │   ├── SysAccount.java            # 系统账户实体
│   │   │       │   └── SysTenant.java             # 系统租户实体
│   │   │       ├── mapper/                         # Mapper接口
│   │   │       │   ├── DataPanelMapper.java       # 数据面板Mapper
│   │   │       │   ├── SysAccountMapper.java      # 系统账户Mapper
│   │   │       │   ├── SysTenantMapper.java       # 系统租户Mapper
│   │   │       │   └── SysRoleMapper.java         # 系统角色Mapper
│   │   │       ├── service/                        # 服务层
│   │   │       │   ├── BaseService.java           # 服务基类接口
│   │   │       │   ├── BaseServiceImpl.java       # 服务基类实现
│   │   │       │   ├── DataPanelService.java      # 数据面板服务接口
│   │   │       │   └── impl/                      # 服务实现类
│   │   │       │       └── DataPanelServiceImpl.java
│   │   │       └── vo/                            # 值对象
│   │   │           ├── StatHomeDataVo.java        # 首页统计数据VO
│   │   │           ├── StatAccountVo.java         # 账户统计VO
│   │   │           ├── StatCarbonIncomeVo.java    # 碳收入统计VO
│   │   │           ├── StatCarbonMonthVo.java     # 碳月度统计VO
│   │   │           ├── StatCarbonProjectVo.java   # 碳项目统计VO
│   │   │           ├── StatCarbonProject.java     # 碳项目信息
│   │   │           ├── EnterpriseGreennessVo.java # 企业绿度VO
│   │   │           ├── QuotationVo.java           # 报价信息VO
│   │   │           └── QuotationProject.java      # 报价项目VO
│   │   └── resources/
│   │       ├── application.yml                     # 主配置文件
│   │       ├── application-dev.yml                 # 开发环境配置
│   │       └── mapper/system/                      # MyBatis XML映射文件
│   │           ├── DataPanelMapper.xml
│   │           └── SysRoleMapper.xml
│   └── test/                                       # 测试代码
└── pom.xml                                         # Maven配置文件
```

## 核心功能

### 1. 数据面板模块

#### 1.1 获取首页数据
- **接口**: `GET /dataPanel/home`
- **功能**: 获取系统首页的统计数据
- **返回数据**:
  - 账户信息（账户名、企业名、头像、产品版本等）
  - 碳信用和碳配额总计
  - 资产收入和资金收入统计
  - 月度供应量、开发量、销售量
  - 项目统计信息（减排总量、签约数、备案数、核准数）
  - 项目列表（前10个项目）
  - 报价信息

#### 1.2 获取企业绿度
- **接口**: `GET /dataPanel/getGreenness`
- **功能**: 获取企业的绿色评分
- **返回数据**:
  - 碳排放率
  - 能源效率率
  - 环境率

## 数据库设计

### 主要数据表

1. **data_panel** - 数据面板表
   - id: 主键
   - home_data: 首页数据（JSON格式）
   - tenant_id: 租户ID

2. **carbon_credit_assets** - 碳信用资产表
   - total: 总量
   - issuing_date: 发行日期

3. **carbon_quota_assets** - 碳配额资产表
   - total: 总量
   - issuing_date: 发行日期

4. **carbon_project** - 碳项目表
   - project_name: 项目名称
   - estimated_reduction: 预计减排量
   - project_status: 项目状态

5. **sys_account** - 系统账户表
6. **sys_tenant** - 系统租户表
7. **sys_role** - 系统角色表
8. **sys_account_role** - 账户角色关联表

## 配置说明

### application.yml
```yaml
server:
  port: 9092  # 服务端口

spring:
  application:
    name: carbon-system
  profiles:
    active: dev
```

### application-dev.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/carbon
    username: root
    password: your_password
  
  data:
    redis:
      host: 127.0.0.1
      port: 6379
```

## 快速开始

### 1. 环境要求
- JDK 21+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+

### 2. 配置数据库
修改 `src/main/resources/application-dev.yml` 中的数据库连接信息。

### 3. 编译项目
```bash
mvn clean compile
```

### 4. 运行项目
```bash
mvn spring-boot:run
```

服务将在 `http://localhost:9092` 启动。

### 5. 测试接口

#### 获取首页数据
```bash
curl http://localhost:9092/dataPanel/home
```

#### 获取企业绿度
```bash
curl http://localhost:9092/dataPanel/getGreenness
```

## API文档

### 1. 获取首页数据

**请求**
```
GET /dataPanel/home
```

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "accountVo": {
      "accountName": "admin",
      "enterpriseName": "青软U+公司",
      "avatar": "https://...",
      "accountType": "0380000002",
      "productVersion": "0400000003",
      "accountValidity": "2023-07-30",
      "expired": true,
      "roleNames": null
    },
    "carbonCredit": 800.0,
    "carbonQuota": 1536.0,
    "greenScore": 0,
    "assetsIncome": {
      "statDate": "2026-01-06",
      "monthIncome": 0.0,
      "totalIncome": 2336.0,
      "monthOnMonthRatio": 0,
      "yearOnYearRatio": 0
    },
    "projectStat": {
      "reductionTotal": "52215.0",
      "singCount": 0,
      "filingCount": 0,
      "approvedCount": 0,
      "projectList": [...]
    }
  }
}
```

### 2. 获取企业绿度

**请求**
```
GET /dataPanel/getGreenness
```

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "carbonEmissionRate": 30.0,
    "energyEfficiencyRate": 20.0,
    "environmentRate": 50.0
  }
}
```

## 业务逻辑说明

### 首页数据获取流程

1. **查询数据面板**: 根据租户ID从 `data_panel` 表查询缓存的首页数据
2. **查询账户信息**: 从 `sys_account` 表获取当前登录账户信息
3. **查询租户信息**: 从 `sys_tenant` 表获取租户信息
4. **查询角色信息**: 从 `sys_role` 和 `sys_account_role` 表获取账户角色
5. **统计碳资产**: 
   - 从 `carbon_credit_assets` 表统计碳信用总量和月度总量
   - 从 `carbon_quota_assets` 表统计碳配额总量和月度总量
6. **计算收入**: 
   - 资产收入 = 碳信用 + 碳配额
   - 碳估值 = 碳排放量 × 50元/吨
7. **统计项目**: 
   - 从 `carbon_project` 表统计项目数量（按状态分类）
   - 获取项目列表（前10个）
8. **组装返回数据**: 将所有统计数据组装成 `StatHomeDataVo` 返回

## 注意事项

1. **日期格式**: 
   - 数据库中的日期格式为 `yyyy-MM-dd`
   - 返回给前端的日期格式也为 `yyyy-MM-dd`

2. **JSON字段处理**: 
   - `data_panel` 表的 `home_data` 字段使用 JSON 格式存储
   - 使用 `@JsonIgnoreProperties(ignoreUnknown = true)` 忽略未知字段

3. **租户隔离**: 
   - 所有数据查询都基于当前登录用户的租户ID
   - 通过 `BaseController` 和 `BaseService` 获取当前租户ID

4. **碳估值计算**: 
   - 当前使用固定价格 50元/吨
   - 实际应用中可能需要根据市场价格动态调整

## 开发者

- 开发时间: 2026-01-06
- 技术支持: Carbon Team

## 许可证

Copyright © 2026 Carbon System





