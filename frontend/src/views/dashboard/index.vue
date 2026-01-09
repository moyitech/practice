<template>
  <div class="dashboard-container">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="sidebar-header">
        <h2>XCARBON MANAGEMENT PLATFORM</h2>
      </div>
      <div class="sidebar-menu">
        <div class="menu-item" :class="{ active: $route.path === '/home' }" @click="handleMenuClick('/home')">
          <span>首页</span>
        </div>
        <div class="menu-item" :class="{ active: $route.path === '/maintain/user' }" @click="handleMenuClick('/maintain/user')">
          <span>账户管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item" :class="{ active: $route.path === '/assets/manage' }" @click="handleMenuClick('/assets/manage')">
          <span>碳资产管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item" :class="{ active: $route.path === '/assets/develop' }" @click="handleMenuClick('/assets/develop')">
          <span>碳资产开发</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item" :class="{ active: $route.path === '/trade/manage' }" @click="handleMenuClick('/trade/manage')">
          <span>碳交易管理</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item" :class="{ active: $route.path === '/news' }" @click="handleMenuClick('/news')">
          <span>碳资讯</span>
          <i class="el-icon-arrow-down"></i>
        </div>
        <div class="menu-item" :class="{ active: $route.path === '/system/manage' }" @click="handleMenuClick('/system/manage')">
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
        <!-- 资产收入和资金收入、账户 -->
        <company-package
          :account-vo="homeData.accountVo"
          :assets-income="homeData.assetsIncome"
          :fund-income="homeData.fundIncome"
          :carbon-credit="homeData.carbonCredit"
          :carbon-quota="homeData.carbonQuota"
          :green-score="homeData.greenScore"
          :last-month="lastMonth"
        />

        <!-- 供应量、开发量、销售量 -->
        <capital-stat
          :month-supply="homeData.monthSupply"
          :month-development="homeData.monthDevelopment"
          :month-sales="homeData.monthSales"
          :last-month="lastMonth"
        />

        <!-- 项目统计 -->
        <project-statistical :project-stat="homeData.projectStat" />

        <!-- 碳交易行情 -->
        <trade-info :trade-info="homeData.quotation" />

        <!-- 碳行业资讯 -->
        <news-container />

        <!-- 常用功能 -->
        <commom-usage-grid />
      </div>
    </div>
  </div>
</template>

<script>
import { getHomeData } from '@/api/dataPanel'
import { logout } from '@/api/user'
import CompanyPackage from './components/companyPackage.vue'
import CapitalStat from './components/capitalStat.vue'
import ProjectStatistical from './components/projectStatistical.vue'
import TradeInfo from './components/tradeInfo.vue'
import NewsContainer from './components/newsContainer.vue'
import CommomUsageGrid from './components/commomUsageGrid.vue'

export default {
  name: 'Dashboard',
  components: {
    CompanyPackage,
    CapitalStat,
    ProjectStatistical,
    TradeInfo,
    NewsContainer,
    CommomUsageGrid
  },
  data() {
    return {
      loading: false,
      homeData: {},
      accountInfo: {},
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
    },
    handleMenuClick(path) {
      if (this.$route.path !== path) {
        this.$router.push(path)
      }
    }
  }
}
</script>

<style scoped>
.dashboard-container {
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
</style>

