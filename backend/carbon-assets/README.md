# Carbon Assets Service - 碳资产服务

## 项目简介

Carbon Assets Service 是碳管理系统的碳资产服务模块，负责碳信用资产的管理，包括碳信用的上传、查询、更新、删除等功能。

## 技术栈

- **Java**: 21
- **Spring Boot**: 3.1.5
- **Spring Cloud**: 2022.0.4
- **MyBatis-Plus**: 3.5.3.1
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **RocketMQ**: 2.2.2（可选）
- **Maven**: 3.8+

## 项目结构

```
carbon-assets/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/carbon/assets/
│   │   │       ├── CarbonAssetsApplication.java    # 主启动类
│   │   │       ├── common/                         # 通用类
│   │   │       │   ├── ApiCode.java               # API响应码
│   │   │       │   ├── ApiResult.java             # API统一返回结果
│   │   │       │   └── BaseController.java        # 控制器基类
│   │   │       ├── config/                         # 配置类
│   │   │       │   └── MyBatisPlusConfig.java     # MyBatis-Plus配置
│   │   │       ├── constant/                       # 常量类
│   │   │       │   ├── AssetsStatus.java          # 资产状态枚举
│   │   │       │   ├── AssetsTradeStatus.java     # 资产交易状态枚举
│   │   │       │   ├── RocketMqName.java          # RocketMQ消息名称
│   │   │       │   └── RocketDelayLevelConstant.java # RocketMQ延迟级别
│   │   │       ├── controller/                    # 控制器层
│   │   │       │   └── CarbonCreditAssetsController.java
│   │   │       ├── entity/                         # 实体类
│   │   │       │   ├── BaseEntity.java
│   │   │       │   ├── CarbonCreditAssets.java
│   │   │       │   ├── CarbonMetaregistry.java
│   │   │       │   └── CarbonMetaregistryProject.java
│   │   │       ├── mapper/                         # Mapper接口
│   │   │       │   ├── CarbonCreditAssetsMapper.java
│   │   │       │   ├── CarbonMetaregistryMapper.java
│   │   │       │   └── CarbonMetaregistryProjectMapper.java
│   │   │       ├── param/                          # 参数类
│   │   │       │   ├── CarbonCreditAssetsAddParam.java
│   │   │       │   ├── CarbonCreditAssetsQueryParam.java
│   │   │       │   └── CarbonCreditAssetsParam.java
│   │   │       ├── service/                        # 服务层
│   │   │       │   ├── BaseService.java
│   │   │       │   ├── BaseServiceImpl.java
│   │   │       │   ├── CarbonCreditAssetsService.java
│   │   │       │   ├── impl/
│   │   │       │   │   └── CarbonCreditAssetsServiceImpl.java
│   │   │       │   └── feign/
│   │   │       │       ├── SystemServiceApi.java
│   │   │       │       └── ChainMakerServiceApi.java
│   │   │       └── vo/                            # 值对象
│   │   │           ├── Paging.java
│   │   │           ├── CarbonCreditAssetsQueryVo.java
│   │   │           ├── CarbonAssetsTotalVo.java
│   │   │           ├── CarbonMetaregistryQueryVo.java
│   │   │           ├── AssetUploadApproval.java
│   │   │           ├── SecurityData.java
│   │   │           └── LoginInfoVo.java
│   │   └── resources/
│   │       ├── application.yml                     # 主配置文件
│   │       ├── application-dev.yml                 # 开发环境配置
│   │       └── mapper/assets/                      # MyBatis XML映射文件
│   │           ├── CarbonCreditAssetsMapper.xml
│   │           └── CarbonMetaregistryMapper.xml
│   └── test/                                       # 测试代码
└── pom.xml                                         # Maven配置文件
```

## 核心功能

### 1. 碳信用资产管理

#### 1.1 添加碳信用资产
- **接口**: `POST /assets/carbonCreditAssets/add`
- **功能**: 上传碳信用资产，包括：
  - 查询项目库信息
  - 创建项目关联记录
  - 保存碳信用资产
  - 发送飞书审批消息（RocketMQ）
  - 发送区块链上链消息

