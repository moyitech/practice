<template>
  <div class="commom-usage-grid">
    <div class="usage-section">
      <h3 class="section-title">常用功能</h3>
      <div class="grid-container">
        <router-link to="/project/create" class="grid-item">
          <div class="item-icon">
            <i class="el-icon-document-add"></i>
          </div>
          <div class="item-label">创建碳减排项目</div>
        </router-link>
        <router-link to="/project/development" class="grid-item">
          <div class="item-icon">
            <i class="el-icon-s-management"></i>
          </div>
          <div class="item-label">项目开发管理</div>
        </router-link>
        <router-link to="/methodology" class="grid-item">
          <div class="item-icon">
            <i class="el-icon-document"></i>
          </div>
          <div class="item-label">查看碳减排方法学</div>
        </router-link>
        <router-link to="/trade/market" class="grid-item">
          <div class="item-icon">
            <i class="el-icon-data-line"></i>
          </div>
          <div class="item-label">碳交易行情</div>
        </router-link>
        <router-link to="/exchange/account" class="grid-item">
          <div class="item-icon">
            <i class="el-icon-user"></i>
          </div>
          <div class="item-label">碳交易所开户</div>
        </router-link>
        <router-link to="/quote/manage" class="grid-item">
          <div class="item-icon">
            <i class="el-icon-s-order"></i>
          </div>
          <div class="item-label">询报价管理</div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CommomUsageGrid'
}
</script>

<style scoped>
.commom-usage-grid {
  margin-bottom: 20px;
}

.usage-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin-bottom: 20px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.grid-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 30px 20px;
  background: #f5f7fa;
  border-radius: 8px;
  text-decoration: none;
  color: #172B4D;
  transition: all 0.3s;
  cursor: pointer;
}

.grid-item:hover {
  background: #E5F9F0;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(39, 167, 119, 0.2);
}

.item-icon {
  font-size: 36px;
  color: #27A777;
  margin-bottom: 12px;
}

.item-label {
  font-size: 14px;
  font-weight: 500;
  text-align: center;
}
</style>

