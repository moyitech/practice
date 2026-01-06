import request from '@/utils/request'

/**
 * 获取注册验证码
 * @param {string} phone 手机号
 */
export function regCode(phone) {
  return request({
    url: `/authCenter/auth/register/code/${phone}`,
    method: 'get'
  })
}

/**
 * 验证账户名是否存在
 * @param {string} accountName 账户名
 */
export function getAuthCenterAuthVerify(accountName) {
  return request({
    url: `/authCenter/auth/verify/${accountName}`,
    method: 'get'
  })
}

/**
 * 注册
 * @param {object} data 注册数据
 */
export function register(data) {
  return request({
    url: '/authCenter/auth/register',
    method: 'post',
    data
  })
}

/**
 * 登录
 * @param {object} data 登录数据
 */
export function login(data) {
  return request({
    url: '/authCenter/auth/login',
    method: 'post',
    data
  })
}

/**
 * 退出登录
 */
export function logout() {
  return request({
    url: '/authCenter/auth/logout',
    method: 'get'
  })
}

/**
 * 获取忘记密码验证码
 * @param {string} phone 手机号
 */
export function forgotPasswordCode(phone) {
  return request({
    url: `/authCenter/auth/forgotPassword/code/${phone}`,
    method: 'get'
  })
}

/**
 * 忘记密码
 * @param {object} data 忘记密码数据
 */
export function forgotPassword(data) {
  return request({
    url: '/authCenter/auth/forgotPassword',
    method: 'post',
    data
  })
}

/**
 * 获取用户基本信息
 * @param {string} accountId 账户ID
 */
export function getUserInfo(accountId) {
  return request({
    url: `/systemCenter/system/sysAccount/info/${accountId}`,
    method: 'get'
  })
}

/**
 * 修改密码
 * @param {object} data 修改密码数据 { id, newPassword, oldPassword }
 */
export function updatePassword(data) {
  return request({
    url: '/systemCenter/system/sysAccount/update/password',
    method: 'post',
    data
  })
}

/**
 * 获取手机验证码
 * @param {string} newPhone 新手机号
 */
export function getPhoneCode(newPhone) {
  return request({
    url: `/systemCenter/system/sysAccount/update/code/${newPhone}`,
    method: 'get'
  })
}

/**
 * 修改手机号
 * @param {object} params 修改手机号参数 { code, id, phone }
 */
export function updatePhone(params) {
  return request({
    url: '/systemCenter/system/sysAccount/update/phone',
    method: 'get',
    params
  })
}

/**
 * 修改邮箱
 * @param {object} params 修改邮箱参数 { email, id, password }
 */
export function updateEmail(params) {
  return request({
    url: '/systemCenter/system/sysAccount/send/email',
    method: 'get',
    params
  })
}

/**
 * 获取交易相关账号列表
 * @param {object} data 请求数据 { accountId }
 */
export function getExchangeAccountList(data) {
  return request({
    url: '/assetsCenter/assets/carbonExchange/getListByTenant',
    method: 'post',
    data
  })
}

/**
 * 添加交易账号
 * @param {object} data 添加账号数据 { accountCredentials, accountName, carbonExchangeId, tenantId, remarks }
 */
export function addExchangeAccount(data) {
  return request({
    url: '/assetsCenter/assets/exchangeAccount/add',
    method: 'post',
    data
  })
}

/**
 * 解绑交易账号
 * @param {object} data 解绑数据 { id }
 */
export function unbindExchangeAccount(data) {
  return request({
    url: '/assetsCenter/assets/exchangeAccount/unbind',
    method: 'put',
    data
  })
}

/**
 * 获取交易所列表
 */
export function getCarbonExchangeList() {
  return request({
    url: '/assetsCenter/assets/carbonExchange/list',
    method: 'get'
  })
}

