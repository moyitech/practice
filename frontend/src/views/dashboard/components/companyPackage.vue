<template>
  <div class="company-package">
    <!-- 顶部三个卡片 -->
    <div class="top-cards">
      <!-- 资产收入 -->
      <div class="card income-card">
        <div class="card-header">
          <span class="card-title">资产收入 上月({{ lastMonth }})</span>
        </div>
        <div class="card-content">
          <div class="main-value">{{ formatNumber(assetsIncome?.totalIncome || 0) }} CNC</div>
          <div class="ratio-row">
            <span class="ratio-item">
              <span class="label">环比</span>
              <span class="value">{{ formatRatio(assetsIncome?.monthOnMonthRatio || 0) }}%</span>
              <i class="el-icon-arrow-up"></i>
            </span>
            <span class="ratio-item">
              <span class="label">同比</span>
              <span class="value">{{ formatRatio(assetsIncome?.yearOnYearRatio || 0) }}%</span>
              <i class="el-icon-arrow-up"></i>
            </span>
          </div>
          <div class="detail-row">
            <span>总收入: ¥{{ formatNumber(assetsIncome?.totalIncome || 0) }}</span>
            <span>碳信用: {{ formatNumber(carbonCredit || 0) }} tCO2e</span>
          </div>
        </div>
      </div>

      <!-- 资金收入 -->
      <div class="card income-card">
        <div class="card-header">
          <span class="card-title">资金收入 上月({{ lastMonth }})</span>
        </div>
        <div class="card-content">
          <div class="main-value">{{ formatNumber(fundIncome?.totalIncome || 0) }} ¥</div>
          <div class="ratio-row">
            <span class="ratio-item">
              <span class="label">环比</span>
              <span class="value">{{ formatRatio(fundIncome?.monthOnMonthRatio || 0) }}%</span>
              <i class="el-icon-arrow-up"></i>
            </span>
            <span class="ratio-item">
              <span class="label">同比</span>
              <span class="value">{{ formatRatio(fundIncome?.yearOnYearRatio || 0) }}%</span>
              <i class="el-icon-arrow-up"></i>
            </span>
          </div>
          <div class="detail-row">
            <span>总收入: ¥{{ formatNumber(fundIncome?.totalIncome || 0) }}</span>
            <span>碳配额: {{ formatNumber(carbonQuota || 0) }} tCO2e</span>
          </div>
        </div>
      </div>

      <!-- 账户信息 -->
      <div class="card account-card">
        <div class="account-info">
          <div class="account-avatar">
            <img v-if="accountVo?.avatar" :src="accountVo.avatar" alt="avatar" />
            <div v-else class="avatar-circle">{{ getAccountInitial(accountVo?.accountName) }}</div>
          </div>
          <div class="account-details">
            <div class="account-name">{{ accountVo?.accountName || 'admin' }}</div>
            <div class="account-tags">
              <span class="tag" v-if="accountVo?.roleNames && accountVo.roleNames.length > 0">
                {{ accountVo.roleNames[0] }}
              </span>
              <span class="tag">{{ getProductVersionName(accountVo?.productVersion) }}</span>
            </div>
            <div class="enterprise-name">{{ accountVo?.enterpriseName || '青软集团' }}</div>
            <div class="account-meta">
              <span>碳中和比例: {{ accountVo?.carbonNeutralRatio || '--' }}</span>
              <span>ESG评分: {{ accountVo?.esgScore || '--' }}</span>
            </div>
            <div class="account-validity">
              账户有效期: {{ accountVo?.expired ? '已过期' : (formatDate(accountVo?.accountValidity) || '--') }}
            </div>
          </div>
        </div>
        <div class="plant-decoration">🌱</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CompanyPackage',
  props: {
    accountVo: {
      type: Object,
      default: () => ({})
    },
    assetsIncome: {
      type: Object,
      default: () => ({})
    },
    fundIncome: {
      type: Object,
      default: () => ({})
    },
    carbonCredit: {
      type: [Number, String],
      default: 0
    },
    carbonQuota: {
      type: [Number, String],
      default: 0
    },
    greenScore: {
      type: [Number, String],
      default: 0
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
    formatRatio(value) {
      if (!value && value !== 0) return '0'
      const num = typeof value === 'string' ? parseFloat(value) : value
      if (isNaN(num)) return '0'
      return num.toFixed(0)
    },
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    },
    getProductVersionName(version) {
      const versionMap = {
        '0400000001': '试用版',
        '0400000002': '标准版',
        '0400000003': '专业版',
        '0400000004': '企业版'
      }
      return versionMap[version] || '专业版'
    },
    getAccountInitial(name) {
      if (!name) return 'A'
      return name.charAt(0).toUpperCase()
    }
  }
}
</script>

<style scoped>
.company-package {
  margin-bottom: 20px;
}

.top-cards {
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

.income-card .main-value {
  font-size: 32px;
  font-weight: 600;
  color: #172B4D;
  margin: 15px 0;
}

.ratio-row {
  display: flex;
  gap: 30px;
  margin: 15px 0;
}

.ratio-item {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
}

.ratio-item .label {
  color: #909399;
}

.ratio-item .value {
  color: #27A777;
  font-weight: 500;
}

.ratio-item i {
  color: #27A777;
  font-size: 12px;
}

.detail-row {
  display: flex;
  flex-direction: column;
  gap: 8px;
  font-size: 14px;
  color: #5E6C84;
  margin-top: 15px;
}

/* 账户卡片 */
.account-card {
  position: relative;
  display: flex;
  align-items: center;
}

.account-info {
  flex: 1;
  display: flex;
  gap: 15px;
}

.account-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  background: linear-gradient(135deg, #27A777 0%, #76DFB7 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.account-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-circle {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
  font-weight: 500;
}

.account-details {
  flex: 1;
}

.account-name {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin-bottom: 8px;
}

.account-tags {
  display: flex;
  gap: 8px;
  margin-bottom: 8px;
}

.tag {
  padding: 4px 12px;
  background: #E5F9F0;
  color: #27A777;
  border-radius: 12px;
  font-size: 12px;
}

.enterprise-name {
  font-size: 14px;
  color: #5E6C84;
  margin-bottom: 8px;
}

.account-meta {
  display: flex;
  gap: 20px;
  font-size: 12px;
  color: #909399;
  margin-bottom: 8px;
}

.account-validity {
  font-size: 12px;
  color: #F56C6C;
}

.plant-decoration {
  font-size: 60px;
  opacity: 0.3;
  position: absolute;
  right: 20px;
  bottom: 20px;
}
</style>

