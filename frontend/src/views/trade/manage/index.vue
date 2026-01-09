<template>
  <div class="page-container">
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
      <div class="content-wrapper">
        <div class="page-header">
          <h2>碳交易管理</h2>
          <p class="page-description">管理碳资产交易，包括买入、卖出订单和交易记录</p>
        </div>

        <!-- 交易统计 -->
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-shopping-cart-full" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">今日交易额</div>
              <div class="stat-value">¥ 125,680.00</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-shopping-cart-2" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">本月交易额</div>
              <div class="stat-value">¥ 1,256,800.00</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-document" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">待处理订单</div>
              <div class="stat-value">5</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-success" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">已完成订单</div>
              <div class="stat-value">128</div>
            </div>
          </div>
        </div>

        <!-- 交易操作 -->
        <div class="action-cards">
          <div class="action-card">
            <div class="action-icon" style="background: #E5F9F0;">
              <i class="el-icon-top" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>买入</h3>
            <p>购买碳配额或碳信用</p>
            <el-button type="primary" style="margin-top: 16px;">立即买入</el-button>
          </div>
          <div class="action-card">
            <div class="action-icon" style="background: #E5F9F0;">
              <i class="el-icon-bottom" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>卖出</h3>
            <p>出售碳配额或碳信用</p>
            <el-button type="primary" style="margin-top: 16px;">立即卖出</el-button>
          </div>
        </div>

        <!-- 交易记录 -->
        <div class="content-card">
          <div class="card-header">
            <h3>交易记录</h3>
            <el-button type="text" size="small">导出记录</el-button>
          </div>
          <el-table :data="tradeList" style="width: 100%" stripe>
            <el-table-column prop="orderNo" label="订单号" width="200"></el-table-column>
            <el-table-column prop="tradeType" label="交易类型" width="120">
              <template slot-scope="scope">
                <el-tag :type="scope.row.tradeType === '买入' ? 'success' : 'danger'">
                  {{ scope.row.tradeType }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="assetType" label="资产类型" width="150"></el-table-column>
            <el-table-column prop="amount" label="数量 (tCO₂)" width="150"></el-table-column>
            <el-table-column prop="price" label="单价 (¥)" width="150"></el-table-column>
            <el-table-column prop="totalAmount" label="总金额 (¥)" width="150"></el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template slot-scope="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="tradeTime" label="交易时间" width="180"></el-table-column>
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button type="text" size="small">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { logout } from '@/api/user'

export default {
  name: 'CarbonTradeManage',
  data() {
    return {
      accountInfo: {},
      tradeList: [
        {
          orderNo: 'TR20240120001',
          tradeType: '买入',
          assetType: '碳配额',
          amount: '1,000.00',
          price: '25.00',
          totalAmount: '25,000.00',
          status: '已完成',
          tradeTime: '2024-01-20 14:30:00'
        },
        {
          orderNo: 'TR20240119002',
          tradeType: '卖出',
          assetType: '碳信用',
          amount: '500.00',
          price: '28.00',
          totalAmount: '14,000.00',
          status: '已完成',
          tradeTime: '2024-01-19 10:15:00'
        },
        {
          orderNo: 'TR20240118003',
          tradeType: '买入',
          assetType: '碳配额',
          amount: '2,000.00',
          price: '24.50',
          totalAmount: '49,000.00',
          status: '待支付',
          tradeTime: '2024-01-18 16:45:00'
        },
        {
          orderNo: 'TR20240117004',
          tradeType: '卖出',
          assetType: '碳信用',
          amount: '800.00',
          price: '27.50',
          totalAmount: '22,000.00',
          status: '已完成',
          tradeTime: '2024-01-17 09:20:00'
        }
      ]
    }
  },
  created() {
    this.loadUserInfo()
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
    handleMenuClick(path) {
      if (this.$route.path !== path) {
        this.$router.push(path)
      }
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
    getStatusType(status) {
      const statusMap = {
        '已完成': 'success',
        '待支付': 'warning',
        '待确认': 'info',
        '已取消': 'danger'
      }
      return statusMap[status] || 'info'
    }
  }
}
</script>

<style scoped>
.page-container {
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

.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 24px;
  font-weight: 600;
  color: #172B4D;
  margin-bottom: 8px;
}

.page-description {
  color: #5E6C84;
  font-size: 14px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.stat-content {
  flex: 1;
}

.stat-label {
  font-size: 14px;
  color: #5E6C84;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 20px;
  font-weight: 600;
  color: #172B4D;
}

.action-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.action-card {
  background: #fff;
  border-radius: 8px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.action-icon {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
}

.action-card h3 {
  font-size: 20px;
  font-weight: 600;
  color: #172B4D;
  margin-bottom: 8px;
}

.action-card p {
  color: #5E6C84;
  font-size: 14px;
  margin: 0;
}

.content-card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.card-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin: 0;
}
</style>

