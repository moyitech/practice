<template>
  <div class="home-container">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="sidebar-header">
        <h2>XCARBON MANAGEMENT PLATFORM</h2>
      </div>
      <div class="sidebar-menu">
        <div class="menu-item active">
          <span>首页</span>
        </div>
        <div class="menu-item">
          <span>账户管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item">
          <span>碳资产管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item">
          <span>碳资产开发</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item">
          <span>碳交易管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item">
          <span>碳资讯</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item">
          <span>系统管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
      </div>
    </div>

    <!-- 主内容区域 -->
    <div class="main-content">
      <!-- 顶部头部 -->
      <div class="header">
        <div class="header-right">
          <i class="el-icon-question"></i>
          <el-dropdown>
            <span class="user-dropdown">
              {{ accountInfo.accountName || 'admin' }}
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <div class="avatar">
            <img v-if="accountInfo.avatar" :src="accountInfo.avatar" alt="avatar" />
            <div v-else class="avatar-placeholder">{{ (accountInfo.accountName || 'A').charAt(0).toUpperCase() }}</div>
          </div>
        </div>
      </div>

      <!-- 内容区域 -->
      <div class="content-wrapper" v-loading="loading">
        <!-- 顶部三个卡片 -->
        <div class="top-cards">
          <!-- 资产收入 -->
          <div class="card income-card">
            <div class="card-header">
              <span class="card-title">资产收入 上月({{ lastMonth }})</span>
            </div>
            <div class="card-content">
              <div class="main-value">{{ formatNumber(homeData.assetsIncome?.totalIncome || 0) }} CNC</div>
              <div class="ratio-row">
                <span class="ratio-item">
                  <span class="label">环比</span>
                  <span class="value">{{ formatRatio(homeData.assetsIncome?.monthOnMonthRatio || 0) }}%</span>
                  <i class="el-icon-arrow-up"></i>
                </span>
                <span class="ratio-item">
                  <span class="label">同比</span>
                  <span class="value">{{ formatRatio(homeData.assetsIncome?.yearOnYearRatio || 0) }}%</span>
                  <i class="el-icon-arrow-up"></i>
                </span>
              </div>
              <div class="detail-row">
                <span>总收入: ¥{{ formatNumber(homeData.assetsIncome?.totalIncome || 0) }}</span>
                <span>碳信用: {{ formatNumber(homeData.carbonCredit || 0) }} tCO2e</span>
              </div>
            </div>
          </div>

          <!-- 资金收入 -->
          <div class="card income-card">
            <div class="card-header">
              <span class="card-title">资金收入 上月({{ lastMonth }})</span>
            </div>
            <div class="card-content">
              <div class="main-value">{{ formatNumber(homeData.fundIncome?.totalIncome || 0) }} ¥</div>
              <div class="ratio-row">
                <span class="ratio-item">
                  <span class="label">环比</span>
                  <span class="value">{{ formatRatio(homeData.fundIncome?.monthOnMonthRatio || 0) }}%</span>
                  <i class="el-icon-arrow-up"></i>
                </span>
                <span class="ratio-item">
                  <span class="label">同比</span>
                  <span class="value">{{ formatRatio(homeData.fundIncome?.yearOnYearRatio || 0) }}%</span>
                  <i class="el-icon-arrow-up"></i>
                </span>
              </div>
              <div class="detail-row">
                <span>总收入: ¥{{ formatNumber(homeData.fundIncome?.totalIncome || 0) }}</span>
                <span>碳配额: {{ formatNumber(homeData.carbonQuota || 0) }} tCO2e</span>
              </div>
            </div>
          </div>

          <!-- 账户信息 -->
          <div class="card account-card">
            <div class="account-info">
              <div class="account-avatar">
                <img v-if="accountInfo.avatar" :src="accountInfo.avatar" alt="avatar" />
                <div v-else class="avatar-circle">{{ (accountInfo.accountName || 'A').charAt(0).toUpperCase() }}</div>
              </div>
              <div class="account-details">
                <div class="account-name">{{ accountInfo.accountName || 'admin' }}</div>
                <div class="account-tags">
                  <span class="tag" v-if="accountInfo.roleNames && accountInfo.roleNames.length > 0">
                    {{ accountInfo.roleNames[0] }}
                  </span>
                  <span class="tag">{{ getProductVersionName(accountInfo.productVersion) }}</span>
                </div>
                <div class="enterprise-name">{{ accountInfo.enterpriseName || '青软集团' }}</div>
                <div class="account-meta">
                  <span>碳中和比例: {{ accountInfo.carbonNeutralRatio || '--' }}</span>
                  <span>ESG评分: {{ accountInfo.esgScore || '--' }}</span>
                </div>
                <div class="account-validity">
                  账户有效期: {{ accountInfo.expired ? '已过期' : (formatDate(accountInfo.accountValidity) || '--') }}
                </div>
              </div>
            </div>
            <div class="plant-decoration">🌱</div>
          </div>
        </div>

        <!-- 中间三个卡片：供应量、开发量、销售量 -->
        <div class="middle-cards">
          <!-- 供应量 -->
          <div class="card supply-card">
            <div class="card-header">
              <span class="card-title">供应量 上月({{ lastMonth }})</span>
            </div>
            <div class="card-content">
              <div class="circular-progress">
                <div class="progress-value">{{ formatNumber(getTotalSupply(homeData.monthSupply)) || 0 }} CNC</div>
                <div class="progress-circle">
                  <svg width="120" height="120">
                    <circle cx="60" cy="60" r="50" stroke="#E5F9F0" stroke-width="8" fill="none"></circle>
                    <circle cx="60" cy="60" r="50" stroke="#27A777" stroke-width="8" fill="none"
                      :stroke-dasharray="getProgressDashArray(homeData.monthSupply)"
                      transform="rotate(-90 60 60)"></circle>
                  </svg>
                </div>
              </div>
              <div class="supply-details">
                <div class="detail-item">
                  <span>碳信用: {{ formatNumber(homeData.monthSupply?.carbonCredit || 0) }} tCO2e</span>
                  <span>碳配额: {{ formatNumber(homeData.monthSupply?.carbonQuota || 0) }} tCO2e</span>
                  <span>绿证: {{ formatNumber(homeData.monthSupply?.greenScore || 0) }} kWh</span>
                </div>
                <div class="detail-item">
                  <span>总供应量: {{ formatNumber(homeData.monthSupply?.carbonSupplyTotal || 0) }} CNC</span>
                  <span>碳资产估值: ¥{{ formatNumber(homeData.monthSupply?.carbonValuation || 0) }}</span>
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
                <div class="progress-value">{{ formatNumber(getTotalSupply(homeData.monthDevelopment)) || 0 }} CNC</div>
                <div class="progress-circle">
                  <svg width="120" height="120">
                    <circle cx="60" cy="60" r="50" stroke="#E5F9F0" stroke-width="8" fill="none"></circle>
                    <circle cx="60" cy="60" r="50" stroke="#27A777" stroke-width="8" fill="none"
                      :stroke-dasharray="getProgressDashArray(homeData.monthDevelopment)"
                      transform="rotate(-90 60 60)"></circle>
                  </svg>
                </div>
              </div>
              <div class="supply-details">
                <div class="detail-item">
                  <span>碳信用: {{ formatNumber(homeData.monthDevelopment?.carbonCredit || 0) }} tCO2e</span>
                  <span>碳配额: {{ formatNumber(homeData.monthDevelopment?.carbonQuota || 0) }} tCO2e</span>
                  <span>绿证: {{ formatNumber(homeData.monthDevelopment?.greenScore || 0) }} kWh</span>
                </div>
                <div class="detail-item">
                  <span>总供应量: {{ formatNumber(homeData.monthDevelopment?.carbonSupplyTotal || 0) }} CNC</span>
                  <span>碳资产估值: ¥{{ formatNumber(homeData.monthDevelopment?.carbonValuation || 0) }}</span>
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
                <div class="progress-value">{{ formatNumber(getTotalSupply(homeData.monthSales)) || 0 }} CNC</div>
                <div class="progress-circle">
                  <svg width="120" height="120">
                    <circle cx="60" cy="60" r="50" stroke="#E5F9F0" stroke-width="8" fill="none"></circle>
                    <circle cx="60" cy="60" r="50" stroke="#27A777" stroke-width="8" fill="none"
                      :stroke-dasharray="getProgressDashArray(homeData.monthSales)"
                      transform="rotate(-90 60 60)"></circle>
                  </svg>
                </div>
              </div>
              <div class="supply-details">
                <div class="detail-item">
                  <span>碳信用: {{ formatNumber(homeData.monthSales?.carbonCredit || 0) }} tCO2e</span>
                  <span>碳配额: {{ formatNumber(homeData.monthSales?.carbonQuota || 0) }} tCO2e</span>
                  <span>绿证: {{ formatNumber(homeData.monthSales?.greenScore || 0) }} kWh</span>
                </div>
                <div class="detail-item">
                  <span>总供应量: {{ formatNumber(homeData.monthSales?.carbonSupplyTotal || 0) }} CNC</span>
                  <span>碳资产估值: ¥{{ formatNumber(homeData.monthSales?.carbonValuation || 0) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 项目开发进展 -->
        <div class="project-progress-section">
          <h3 class="section-title">项目开发进展</h3>
          <div class="progress-steps">
            <div class="progress-step active">
              <div class="step-header">
                <span class="step-number">01</span>
                <span class="step-title">项目立项</span>
              </div>
              <div class="step-tasks">
                <div class="task-item">
                  <span>选择方法学</span>
                  <el-link type="success" :underline="false">去选择</el-link>
                </div>
                <div class="task-item">
                  <span>创建项目</span>
                  <el-link type="success" :underline="false">去创建</el-link>
                </div>
                <div class="task-item">
                  <span>上传业主资料</span>
                  <el-link type="success" :underline="false">去上传</el-link>
                </div>
                <div class="task-item">
                  <span>查看项目情况</span>
                  <el-link type="success" :underline="false">去查看</el-link>
                </div>
              </div>
            </div>
            <div class="progress-step">
              <div class="step-header">
                <span class="step-number">02</span>
                <span class="step-title">项目监测</span>
              </div>
              <div class="step-tasks">
                <div class="task-item">
                  <span>选择项目</span>
                  <el-link type="info" :underline="false">去选择</el-link>
                </div>
                <div class="task-item">
                  <span>添加监测数据</span>
                  <el-link type="info" :underline="false">去添加</el-link>
                </div>
                <div class="task-item">
                  <span>查看监测情况</span>
                  <el-link type="info" :underline="false">去查看</el-link>
                </div>
              </div>
            </div>
            <div class="progress-step">
              <div class="step-header">
                <span class="step-number">03</span>
                <span class="step-title">项目开发</span>
              </div>
              <div class="step-tasks">
                <div class="task-item">
                  <span>选择项目</span>
                  <el-link type="info" :underline="false">去选择</el-link>
                </div>
                <div class="task-item">
                  <span>编写项目设计文档</span>
                  <el-link type="info" :underline="false">去编写</el-link>
                </div>
                <div class="task-item">
                  <span>查看开发进展</span>
                  <el-link type="info" :underline="false">去查看</el-link>
                </div>
              </div>
            </div>
            <div class="progress-step">
              <div class="step-header">
                <span class="step-number">04</span>
                <span class="step-title">项目材料</span>
              </div>
              <div class="step-tasks">
                <div class="task-item">
                  <span>项目审定报告</span>
                  <el-link type="info" :underline="false">去上传</el-link>
                </div>
                <div class="task-item">
                  <span>项目备案书</span>
                  <el-link type="info" :underline="false">去上传</el-link>
                </div>
                <div class="task-item">
                  <span>项目核证报告</span>
                  <el-link type="info" :underline="false">去上传</el-link>
                </div>
                <div class="task-item">
                  <span>碳减排量核证签发</span>
                  <el-link type="info" :underline="false">去上传</el-link>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 项目统计 -->
        <div class="project-stat-section">
          <div class="stat-left">
            <div class="stat-circle">
              <div class="circle-value">{{ formatNumber(projectStat.reductionTotal) || 0 }}</div>
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
                  <tr v-if="!projectStat.projectList || projectStat.projectList.length === 0">
                    <td colspan="4" class="empty-data">暂无项目数据</td>
                  </tr>
                  <tr v-for="(project, index) in projectStat.projectList" :key="index">
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
                <span class="summary-value">{{ formatNumber(projectStat.reductionTotal) || 0 }} tCO2e</span>
              </div>
              <div class="summary-item">
                <span class="summary-label">签约数:</span>
                <span class="summary-value">{{ projectStat.singCount || 0 }}</span>
              </div>
              <div class="summary-item">
                <span class="summary-label">备案数:</span>
                <span class="summary-value">{{ projectStat.filingCount || 0 }}</span>
              </div>
              <div class="summary-item">
                <span class="summary-label">核准数:</span>
                <span class="summary-value">{{ projectStat.approvedCount || 0 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getHomeData } from '@/api/dataPanel'
import { logout } from '@/api/user'

export default {
  name: 'Home',
  data() {
    return {
      loading: false,
      homeData: {},
      accountInfo: {},
      projectStat: {
        projectList: []
      },
      lastMonth: ''
    }
  },
  created() {
    this.loadUserInfo()
    this.loadHomeData()
    this.calculateLastMonth()
  },
  methods: {
    loadUserInfo() {
      const userInfoStr = localStorage.getItem('userInfo')
      if (userInfoStr) {
        this.accountInfo = JSON.parse(userInfoStr)
      } else {
        this.$router.push('/login')
      }
    },
    async loadHomeData() {
      this.loading = true
      try {
        const res = await getHomeData()
        if (res.code === 200 && res.data) {
          this.homeData = res.data
          this.accountInfo = res.data.accountVo || this.accountInfo
          this.projectStat = res.data.projectStat || { projectList: [] }
        }
      } catch (error) {
        this.$message.error('加载数据失败')
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    calculateLastMonth() {
      const now = new Date()
      const lastMonth = new Date(now.getFullYear(), now.getMonth() - 1, 1)
      const monthNames = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
      this.lastMonth = monthNames[lastMonth.getMonth()]
    },
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
    },
    handleLogout() {
      logout().then(() => {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        this.$message.success('退出登录成功')
        this.$router.push('/login')
      }).catch(() => {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        this.$router.push('/login')
      })
    }
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  height: 100vh;
  background: #f5f7fa;
}

/* 左侧导航栏 */
.sidebar {
  width: 240px;
  background: #fff;
  border-right: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.sidebar-header h2 {
  margin: 0;
  font-size: 14px;
  font-weight: 500;
  color: #172B4D;
  letter-spacing: 0.5px;
}

.sidebar-menu {
  flex: 1;
  padding: 10px 0;
}

.menu-item {
  padding: 12px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  color: #5E6C84;
  font-size: 14px;
  transition: background-color 0.3s;
}

.menu-item:hover {
  background: #f5f7fa;
}

.menu-item.active {
  background: #E5F9F0;
  color: #27A777;
  border-left: 3px solid #27A777;
}

.menu-item i {
  font-size: 12px;
  color: #909399;
}

/* 主内容区域 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  height: 60px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 30px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-right i {
  font-size: 20px;
  color: #909399;
  cursor: pointer;
}

.user-dropdown {
  cursor: pointer;
  color: #172B4D;
  font-size: 14px;
}

.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
  background: #E5F9F0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-placeholder {
  color: #27A777;
  font-weight: 500;
}

/* 内容区域 */
.content-wrapper {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

/* 卡片通用样式 */
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

/* 顶部三个卡片 */
.top-cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 20px;
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

/* 中间三个卡片 */
.middle-cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 20px;
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

/* 项目开发进展 */
.project-progress-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin-bottom: 20px;
}

.progress-steps {
  display: flex;
  gap: 20px;
  overflow-x: auto;
}

.progress-step {
  flex: 1;
  min-width: 200px;
  padding: 15px;
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  background: #fafafa;
}

.progress-step.active {
  border-color: #27A777;
  background: #E5F9F0;
}

.step-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e4e7ed;
}

.step-number {
  font-size: 16px;
  font-weight: 600;
  color: #27A777;
}

.step-title {
  font-size: 14px;
  font-weight: 500;
  color: #172B4D;
}

.step-tasks {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.task-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  color: #5E6C84;
}

/* 项目统计 */
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
