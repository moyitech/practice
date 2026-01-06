import request from '@/utils/request'

/**
 * 获取碳文章列表
 * @param {object} params 查询参数
 * @param {string} params.categoryId 分类ID
 * @param {number} params.current 当前页
 * @param {boolean} params.asc 是否升序
 */
export function getCarbonArticleList(params) {
  return request({
    url: '/systemCenter/system/carbonArticle/getPageList',
    method: 'get',
    params
  })
}

