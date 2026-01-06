<template>
  <div class="trade-info">
    <div class="trade-info-section">
      <h3 class="section-title">碳交易行情</h3>
      <div class="trade-content">
        <div v-if="!tradeInfo || (Array.isArray(tradeInfo) && tradeInfo.length === 0)" class="empty-data">
          暂无交易行情数据
        </div>
        <div v-else class="trade-list">
          <div v-for="(item, index) in (Array.isArray(tradeInfo) ? tradeInfo : [])" :key="index" class="trade-item">
            <div class="trade-header">
              <span class="trade-type">{{ item.type || '未知类型' }}</span>
            </div>
            <div class="trade-details" v-if="item.projects && item.projects.length > 0">
              <div v-for="(project, pIndex) in item.projects" :key="pIndex" class="project-item">
                <span class="project-label">签发量:</span>
                <span class="project-value">{{ project.count || 0 }}</span>
                <span class="project-label">存量:</span>
                <span class="project-value">{{ project.stockCount || 0 }}</span>
                <span class="project-label">项目数:</span>
                <span class="project-value">{{ project.singCount || 0 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TradeInfo',
  props: {
    tradeInfo: {
      type: [Array, Object],
      default: () => []
    }
  }
}
</script>

<style scoped>
.trade-info {
  margin-bottom: 20px;
}

.trade-info-section {
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

.trade-content {
  min-height: 100px;
}

.empty-data {
  text-align: center;
  color: #909399;
  padding: 40px;
}

.trade-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.trade-item {
  padding: 15px;
  background: #f5f7fa;
  border-radius: 6px;
  border-left: 3px solid #27A777;
}

.trade-header {
  margin-bottom: 10px;
}

.trade-type {
  font-size: 16px;
  font-weight: 600;
  color: #172B4D;
}

.trade-details {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.project-item {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}

.project-label {
  color: #5E6C84;
}

.project-value {
  color: #27A777;
  font-weight: 500;
}
</style>

