<template>
  <div class="capital-stat">
    <div class="middle-cards">
      <!-- 供应量 -->
      <div class="card supply-card">
        <div class="card-header">
          <span class="card-title">供应量 上月({{ lastMonth }})</span>
        </div>
        <div class="card-content">
          <div class="circular-progress">
            <div class="progress-value">{{ formatNumber(getTotalSupply(monthSupply)) || 0 }} CNC</div>
            <div class="progress-circle">
              <svg width="120" height="120">
                <circle cx="60" cy="60" r="50" stroke="#E5F9F0" stroke-width="8" fill="none"></circle>
                <circle cx="60" cy="60" r="50" stroke="#27A777" stroke-width="8" fill="none"
                  :stroke-dasharray="getProgressDashArray(monthSupply)"
                  transform="rotate(-90 60 60)"></circle>
              </svg>
            </div>
          </div>
          <div class="supply-details">
            <div class="detail-item">
              <span>碳信用: {{ formatNumber(monthSupply?.carbonCredit || 0) }} tCO2e</span>
              <span>碳配额: {{ formatNumber(monthSupply?.carbonQuota || 0) }} tCO2e</span>
              <span>绿证: {{ formatNumber(monthSupply?.greenScore || 0) }} kWh</span>
            </div>
            <div class="detail-item">
              <span>总供应量: {{ formatNumber(monthSupply?.carbonSupplyTotal || 0) }} CNC</span>
              <span>碳资产估值: ¥{{ formatNumber(monthSupply?.carbonValuation || 0) }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 开发量 -->
      <div class="card development-card">
        <div class="card-header">
          <span class="card-title">开发量 上月({{ lastMonth }})</span>
        </div>
        <div class="card-content">
          <div class="circular-progress">
            <div class="progress-value">{{ formatNumber(getTotalSupply(monthDevelopment)) || 0 }} CNC</div>
            <div class="progress-circle">
              <svg width="120" height="120">
                <circle cx="60" cy="60" r="50" stroke="#E5F9F0" stroke-width="8" fill="none"></circle>
                <circle cx="60" cy="60" r="50" stroke="#27A777" stroke-width="8" fill="none"
                  :stroke-dasharray="getProgressDashArray(monthDevelopment)"
                  transform="rotate(-90 60 60)"></circle>
              </svg>
            </div>
          </div>
          <div class="supply-details">
            <div class="detail-item">
              <span>碳信用: {{ formatNumber(monthDevelopment?.carbonCredit || 0) }} tCO2e</span>
              <span>碳配额: {{ formatNumber(monthDevelopment?.carbonQuota || 0) }} tCO2e</span>
              <span>绿证: {{ formatNumber(monthDevelopment?.greenScore || 0) }} kWh</span>
            </div>
            <div class="detail-item">
              <span>总供应量: {{ formatNumber(monthDevelopment?.carbonSupplyTotal || 0) }} CNC</span>
              <span>碳资产估值: ¥{{ formatNumber(monthDevelopment?.carbonValuation || 0) }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 销售量 -->
      <div class="card sales-card">
        <div class="card-header">
          <span class="card-title">销售量 上月({{ lastMonth }})</span>
        </div>
        <div class="card-content">
          <div class="circular-progress">
            <div class="progress-value">{{ formatNumber(getTotalSupply(monthSales)) || 0 }} CNC</div>
            <div class="progress-circle">
              <svg width="120" height="120">
                <circle cx="60" cy="60" r="50" stroke="#E5F9F0" stroke-width="8" fill="none"></circle>
                <circle cx="60" cy="60" r="50" stroke="#27A777" stroke-width="8" fill="none"
                  :stroke-dasharray="getProgressDashArray(monthSales)"
                  transform="rotate(-90 60 60)"></circle>
              </svg>
            </div>
          </div>
          <div class="supply-details">
            <div class="detail-item">
              <span>碳信用: {{ formatNumber(monthSales?.carbonCredit || 0) }} tCO2e</span>
              <span>碳配额: {{ formatNumber(monthSales?.carbonQuota || 0) }} tCO2e</span>
              <span>绿证: {{ formatNumber(monthSales?.greenScore || 0) }} kWh</span>
            </div>
            <div class="detail-item">
              <span>总供应量: {{ formatNumber(monthSales?.carbonSupplyTotal || 0) }} CNC</span>
              <span>碳资产估值: ¥{{ formatNumber(monthSales?.carbonValuation || 0) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CapitalStat',
  props: {
    monthSupply: {
      type: Object,
      default: () => ({})
    },
    monthDevelopment: {
      type: Object,
      default: () => ({})
    },
    monthSales: {
      type: Object,
      default: () => ({})
    },
    lastMonth: {
      type: String,
      default: ''
    }
  },
  methods: {
    formatNumber(value) {
      if (!value && value !== 0) return '0'
      const num = typeof value === 'string' ? parseFloat(value) : value
      if (isNaN(num)) return '0'
      return num.toLocaleString('zh-CN', { maximumFractionDigits: 2 })
    },
    getTotalSupply(data) {
      if (!data) return 0
      return (data.carbonCredit || 0) + (data.carbonQuota || 0) + (data.greenScore || 0)
    },
    getProgressDashArray(data) {
      if (!data) return '0 314'
      const total = this.getTotalSupply(data)
      const totalSupply = data.carbonSupplyTotal || 1
      const percentage = total / totalSupply
      const circumference = 2 * Math.PI * 50
      const dashLength = circumference * percentage
      return `${dashLength} ${circumference}`
    }
  }
}
</script>

<style scoped>
.capital-stat {
  margin-bottom: 20px;
}

.middle-cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.card-header {
  margin-bottom: 15px;
}

.card-title {
  font-size: 14px;
  color: #5E6C84;
  font-weight: 500;
}

.circular-progress {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  margin: 20px 0;
}

.progress-value {
  position: absolute;
  font-size: 24px;
  font-weight: 600;
  color: #172B4D;
  z-index: 1;
}

.progress-circle {
  width: 120px;
  height: 120px;
}

.supply-details {
  margin-top: 20px;
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
  font-size: 13px;
  color: #5E6C84;
  margin-bottom: 10px;
}

.detail-item span {
  display: block;
}
</style>

