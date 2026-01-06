import request from '@/utils/request'

/**
 * 获取首页数据
 */
export function getHomeData() {
  return request({
    url: '/systemCenter/dataPanel/home',
    method: 'get'
  })
}

/**
 * 获取企业绿度
 */
export function getGreenness() {
  return request({
    url: '/systemCenter/dataPanel/getGreenness',
    method: 'get'
  })
}

