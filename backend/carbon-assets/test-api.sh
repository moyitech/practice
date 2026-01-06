#!/bin/bash

# 碳信用资产接口测试脚本
# 使用前请确保服务已启动在 http://127.0.0.1:9093

BASE_URL="http://127.0.0.1:9093/assets/carbonCreditAssets"

echo "=========================================="
echo "碳信用资产接口测试"
echo "=========================================="
echo ""

# 1. 测试分页列表接口
echo "1. 测试分页列表接口"
echo "请求: POST ${BASE_URL}/getPageList"
curl -X POST "${BASE_URL}/getPageList" \
  -H "Content-Type: application/json" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -d '{"asc":true,"current":1,"size":10}' \
  -w "\n状态码: %{http_code}\n" \
  -s
echo ""
echo "----------------------------------------"
echo ""

# 2. 测试获取资产总额接口
echo "2. 测试获取资产总额接口"
echo "请求: GET ${BASE_URL}/assetsTotal"
curl -X GET "${BASE_URL}/assetsTotal" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -w "\n状态码: %{http_code}\n" \
  -s
echo ""
echo "----------------------------------------"
echo ""

# 3. 测试添加碳信用资产接口（需要先有carbon_metaregistry数据）
echo "3. 测试添加碳信用资产接口"
echo "请求: POST ${BASE_URL}/add"
echo "注意: 此接口需要数据库中存在carbon_metaregistry表的数据"
curl -X POST "${BASE_URL}/add" \
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
  }' \
  -w "\n状态码: %{http_code}\n" \
  -s
echo ""
echo "----------------------------------------"
echo ""

# 4. 测试获取详情接口（需要先有数据）
echo "4. 测试获取详情接口"
echo "请求: GET ${BASE_URL}/info/1"
curl -X GET "${BASE_URL}/info/1" \
  -H "accountId: 1" \
  -H "tenantId: 1" \
  -w "\n状态码: %{http_code}\n" \
  -s
echo ""
echo "----------------------------------------"
echo ""

echo "=========================================="
echo "测试完成"
echo "=========================================="

