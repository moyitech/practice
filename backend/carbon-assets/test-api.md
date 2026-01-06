# 碳信用资产接口测试文档

## 测试前准备

1. **启动服务**
   ```bash
   cd /Users/mengxianze/Desktop/project/cursor-design-1/backend/carbon-assets
   mvn spring-boot:run
   ```

2. **确保数据库连接正常**
   - 检查 `application-dev.yml` 中的数据库配置
   - 确保数据库中存在以下表：
     - `carbon_credit_assets`
     - `carbon_metaregistry`
     - `carbon_metaregistry_project`
     - `carbon_exchange` (可选)
     - `carbon_methodology` (可选)

## 接口测试

### 1. 分页列表接口

**接口地址**: `POST /assets/carbonCreditAssets/getPageList`

**请求示例**:
```bash
curl -X POST "http://127.0.0.1:9093/assets/carbonCreditAssets/getPageList" \
  -H "Content-Type: application/json" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -d '{
    "asc": true,
    "current": 1,
    "size": 10
  }'
```

**请求参数**:
```json
{
  "asc": true,           // 是否升序
  "current": 1,          // 当前页
  "size": 10,            // 每页大小
  "projectName": "",     // 项目名称（可选）
  "methodName": "",      // 方法名称（可选）
  "certificationCriteria": "",  // 认证标准（可选）
  "assetsStatus": "",    // 资产状态（可选）
  "transactionStatus": "", // 交易状态（可选）
  "industryCode": "",    // 行业代码（可选）
  "issuingDateStart": "", // 签发日期开始（可选）
  "issuingDateEnd": "",  // 签发日期结束（可选）
  "projectScopeCode": "" // 项目范围代码（可选）
}
```

**预期响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "current": 1,
    "size": 10,
    "total": 0,
    "pages": 0,
    "records": []
  }
}
```

### 2. 获取资产总额接口

**接口地址**: `GET /assets/carbonCreditAssets/assetsTotal`

**请求示例**:
```bash
curl -X GET "http://127.0.0.1:9093/assets/carbonCreditAssets/assetsTotal" \
  -H "accountId: 1" \
  -H "tenantId: 1"
```

**预期响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 0,
    "availableAmount": 0,
    "frozenAmount": 0,
    "lockedAmount": 0
  }
}
```

### 3. 添加碳信用资产接口

**接口地址**: `POST /assets/carbonCreditAssets/add`

**请求示例**:
```bash
curl -X POST "http://127.0.0.1:9093/assets/carbonCreditAssets/add" \
  -H "Content-Type: application/json" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -d '{
    "carbonProjectId": 1,
    "total": 1000,
    "issuingDate": "2024-01-01T00:00:00",
    "expiryDate": "2025-01-01T00:00:00",
    "issuingAgency": "生态环境部",
    "certifiedAgency": "CCER"
  }'
```

**请求参数**:
```json
{
  "carbonProjectId": 1,        // 项目ID（必填，对应carbon_metaregistry表的id）
  "total": 1000,              // 持仓总量（必填）
  "carbonExchangeId": 0,      // 交易所ID（可选）
  "certifiedAgency": "",      // 核证机构（可选）
  "issuingAgency": "",        // 签发机构（可选）
  "issuingCertificates": "",  // 签发凭证（可选）
  "issuingCertificatesFileName": "", // 签发凭证文件名（可选）
  "issuingDate": "",          // 签发日期（可选）
  "expiryDate": "",           // 有效期（可选）
  "buyTotalPrice": 0,         // 交易总价（可选）
  "buyUnitPrice": 0,          // 交易单价（可选）
  "buyDate": "",              // 交易日期（可选）
  "buyCertificate": "",       // 交易凭证（可选）
  "buyCertificateFileName": "" // 交易凭证文件名（可选）
}
```

**预期响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": true
}
```

**注意**: 
- `carbonProjectId` 必须是 `carbon_metaregistry` 表中存在的ID
- 如果项目库不存在，会返回错误

### 4. 获取详情接口

**接口地址**: `GET /assets/carbonCreditAssets/info/{id}`

**请求示例**:
```bash
curl -X GET "http://127.0.0.1:9093/assets/carbonCreditAssets/info/1" \
  -H "accountId: 1" \
  -H "tenantId: 1"
```

**预期响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "carbonProjectId": 1,
    "total": 1000,
    "availableAmount": 1000,
    "frozenAmount": 0,
    "lockedAmount": 0,
    "valuation": 50000,
    "assetsStatus": "待审批",
    "transactionStatus": "挂牌",
    ...
  }
}
```

### 5. 更新碳信用资产接口

**接口地址**: `PUT /assets/carbonCreditAssets/update`

**请求示例**:
```bash
curl -X PUT "http://127.0.0.1:9093/assets/carbonCreditAssets/update" \
  -H "Content-Type: application/json" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -d '{
    "id": 1,
    "total": 2000,
    "availableAmount": 2000
  }'
```

### 6. 删除碳信用资产接口

**接口地址**: `POST /assets/carbonCreditAssets/delete?id={id}`

**请求示例**:
```bash
curl -X POST "http://127.0.0.1:9093/assets/carbonCreditAssets/delete?id=1" \
  -H "accountId: 1" \
  -H "tenantId: 1"
```

## 使用测试脚本

运行测试脚本：
```bash
cd /Users/mengxianze/Desktop/project/cursor-design-1/backend/carbon-assets
./test-api.sh
```

## 常见问题

1. **连接被拒绝**: 确保服务已启动在端口9093
2. **数据库连接失败**: 检查 `application-dev.yml` 中的数据库配置
3. **项目库不存在**: 确保 `carbon_metaregistry` 表中有数据，且ID正确
4. **RocketMQ错误**: 如果未配置RocketMQ，相关功能会被跳过，不影响其他功能

## Swagger文档

服务启动后，可以访问Swagger文档：
- 地址: http://127.0.0.1:9093/swagger-ui.html

