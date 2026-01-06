<template>
  <div class="user-info-container">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="sidebar-header">
        <h2>XCARBON MANAGEMENT PLATFORM</h2>
      </div>
      <div class="sidebar-menu">
        <div class="menu-item" :class="{ active: $route.path === '/home' }" @click="handleMenuClick('/home')">
          <span>首页</span>
        </div>
        <div class="menu-item menu-parent" :class="{ active: $route.path.startsWith('/maintain') }">
          <span @click="handleMenuClick('/maintain/user')">账户管理</span>
          <i class="el-icon-arrow-down" :class="{ 'rotate': $route.path.startsWith('/maintain') }"></i>
        </div>
        <div v-if="$route.path.startsWith('/maintain')" class="sub-menu">
          <div class="sub-menu-item active">
            <span>基本信息</span>
          </div>
          <div class="sub-menu-item" @click="handleMenuClick('/maintain/enterprise')">
            <span>企业信息</span>
          </div>
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
        <div class="header-left">
          <h1 class="platform-title">碳中和资产管理平台</h1>
          <h2 class="platform-subtitle">XCARBON MANAGEMENT PLATFORM</h2>
        </div>
        <div class="header-right">
          <i class="el-icon-question"></i>
          <el-dropdown>
            <span class="user-dropdown">
              {{ userInfo.accountName || 'admin' }}
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button type="primary" size="small" @click="showPasswordDialog = true">修改密码</el-button>
          <div class="avatar">
            <img v-if="userInfo.avatar" :src="userInfo.avatar" alt="avatar" />
            <div v-else class="avatar-placeholder">{{ (userInfo.accountName || 'A').charAt(0).toUpperCase() }}</div>
          </div>
        </div>
      </div>
      
      <!-- 面包屑导航 -->
      <div class="breadcrumb-wrapper">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item @click.native="handleMenuClick('/home')" style="cursor: pointer;">首页</el-breadcrumb-item>
          <el-breadcrumb-item>基本信息</el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <!-- 内容区域 -->
      <div class="content-wrapper" v-loading="loading">
      <!-- 基本信息区域 -->
      <div class="basic-info-section">
        <div class="info-header">
          <div class="avatar-section">
            <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :before-upload="beforeAvatarUpload"
              :http-request="handleAvatarUpload"
            >
              <img v-if="userInfo.avatar" :src="userInfo.avatar" class="avatar" />
              <div v-else class="avatar-placeholder">
                {{ (userInfo.accountName || 'U').charAt(0).toUpperCase() }}
              </div>
              <div class="avatar-mask">
                <i class="el-icon-camera"></i>
              </div>
            </el-upload>
          </div>
          <h3 class="section-title">基本信息</h3>
        </div>
        <div class="info-form">
          <div class="form-left">
            <div class="form-item">
              <label>用户名</label>
              <el-input 
                v-model="userInfo.accountName" 
                :disabled="true"
                placeholder="admin"
              ></el-input>
            </div>
            <div class="form-item">
              <label>系统角色</label>
              <el-tag type="info" size="small">超级管理员</el-tag>
            </div>
          </div>
          <div class="form-right">
            <div class="form-item">
              <label>姓名</label>
              <el-input 
                v-model="userInfo.realName" 
                placeholder="请输入姓名"
              ></el-input>
            </div>
            <div class="form-item">
              <label>电子邮箱</label>
              <el-input 
                v-model="userInfo.email" 
                placeholder="请输入电子邮箱"
              ></el-input>
            </div>
            <div class="form-item">
              <label>联系电话</label>
              <el-input 
                v-model="userInfo.phone" 
                placeholder="请输入联系电话"
              ></el-input>
            </div>
            <div class="form-item">
              <label>注册时间</label>
              <el-input 
                :value="formatDateTime(userInfo.createdTime || userInfo.registerTime)" 
                :disabled="true"
                placeholder="2021-06-22 23:46:14"
              ></el-input>
            </div>
          </div>
        </div>
      </div>

      <!-- Tab栏切换 -->
      <div class="tab-section">
        <el-tabs v-model="activeTab" @tab-click="handleTabClick">
          <el-tab-pane label="登录相关账户" name="login">
            <div class="account-section">
              <div class="account-item">
                <label>账号</label>
                <div class="account-input-wrapper">
                  <el-input 
                    v-model="userInfo.accountName" 
                    :disabled="true"
                    placeholder="admin"
                  ></el-input>
                  <span class="account-tip">系统自动生成,可用于登录</span>
                </div>
              </div>
              <div class="account-item">
                <label>手机号</label>
                <div class="account-input-wrapper">
                  <el-input 
                    v-model="userInfo.phone" 
                    placeholder="请输入手机号"
                  ></el-input>
                  <span class="account-tip">绑定手机号,可用于登录、重置密码或其他安全</span>
                  <el-button 
                    type="primary" 
                    size="small"
                    @click="showPhoneDialog = true"
                  >
                    {{ userInfo.phone ? '修改' : '绑定' }}
                  </el-button>
                </div>
              </div>
              <div class="account-item">
                <label>邮箱</label>
                <div class="account-input-wrapper">
                  <el-input 
                    v-model="userInfo.email" 
                    placeholder="请输入邮箱"
                  ></el-input>
                  <span class="account-tip">绑定邮箱,可用于登录、重置密码或其他安全</span>
                  <el-button 
                    type="primary" 
                    size="small"
                    @click="showEmailDialog = true"
                  >
                    {{ userInfo.email ? '修改' : '绑定' }}
                  </el-button>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="交易相关账户" name="exchange">
            <div class="exchange-section">
              <div class="exchange-header">
                <span class="section-title">交易平台</span>
                <el-button type="primary" size="small" @click="showAddAccountDialog = true">添加账号</el-button>
              </div>
              <div class="exchange-list">
                <el-table :data="exchangeAccountList" style="width: 100%">
                  <el-table-column prop="accountName" label="账号名" width="200"></el-table-column>
                  <el-table-column prop="carbonExchangeName" label="交易所" width="200"></el-table-column>
                  <el-table-column prop="remarks" label="备注"></el-table-column>
                  <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                      <el-button 
                        type="text" 
                        size="small" 
                        style="color: #F56C6C"
                        @click="handleUnbind(scope.row)"
                      >
                        解绑
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <div v-if="exchangeAccountList.length === 0" class="empty-data">
                  暂无交易账号，请添加
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      </div>
    </div>

    <!-- 修改密码弹窗 -->
    <el-dialog
      title="修改密码"
      :visible.sync="showPasswordDialog"
      width="500px"
      @close="resetPasswordForm"
    >
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordForm" label-width="100px">
        <el-form-item label="当前密码" prop="oldPassword">
          <el-input
            type="password"
            v-model="passwordForm.oldPassword"
            placeholder="请输入当前密码"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            type="password"
            v-model="passwordForm.newPassword"
            placeholder="请输入新密码"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            type="password"
            v-model="passwordForm.confirmPassword"
            placeholder="请再次输入新密码"
            show-password
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showPasswordDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdatePassword" :loading="passwordLoading">保存</el-button>
      </div>
    </el-dialog>

    <!-- 修改手机号弹窗 -->
    <el-dialog
      :title="userInfo.phone ? '修改手机号' : '绑定手机号'"
      :visible.sync="showPhoneDialog"
      width="500px"
      @close="resetPhoneForm"
    >
      <el-form :model="phoneForm" :rules="phoneRules" ref="phoneForm" label-width="100px">
        <el-form-item label="手机号" prop="phone">
          <el-input
            v-model="phoneForm.phone"
            placeholder="请输入手机号"
            maxlength="11"
          ></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <div class="code-input-group">
            <el-input
              v-model="phoneForm.code"
              placeholder="请输入验证码"
              maxlength="6"
            ></el-input>
            <el-button
              type="primary"
              :disabled="codeCountdown > 0"
              @click="handleGetPhoneCode"
              :loading="codeLoading"
            >
              {{ codeCountdown > 0 ? `${codeCountdown}秒后重新获取` : '获取验证码' }}
            </el-button>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showPhoneDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdatePhone" :loading="phoneLoading">保存</el-button>
      </div>
    </el-dialog>

    <!-- 绑定邮箱弹窗 -->
    <el-dialog
      :title="userInfo.email ? '修改邮箱' : '绑定邮箱'"
      :visible.sync="showEmailDialog"
      width="500px"
      @close="resetEmailForm"
    >
      <el-form :model="emailForm" :rules="emailRules" ref="emailForm" label-width="100px">
        <el-form-item label="账号密码" prop="password">
          <el-input
            type="password"
            v-model="emailForm.password"
            placeholder="请输入账号密码"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="emailForm.email"
            placeholder="请输入邮箱地址"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showEmailDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdateEmail" :loading="emailLoading">保存</el-button>
      </div>
    </el-dialog>

    <!-- 添加交易账号弹窗 -->
    <el-dialog
      title="添加交易账号"
      :visible.sync="showAddAccountDialog"
      width="600px"
      @close="resetAddAccountForm"
    >
      <el-form :model="addAccountForm" :rules="addAccountRules" ref="addAccountForm" label-width="120px">
        <el-form-item label="交易所" prop="carbonExchangeId">
          <el-select
            v-model="addAccountForm.carbonExchangeId"
            placeholder="请选择交易所"
            style="width: 100%"
          >
            <el-option
              v-for="item in exchangeList"
              :key="item.id"
              :label="item.exchangeName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账号名" prop="accountName">
          <el-input
            v-model="addAccountForm.accountName"
            placeholder="请输入账号名"
          ></el-input>
        </el-form-item>
        <el-form-item label="账户凭证" prop="accountCredentials">
          <el-upload
            class="upload-demo"
            action="#"
            :limit="1"
            :on-exceed="handleExceed"
            :before-upload="beforeUpload"
            :http-request="handleFileUpload"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            type="textarea"
            v-model="addAccountForm.remarks"
            placeholder="请输入备注"
            :rows="3"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAddAccountDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAddAccount" :loading="addAccountLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getUserInfo,
  updatePassword,
  getPhoneCode,
  updatePhone,
  updateEmail,
  getExchangeAccountList,
  addExchangeAccount,
  unbindExchangeAccount,
  getCarbonExchangeList
} from '@/api/user'
import { logout } from '@/api/user'

