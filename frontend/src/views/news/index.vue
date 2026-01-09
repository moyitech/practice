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
          <h2>碳资讯</h2>
          <p class="page-description">了解最新的碳市场动态、政策法规和行业资讯</p>
        </div>

        <!-- 资讯分类 -->
        <div class="category-tabs">
          <el-tabs v-model="activeCategory" @tab-click="handleCategoryChange">
            <el-tab-pane label="全部" name="all"></el-tab-pane>
            <el-tab-pane label="政策法规" name="policy"></el-tab-pane>
            <el-tab-pane label="市场动态" name="market"></el-tab-pane>
            <el-tab-pane label="行业资讯" name="industry"></el-tab-pane>
            <el-tab-pane label="技术前沿" name="tech"></el-tab-pane>
          </el-tabs>
        </div>

        <!-- 资讯列表 -->
        <div class="news-list">
          <div v-for="item in filteredNews" :key="item.id" class="news-item" @click="handleNewsClick(item)">
            <div class="news-image">
              <div class="image-placeholder">
                <i class="el-icon-picture"></i>
              </div>
            </div>
            <div class="news-content">
              <div class="news-header">
                <span class="news-category">{{ item.category }}</span>
                <span class="news-time">{{ item.publishTime }}</span>
              </div>
              <h3 class="news-title">{{ item.title }}</h3>
              <p class="news-summary">{{ item.summary }}</p>
              <div class="news-footer">
                <span class="news-source">{{ item.source }}</span>
                <span class="news-views">
                  <i class="el-icon-view"></i> {{ item.views }}
                </span>
              </div>
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
  name: 'CarbonNews',
  data() {
    return {
      accountInfo: {},
      activeCategory: 'all',
      newsList: [
        {
          id: 1,
          title: '全国碳市场交易量突破100亿吨，市场活跃度持续提升',
          summary: '据最新数据显示，全国碳排放权交易市场累计交易量已突破100亿吨，市场活跃度持续提升，交易价格稳中有升...',
          category: '市场动态',
          source: '碳市场日报',
          publishTime: '2024-01-20',
          views: 1234
        },
        {
          id: 2,
          title: '《碳排放权交易管理暂行条例》正式发布，将于3月1日起施行',
          summary: '国务院发布《碳排放权交易管理暂行条例》，这是我国碳排放权交易领域的首部行政法规，标志着碳市场制度建设取得重要进展...',
          category: '政策法规',
          source: '生态环境部',
          publishTime: '2024-01-18',
          views: 2567
        },
        {
          id: 3,
          title: 'CCER项目重启在即，林业碳汇项目迎来发展机遇',
          summary: '国家发改委表示，CCER（国家核证自愿减排量）项目有望在今年重启，林业碳汇、光伏、风电等项目将迎来新的发展机遇...',
          category: '行业资讯',
          source: '碳市场观察',
          publishTime: '2024-01-15',
          views: 1890
        },
        {
          id: 4,
          title: '区块链技术在碳资产交易中的应用前景分析',
          summary: '随着区块链技术的不断发展，其在碳资产交易、溯源、认证等领域的应用前景广阔，有望提升交易效率和透明度...',
          category: '技术前沿',
          source: '科技前沿',
          publishTime: '2024-01-12',
          views: 987
        },
        {
          id: 5,
          title: '欧盟碳边境调节机制（CBAM）正式实施，对出口企业影响几何？',
          summary: '欧盟碳边境调节机制正式实施，将对高碳产品进口征收碳关税，中国出口企业需要关注相关政策变化，提前做好应对准备...',
          category: '政策法规',
          source: '国际碳市场',
          publishTime: '2024-01-10',
          views: 2156
        },
        {
          id: 6,
          title: '2024年碳市场展望：价格波动或加大，交易量有望创新高',
          summary: '分析机构预测，2024年碳市场价格波动可能加大，但整体交易量有望创新高，市场参与者需要做好风险管理和策略调整...',
          category: '市场动态',
          source: '碳市场研究',
          publishTime: '2024-01-08',
          views: 1456
        }
      ]
    }
  },
  computed: {
    filteredNews() {
      if (this.activeCategory === 'all') {
        return this.newsList
      }
      const categoryMap = {
        'policy': '政策法规',
        'market': '市场动态',
        'industry': '行业资讯',
        'tech': '技术前沿'
      }
      return this.newsList.filter(item => item.category === categoryMap[this.activeCategory])
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
    handleCategoryChange(tab) {
      this.activeCategory = tab.name
    },
    handleNewsClick(item) {
      this.$message.info(`查看资讯：${item.title}`)
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

.category-tabs {
  background: #fff;
  border-radius: 8px;
  padding: 0 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.news-item {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  display: flex;
  gap: 20px;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.news-item:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.news-image {
  width: 200px;
  height: 120px;
  flex-shrink: 0;
}

.image-placeholder {
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  font-size: 32px;
}

.news-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.news-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.news-category {
  background: #E5F9F0;
  color: #27A777;
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
}

.news-time {
  color: #909399;
  font-size: 12px;
}

.news-title {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin: 0 0 12px 0;
  line-height: 1.5;
}

.news-summary {
  color: #5E6C84;
  font-size: 14px;
  line-height: 1.6;
  margin: 0 0 12px 0;
  flex: 1;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  color: #909399;
}

.news-views {
  display: flex;
  align-items: center;
  gap: 4px;
}
</style>

