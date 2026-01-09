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
          <h2>碳资产管理</h2>
          <p class="page-description">管理和查看您的碳资产信息，包括碳配额、碳信用等资产详情</p>
        </div>

        <!-- 统计卡片 -->
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-wallet" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">总资产价值</div>
              <div class="stat-value">¥ 1,234,567.89</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-document" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">碳配额总量</div>
              <div class="stat-value">12,345.67 tCO₂</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-coin" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">碳信用总量</div>
              <div class="stat-value">8,901.23 tCO₂</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: #E5F9F0;">
              <i class="el-icon-trophy" style="color: #27A777;"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">绿色积分</div>
              <div class="stat-value">9,876</div>
            </div>
          </div>
        </div>

        <!-- 资产列表 -->
        <div class="content-card">
          <div class="card-header">
            <h3>资产列表</h3>
            <el-button type="primary" size="small">
              <i class="el-icon-plus"></i> 新增资产
            </el-button>
          </div>
          <el-table :data="assetList" style="width: 100%" stripe>
            <el-table-column prop="assetName" label="资产名称" width="200"></el-table-column>
            <el-table-column prop="assetType" label="资产类型" width="150">
              <template slot-scope="scope">
                <el-tag :type="scope.row.assetType === '配额' ? 'success' : 'primary'">
                  {{ scope.row.assetType }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="amount" label="数量 (tCO₂)" width="150"></el-table-column>
            <el-table-column prop="value" label="价值 (¥)" width="150"></el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template slot-scope="scope">
                <el-tag :type="scope.row.status === '有效' ? 'success' : 'info'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="text" size="small">查看</el-button>
                <el-button type="text" size="small">编辑</el-button>
                <el-button type="text" size="small" style="color: #f56c6c;">删除</el-button>
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
  name: 'CarbonAssetsManage',
  data() {
    return {
      accountInfo: {},
      assetList: [
        {
          assetName: 'CCER项目-光伏发电',
          assetType: '碳信用',
          amount: '5,000.00',
          value: '125,000.00',
          status: '有效',
          createTime: '2024-01-15 10:30:00'
        },
        {
          assetName: '全国碳排放配额',
          assetType: '配额',
          amount: '3,500.00',
          value: '87,500.00',
          status: '有效',
          createTime: '2024-01-10 14:20:00'
        },
        {
          assetName: 'CCER项目-风力发电',
          assetType: '碳信用',
          amount: '2,800.00',
          value: '70,000.00',
          status: '有效',
          createTime: '2024-01-08 09:15:00'
        },
        {
          assetName: 'CCER项目-生物质能',
          assetType: '碳信用',
          amount: '1,100.00',
          value: '27,500.00',
          status: '有效',
          createTime: '2024-01-05 16:45:00'
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

