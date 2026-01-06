<template>
  <div class="login-container">
    <div>
      <img class="logo" :src="loginLogo" />
    </div>
    <div class="loginContiner">
      <div class="loginInputContainer">
        <div class="login-form">
          <el-form :model="loginForm" ref="loginForm" label-position="left">
            <div class="login-input" style="margin-top: 0px;">
              <span class="txt1">用&nbsp;户&nbsp;名</span>
              <img class="label1" referrerpolicy="no-referrer" src="@/assets/imgs/label1.jpg" />
              <input class="user-input" ref="accountName" v-model="loginForm.accountName" name="accountName" type="text"
                placeholder="请输入用户名" @keyup.enter="handleLogin" />
            </div>

            <div class="login-input">
              <span class="txt1">密&nbsp;&nbsp;&nbsp;&nbsp;码</span>
              <img class="label1" referrerpolicy="no-referrer" src="@/assets/imgs/label1.jpg" />
              <input class="pwd-input" :type="passwordType" ref="password" v-model="loginForm.password"
                placeholder="请输入密码" name="password" @keyup.enter="handleLogin" />
            </div>

            <div class="btn_login">
              <el-button :class="setButtonClass()" type="success"
                style="width:100%;margin-top:30px;height:50px;font-size: 15px;" :loading="loading"
                @click.prevent="handleLogin">登录</el-button>
            </div>
          </el-form>
          <div class="forgot-password-link">
            <el-link type="success" @click="handleForgotPassword">忘记密码？</el-link>
          </div>
          <div class="hasaccount">
            <a class="acss">还没有账户，</a>
            <el-link type="success" @click="handleRegister">去注册 ></el-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from '@/api/user'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        accountName: '',
        password: ''
      },
      passwordType: 'password',
      loading: false,
      loginLogo: require('@/assets/imgs/login_logo.png')
    }
  },
  methods: {
    handleLogin() {
      if (!this.loginForm.accountName) {
        this.$message.error('请输入用户名')
        return
      }
      if (!this.loginForm.password) {
        this.$message.error('请输入密码')
        return
      }

      this.loading = true
      login(this.loginForm)
        .then((res) => {
          this.loading = false
          if (res.code === 200) {
            // 保存token
            localStorage.setItem('token', res.data.token)
            localStorage.setItem('userInfo', JSON.stringify(res.data.securityData))
            this.$message.success('登录成功')
            // 跳转到首页
            this.$router.push({ path: '/home' })
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    setButtonClass() {
      if (this.loginForm.accountName && this.loginForm.password) {
        return 'login-text'
      } else {
        return 'no-login-text'
      }
    },
    handleRegister() {
      this.$router.push({ path: '/register' })
    },
    handleForgotPassword() {
      this.$router.push({ path: '/forgotPassword' })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: rgba(237, 249, 252, 1);

  .login-form {
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    width: 520px;
    min-height: 400px;
    padding: 35px 35px 15px 35px;
    margin: -200px auto 0;
    background: #fff;
    border-radius: 6px;
  }
}

.logo {
  background: transparent;
  width: 134px;
  height: 36px;
  float: left;
  margin-left: 40px;
  margin-top: 30px;
}

.login-input {
  margin-top: 32px;
  height: 50px;
  background: #E5F9F0;
  display: flex;
  flex-direction: row;
  font-family: PingFangSC-Medium, PingFang SC;
}

.txt1 {
  width: 55px;
  height: 15px;
  overflow-wrap: break-word;
  text-align: left;
  white-space: nowrap;
  line-height: 15px;
  display: flex;
  align-self: center;
  margin-left: 16px;
  width: 64px;
  font-size: 16px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #172B4D;
  letter-spacing: 1px;
}

.label1 {
  width: 1px;
  height: 16px;
  display: flex;
  align-self: center;
  margin-left: 9px;
  margin-right: 9px;
}

.user-input {
  padding: 5px;
  display: flex;
  align-self: center;
  margin-right: 25px;
  font-size: 15px;
  line-height: 15px;
  flex-grow: 1;
  background-color: transparent;
  border: 0;
  outline: none;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #172B4D;
}

.pwd-input {
  padding: 5px;
  display: flex;
  align-self: center;
  margin-right: 5px;
  font-size: 15px;
  line-height: 15px;
  flex-grow: 1;
  background-color: transparent;
  border: 0;
  outline: none;
  font-family: PingFangSC-Regular, PingFang SC;
}

.loginContiner {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 100%;
  display: flex;
  flex-flow: row;
}

.loginInputContainer {
  margin: auto;
  padding-top: 32px;
  height: 400px !important;
  padding: 0 !important;
  border-radius: 6px;
}

.forgot-password-link {
  width: 100px;
  margin-top: 10px;
  display: flex;
  flex-direction: row;
  margin: auto;
  padding-top: 10px;
}

.hasaccount {
  width: 150px;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  margin: auto;
  padding-top: 10px;
}

.acss {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #5E6C84;
  line-height: 14px;
}

.login-text {
  font-size: 15px;
  background: #27A777;
  color: #fff;
  font-size: 18px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #FFFFFF;
  line-height: 18px;
  transition: background-color .3s ease-in;
}

.no-login-text {
  background: #76DFB7;
  color: #fff;
  font-size: 18px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #FFFFFF;
  line-height: 18px;
  width: 100%;
  margin-top: 30px;
  height: 50px;
  border: none;
  pointer-events: none;
  transition: background-color .3s ease-in;
}
</style>


