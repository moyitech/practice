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
          <h2>系统管理</h2>
          <p class="page-description">管理系统配置、用户权限、数据字典等系统功能</p>
        </div>

        <!-- 功能模块 -->
        <div class="module-grid">
          <div class="module-card" @click="handleModuleClick('user')">
            <div class="module-icon" style="background: #E5F9F0;">
              <i class="el-icon-user" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>用户管理</h3>
            <p>管理系统用户账号、角色和权限</p>
          </div>
          <div class="module-card" @click="handleModuleClick('role')">
            <div class="module-icon" style="background: #E5F9F0;">
              <i class="el-icon-s-custom" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>角色管理</h3>
            <p>配置系统角色和权限分配</p>
          </div>
          <div class="module-card" @click="handleModuleClick('menu')">
            <div class="module-icon" style="background: #E5F9F0;">
              <i class="el-icon-menu" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>菜单管理</h3>
            <p>管理系统菜单和功能模块</p>
          </div>
          <div class="module-card" @click="handleModuleClick('dict')">
            <div class="module-icon" style="background: #E5F9F0;">
              <i class="el-icon-document" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>数据字典</h3>
            <p>管理系统数据字典配置</p>
          </div>
          <div class="module-card" @click="handleModuleClick('log')">
            <div class="module-icon" style="background: #E5F9F0;">
              <i class="el-icon-document-copy" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>操作日志</h3>
            <p>查看系统操作日志和审计记录</p>
          </div>
          <div class="module-card" @click="handleModuleClick('config')">
            <div class="module-icon" style="background: #E5F9F0;">
              <i class="el-icon-setting" style="color: #27A777; font-size: 32px;"></i>
            </div>
            <h3>系统配置</h3>
            <p>配置系统参数和基础设置</p>
          </div>
        </div>

        <!-- 系统信息 -->
        <div class="content-card">
          <div class="card-header">
            <h3>系统信息</h3>
          </div>
          <div class="system-info">
            <div class="info-item">
              <span class="info-label">系统版本</span>
              <span class="info-value">v1.0.0</span>
            </div>
            <div class="info-item">
              <span class="info-label">运行时间</span>
              <span class="info-value">365 天</span>
            </div>
            <div class="info-item">
              <span class="info-label">在线用户</span>
              <span class="info-value">128 人</span>
            </div>
            <div class="info-item">
              <span class="info-label">系统状态</span>
              <el-tag type="success">正常运行</el-tag>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { logout } from '@/api/user'

export default {
  name: 'SystemManage',
  data() {
    return {
      accountInfo: {}
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
    handleModuleClick(module) {
      this.$message.info(`进入${this.getModuleName(module)}模块`)
    },
    getModuleName(module) {
      const nameMap = {
        'user': '用户管理',
        'role': '角色管理',
        'menu': '菜单管理',
        'dict': '数据字典',
        'log': '操作日志',
        'config': '系统配置'
      }
      return nameMap[module] || module
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

.module-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.module-card {
  background: #fff;
  border-radius: 8px;
  padding: 30px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.module-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-4px);
}

.module-icon {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
}

.module-card h3 {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin: 0 0 8px 0;
}

.module-card p {
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
  margin-bottom: 20px;
}

.card-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin: 0;
}

.system-info {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: #f5f7fa;
  border-radius: 4px;
}

.info-label {
  color: #5E6C84;
  font-size: 14px;
}

.info-value {
  color: #172B4D;
  font-size: 14px;
  font-weight: 600;
}
</style>