export default {
  name: 'UserInfo',
  data() {
    // 验证密码确认
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    // 验证邮箱格式
    const validateEmail = (rule, value, callback) => {
      const emailReg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
      if (!value) {
        callback(new Error('请输入邮箱地址'))
      } else if (!emailReg.test(value)) {
        callback(new Error('请输入正确的邮箱格式'))
      } else {
        callback()
      }
    }
    // 验证手机号格式
    const validatePhone = (rule, value, callback) => {
      const phoneReg = /^1[3-9]\d{9}$/
      if (!value) {
        callback(new Error('请输入手机号'))
      } else if (!phoneReg.test(value)) {
        callback(new Error('请输入正确的手机号格式'))
      } else {
        callback()
      }
    }

    return {
      loading: false,
      activeTab: 'login',
      userInfo: {},
      exchangeAccountList: [],
      exchangeList: [],
      showPasswordDialog: false,
      showPhoneDialog: false,
      showEmailDialog: false,
      showAddAccountDialog: false,
      passwordLoading: false,
      phoneLoading: false,
      emailLoading: false,
      addAccountLoading: false,
      codeLoading: false,
      codeCountdown: 0,
      fileList: [],
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入当前密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      },
      phoneForm: {
        phone: '',
        code: ''
      },
      phoneRules: {
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { len: 6, message: '验证码长度为6位', trigger: 'blur' }
        ]
      },
      emailForm: {
        password: '',
        email: ''
      },
      emailRules: {
        password: [
          { required: true, message: '请输入账号密码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ]
      },
      addAccountForm: {
        carbonExchangeId: '',
        accountName: '',
        accountCredentials: '',
        remarks: ''
      },
      addAccountRules: {
        carbonExchangeId: [
          { required: true, message: '请选择交易所', trigger: 'change' }
        ],
        accountName: [
          { required: true, message: '请输入账号名', trigger: 'blur' }
        ],
        accountCredentials: [
          { required: true, message: '请上传账户凭证', trigger: 'change' }
        ]
      }
    }
  },
  mounted() {
    this.loadUserInfo()
    this.loadExchangeAccountList()
    this.loadExchangeList()
  },
  methods: {
    // 加载用户基本信息
    async loadUserInfo() {
      this.loading = true
      try {
        const userInfoStr = localStorage.getItem('userInfo')
        if (!userInfoStr) {
          this.$message.error('请先登录')
          this.$router.push('/login')
          return
        }
        const userInfo = JSON.parse(userInfoStr)
        const accountId = userInfo.accountId
        if (!accountId) {
          this.$message.error('账户ID不存在')
          return
        }
        // 先使用localStorage中的信息作为默认值
        this.userInfo = { ...userInfo }
        // 尝试从接口获取最新信息
        try {
          const res = await getUserInfo(accountId)
          if (res.code === 200 && res.data) {
            this.userInfo = { ...this.userInfo, ...res.data }
          }
        } catch (apiError) {
          // 接口未实现或失败时，使用localStorage中的信息
          console.warn('获取用户详细信息失败，使用本地缓存信息', apiError)
          // 不显示错误提示，因为已经有默认数据了
        }
      } catch (error) {
        console.error('加载用户信息失败', error)
        // 如果连localStorage都没有，才显示错误
        if (!localStorage.getItem('userInfo')) {
          this.$message.error('请先登录')
          this.$router.push('/login')
        }
      } finally {
        this.loading = false
      }
    },
    // 加载交易账号列表
    async loadExchangeAccountList() {
      try {
        const userInfoStr = localStorage.getItem('userInfo')
        if (!userInfoStr) return
        const userInfo = JSON.parse(userInfoStr)
        const accountId = userInfo.accountId
        if (!accountId) return
        const res = await getExchangeAccountList({ accountId })
        if (res.code === 200 && res.data) {
          this.exchangeAccountList = Array.isArray(res.data) ? res.data : []
        }
      } catch (error) {
        // 接口未实现时，静默处理，不显示错误
        console.warn('加载交易账号列表失败，接口可能未实现', error)
        this.exchangeAccountList = []
      }
    },
    // 加载交易所列表
    async loadExchangeList() {
      try {
        const res = await getCarbonExchangeList()
        if (res.code === 200 && res.data) {
          this.exchangeList = Array.isArray(res.data) ? res.data : []
        }
      } catch (error) {
        // 接口未实现时，静默处理，不显示错误
        console.warn('加载交易所列表失败，接口可能未实现', error)
        this.exchangeList = []
      }
    },
    // Tab切换
    handleTabClick(tab) {
      this.activeTab = tab.name
    },
    // 修改密码
    handleUpdatePassword() {
      this.$refs.passwordForm.validate(async (valid) => {
        if (!valid) return
        this.passwordLoading = true
        try {
          const userInfoStr = localStorage.getItem('userInfo')
          const userInfo = JSON.parse(userInfoStr)
          const res = await updatePassword({
            id: userInfo.accountId,
            oldPassword: this.passwordForm.oldPassword,
            newPassword: this.passwordForm.newPassword
          })
          if (res.code === 200) {
            this.$message.success('密码修改成功，请重新登录')
            this.showPasswordDialog = false
            // 提示重新登录
            setTimeout(() => {
              localStorage.removeItem('token')
              localStorage.removeItem('userInfo')
              this.$router.push('/login')
            }, 1500)
          }
        } catch (error) {
          this.$message.error('修改密码失败')
          console.error(error)
        } finally {
          this.passwordLoading = false
        }
      })
    },
    // 重置密码表单
    resetPasswordForm() {
      this.$refs.passwordForm && this.$refs.passwordForm.resetFields()
      this.passwordForm = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    },
    // 获取手机验证码
    async handleGetPhoneCode() {
      // 先验证手机号
      this.$refs.phoneForm.validateField('phone', async (valid) => {
        if (valid) return
        this.codeLoading = true
        try {
          const res = await getPhoneCode(this.phoneForm.phone)
          if (res.code === 200) {
            this.$message.success('验证码已发送')
            // 开始倒计时
            this.codeCountdown = 60
            const timer = setInterval(() => {
              this.codeCountdown--
              if (this.codeCountdown <= 0) {
                clearInterval(timer)
              }
            }, 1000)
          }
        } catch (error) {
          this.$message.error('获取验证码失败')
          console.error(error)
        } finally {
          this.codeLoading = false
        }
      })
    },
    // 修改手机号
    handleUpdatePhone() {
      this.$refs.phoneForm.validate(async (valid) => {
        if (!valid) return
        this.phoneLoading = true
        try {
          const userInfoStr = localStorage.getItem('userInfo')
          const userInfo = JSON.parse(userInfoStr)
          const res = await updatePhone({
            id: userInfo.accountId,
            phone: this.phoneForm.phone,
            code: this.phoneForm.code
          })
          if (res.code === 200) {
            this.$message.success('手机号修改成功')
            this.showPhoneDialog = false
            // 刷新用户信息
            await this.loadUserInfo()
          }
        } catch (error) {
          this.$message.error('修改手机号失败')
          console.error(error)
        } finally {
          this.phoneLoading = false
        }
      })
    },
    // 重置手机号表单
    resetPhoneForm() {
      this.$refs.phoneForm && this.$refs.phoneForm.resetFields()
      this.phoneForm = {
        phone: '',
        code: ''
      }
      this.codeCountdown = 0
    },
    // 修改邮箱
    handleUpdateEmail() {
      this.$refs.emailForm.validate(async (valid) => {
        if (!valid) return
        this.emailLoading = true
        try {
          const userInfoStr = localStorage.getItem('userInfo')
          const userInfo = JSON.parse(userInfoStr)
          const res = await updateEmail({
            id: userInfo.accountId,
            email: this.emailForm.email,
            password: this.emailForm.password
          })
          if (res.code === 200) {
            this.$message.success('邮箱绑定成功')
            this.showEmailDialog = false
            // 刷新用户信息
            await this.loadUserInfo()
          }
        } catch (error) {
          this.$message.error('绑定邮箱失败')
          console.error(error)
        } finally {
          this.emailLoading = false
        }
      })
    },
    // 重置邮箱表单
    resetEmailForm() {
      this.$refs.emailForm && this.$refs.emailForm.resetFields()
      this.emailForm = {
        password: '',
        email: ''
      }
    },
    // 添加交易账号
    handleAddAccount() {
      this.$refs.addAccountForm.validate(async (valid) => {
        if (!valid) return
        this.addAccountLoading = true
        try {
          const userInfoStr = localStorage.getItem('userInfo')
          const userInfo = JSON.parse(userInfoStr)
          const res = await addExchangeAccount({
            carbonExchangeId: this.addAccountForm.carbonExchangeId,
            accountName: this.addAccountForm.accountName,
            accountCredentials: this.addAccountForm.accountCredentials,
            tenantId: userInfo.tenantId,
            remarks: this.addAccountForm.remarks || ''
          })
          if (res.code === 200) {
            this.$message.success('账号添加成功')
            this.showAddAccountDialog = false
            // 刷新交易账号列表
            await this.loadExchangeAccountList()
          }
        } catch (error) {
          this.$message.error('添加账号失败')
          console.error(error)
        } finally {
          this.addAccountLoading = false
        }
      })
    },
    // 重置添加账号表单
    resetAddAccountForm() {
      this.$refs.addAccountForm && this.$refs.addAccountForm.resetFields()
      this.addAccountForm = {
        carbonExchangeId: '',
        accountName: '',
        accountCredentials: '',
        remarks: ''
      }
      this.fileList = []
    },
    // 解绑账号
    handleUnbind(row) {
      this.$confirm('确认解绑该账号吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await unbindExchangeAccount({ id: row.id })
          if (res.code === 200) {
            this.$message.success('解绑成功')
            // 刷新交易账号列表
            await this.loadExchangeAccountList()
          }
        } catch (error) {
          this.$message.error('解绑失败')
          console.error(error)
        }
      }).catch(() => {})
    },
    // 头像上传前验证
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    // 头像上传
    handleAvatarUpload(options) {
      // 这里应该调用上传接口，暂时先提示
      this.$message.info('头像上传功能需要后端接口支持')
      // TODO: 实现头像上传接口调用
      // const formData = new FormData()
      // formData.append('file', options.file)
      // formData.append('accountId', this.userInfo.accountId)
      // uploadAvatar(formData).then(res => {
      //   if (res.code === 200) {
      //     this.userInfo.avatar = res.data.url
      //     this.$message.success('头像上传成功')
      //   }
      // })
    },
    // 文件上传前验证
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt5M = file.size / 1024 / 1024 < 5
      if (!isJPG) {
        this.$message.error('只能上传 JPG/PNG 格式的图片!')
      }
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过 5MB!')
      }
      return isJPG && isLt5M
    },
    // 文件上传
    handleFileUpload(options) {
      // 这里应该调用文件上传接口
      // 暂时先读取文件为base64
      const reader = new FileReader()
      reader.onload = (e) => {
        this.addAccountForm.accountCredentials = e.target.result
        this.$refs.addAccountForm && this.$refs.addAccountForm.validateField('accountCredentials')
      }
      reader.readAsDataURL(options.file)
      this.fileList = [options.file]
    },
    // 文件超出限制
    handleExceed() {
      this.$message.warning('只能上传一个文件')
    },
    // 格式化日期
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    },
    // 格式化日期时间
    formatDateTime(date) {
      if (!date) return ''
      const d = new Date(date)
      const year = d.getFullYear()
      const month = String(d.getMonth() + 1).padStart(2, '0')
      const day = String(d.getDate()).padStart(2, '0')
      const hours = String(d.getHours()).padStart(2, '0')
      const minutes = String(d.getMinutes()).padStart(2, '0')
      const seconds = String(d.getSeconds()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    },
    // 获取产品版本名称
    getProductVersionName(version) {
      const versionMap = {
        '0400000001': '试用版',
        '0400000002': '标准版',
        '0400000003': '专业版',
        '0400000004': '企业版'
      }
      return versionMap[version] || '专业版'
    },
    // 菜单点击
    handleMenuClick(path) {
      if (this.$route.path !== path) {
        this.$router.push(path)
      }
    },
    // 退出登录
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
.user-info-container {
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
  transition: transform 0.3s;
}

.menu-item i.rotate {
  transform: rotate(180deg);
}

.menu-parent {
  position: relative;
}

.sub-menu {
  background: #f5f7fa;
  padding: 5px 0;
}

.sub-menu-item {
  padding: 8px 20px 8px 50px;
  font-size: 13px;
  color: #5E6C84;
  cursor: pointer;
  transition: background-color 0.3s;
}

.sub-menu-item:hover {
  background: #e8f5e9;
}

.sub-menu-item.active {
  background: #E5F9F0;
  color: #27A777;
  font-weight: 500;
}

/* 主内容区域 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  height: 80px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
}

.header-left {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.platform-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
}

.platform-subtitle {
  margin: 0;
  font-size: 12px;
  font-weight: 400;
  color: #909399;
  letter-spacing: 1px;
}

.breadcrumb-wrapper {
  padding: 15px 30px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
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

.content-wrapper {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

/* 基本信息区域 */
.basic-info-section {
  background: #fff;
  border-radius: 8px;
  padding: 30px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.info-header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.section-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
}

.info-form {
  display: flex;
  gap: 40px;
}

.form-left,
.form-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-item label {
  font-size: 14px;
  color: #5E6C84;
  font-weight: 500;
}

.avatar-section {
  flex-shrink: 0;
}

.avatar-uploader {
  position: relative;
  width: 100px;
  height: 100px;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #e4e7ed;
}

.avatar-placeholder {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: linear-gradient(135deg, #27A777 0%, #76DFB7 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 36px;
  font-weight: 500;
  border: 2px solid #e4e7ed;
}

.avatar-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s;
  cursor: pointer;
}

.avatar-uploader:hover .avatar-mask {
  opacity: 1;
}

.avatar-mask i {
  color: #fff;
  font-size: 24px;
}


/* Tab区域 */
.tab-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* 登录相关账户 */
.account-section {
  padding: 20px 0;
}

.account-item {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px 0;
  border-bottom: 1px solid #f0f0f0;
}

.account-item:last-child {
  border-bottom: none;
}

.account-item label {
  font-size: 14px;
  color: #5E6C84;
  font-weight: 500;
}

.account-input-wrapper {
  display: flex;
  align-items: center;
  gap: 15px;
  flex-wrap: wrap;
}

.account-input-wrapper .el-input {
  flex: 1;
  min-width: 300px;
}

.account-tip {
  font-size: 12px;
  color: #909399;
  white-space: nowrap;
}

/* 交易相关账户 */
.exchange-section {
  padding: 20px 0;
}

.exchange-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 16px;
  font-weight: 500;
  color: #172B4D;
}

.exchange-list {
  margin-top: 20px;
}

.empty-data {
  text-align: center;
  padding: 40px;
  color: #909399;
  font-size: 14px;
}

/* 验证码输入组 */
.code-input-group {
  display: flex;
  gap: 10px;
}

.code-input-group .el-input {
  flex: 1;
}

/* 对话框样式 */
.dialog-footer {
  text-align: right;
}

/* 上传组件样式 */
.upload-demo {
  width: 100%;
}
</style>

