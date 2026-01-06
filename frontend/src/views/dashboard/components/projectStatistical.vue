<template>
  <div class="project-statistical">
    <div class="project-stat-section">
      <div class="stat-left">
        <div class="stat-circle">
          <div class="circle-value">{{ formatNumber(projectStat?.reductionTotal) || 0 }}</div>
          <div class="circle-label">预计减排量</div>
          <div class="circle-unit">tCO2e</div>
        </div>
      </div>
      <div class="stat-right">
        <div class="stat-table">
          <table>
            <thead>
              <tr>
                <th>项目名称</th>
                <th>预计减排量(tCO2e)</th>
                <th>碳资产估值(¥)</th>
                <th>项目状态</th>
              </tr>
            </thead>
            <tbody>
              <tr v-if="!projectStat?.projectList || projectStat.projectList.length === 0">
                <td colspan="4" class="empty-data">暂无项目数据</td>
              </tr>
              <tr v-for="(project, index) in projectStat?.projectList" :key="index">
                <td>{{ project.projectName }}</td>
                <td>{{ project.reduction }}</td>
                <td>{{ formatNumber(project.carbonValuation) }}</td>
                <td>
                  <span class="status-tag">{{ project.developmentState || '已开发' }}</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="stat-summary">
          <div class="summary-item">
            <span class="summary-label">减排总量:</span>
            <span class="summary-value">{{ formatNumber(projectStat?.reductionTotal) || 0 }} tCO2e</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">签约数:</span>
            <span class="summary-value">{{ projectStat?.singCount || 0 }}</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">备案数:</span>
            <span class="summary-value">{{ projectStat?.filingCount || 0 }}</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">核准数:</span>
            <span class="summary-value">{{ projectStat?.approvedCount || 0 }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProjectStatistical',
  props: {
    projectStat: {
      type: Object,
      default: () => ({
        projectList: []
      })
    }
  },
  methods: {
    formatNumber(value) {
      if (!value && value !== 0) return '0'
      const num = typeof value === 'string' ? parseFloat(value) : value
      if (isNaN(num)) return '0'
      return num.toLocaleString('zh-CN', { maximumFractionDigits: 2 })
    }
  }
}
</script>

<style scoped>
.project-statistical {
  margin-bottom: 20px;
}

.project-stat-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 30px;
}

.stat-left {
  flex-shrink: 0;
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-circle {
  width: 180px;
  height: 180px;
  border-radius: 50%;
  background: linear-gradient(135deg, #27A777 0%, #76DFB7 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.circle-value {
  font-size: 32px;
  font-weight: 600;
  margin-bottom: 5px;
}

.circle-label {
  font-size: 14px;
  margin-bottom: 5px;
}

.circle-unit {
  font-size: 12px;
  opacity: 0.9;
}

.stat-right {
  flex: 1;
}

.stat-table {
  margin-bottom: 20px;
}

.stat-table table {
  width: 100%;
  border-collapse: collapse;
}

.stat-table th {
  background: #f5f7fa;
  padding: 12px;
  text-align: left;
  font-size: 14px;
  font-weight: 500;
  color: #172B4D;
  border-bottom: 1px solid #e4e7ed;
}

.stat-table td {
  padding: 12px;
  font-size: 14px;
  color: #5E6C84;
  border-bottom: 1px solid #f0f0f0;
}

.stat-table tr:hover {
  background: #f5f7fa;
}

.empty-data {
  text-align: center;
  color: #909399;
  padding: 40px;
}

.status-tag {
  padding: 4px 12px;
  background: #E5F9F0;
  color: #27A777;
  border-radius: 12px;
  font-size: 12px;
}

.stat-summary {
  display: flex;
  gap: 30px;
  padding-top: 15px;
  border-top: 1px solid #e4e7ed;
}

.summary-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.summary-label {
  font-size: 12px;
  color: #909399;
}

.summary-value {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
}
</style>