#### 1.2 获取碳信用资产详情
- **接口**: `GET /assets/carbonCreditAssets/info/{id}`
- **功能**: 根据ID获取碳信用资产详细信息

#### 1.3 分页查询碳信用资产
- **接口**: `POST /assets/carbonCreditAssets/getPageList`
- **功能**: 分页查询碳信用资产列表，支持多条件筛选

#### 1.4 更新碳信用资产
- **接口**: `PUT /assets/carbonCreditAssets/update`
- **功能**: 更新碳信用资产信息

#### 1.5 删除碳信用资产
- **接口**: `POST /assets/carbonCreditAssets/delete`
- **功能**: 删除碳信用资产及其关联的项目记录

#### 1.6 获取碳资产总额
- **接口**: `GET /assets/carbonCreditAssets/assetsTotal`
- **功能**: 获取碳资产总额统计信息

## 数据库表结构

### carbon_credit_assets (碳信用资产表)
主要字段：
- id: 主键
- carbon_project_id: 项目ID（关联carbon_metaregistry_project表）
- carbon_exchange_id: 交易所ID
- total: 持仓总量
- available_amount: 可用数量
- frozen_amount: 冻结数量
- locked_amount: 锁定数量
- valuation: 资产估值
- assets_status: 资产状态
- transaction_status: 交易状态

### carbon_metaregistry (碳元注册表)
主要字段：
- id: 主键
- project_name: 项目名称
- methodology_code: 方法学代码
- certified_standard: 认证标准
- project_verifier: 项目核证机构
- project_owner: 项目所有者

### carbon_metaregistry_project (碳元注册项目表)
主要字段：
- id: 主键
- metaregistry_id: 关联carbon_metaregistry表的id
- tenant_id: 租户ID
- project_introduction: 项目介绍

## 配置说明

### 最小配置（只需MySQL和Redis）

**application-dev.yml：**
```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/carbon?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 你的MySQL密码  # 修改这里
  
  redis:
    host: 127.0.0.1
    port: 6379
```

### RocketMQ配置（可选）

如果需要使用RocketMQ发送飞书审批消息，取消注释：
```yaml
rocketmq:
  name-server: 127.0.0.1:9876
  producer:
    group: carbon-assets
    send-message-timeout: 300000
```

## 启动应用

### 方式一：使用Maven启动

```bash
cd /Users/mengxianze/Desktop/project/cursor-design-1/backend/carbon-assets
mvn spring-boot:run
```

### 方式二：使用IDE启动

直接运行 `CarbonAssetsApplication.java` 的 main 方法

## 接口测试

### 1. 碳信用分页列表接口

**请求示例：**
```bash
curl -X POST http://127.0.0.1:9093/assets/carbonCreditAssets/getPageList \
  -H "Content-Type: application/json" \
  -d '{"asc":true,"current":1,"size":10}'
```

### 2. 添加碳信用资产接口

**请求示例：**
```bash
curl -X POST http://127.0.0.1:9093/assets/carbonCreditAssets/add \
  -H "Content-Type: application/json" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -d '{
    "carbonProjectId": 1,
    "total": 1000,
    "issuingDate": "2024-01-01",
    "expiryDate": "2025-01-01"
  }'
```

## 注意事项

1. **数据库表**: 确保数据库中存在以下表：
   - `carbon_credit_assets`
   - `carbon_metaregistry`
   - `carbon_metaregistry_project`
   - `carbon_exchange`（可选）
   - `carbon_methodology`（可选）

2. **RocketMQ**: 如果未配置RocketMQ，飞书审批消息发送功能将被跳过，不会影响其他功能

3. **Feign客户端**: SystemServiceApi和ChainMakerServiceApi为可选服务，如果未配置，相关功能将被跳过

4. **端口**: 默认端口为9093，可在`application.yml`中修改

## 开发团队

- **开发时间**: 2026-01-06
- **技术支持**: Carbon Team

