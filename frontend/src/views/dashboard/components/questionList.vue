<template>
  <div class="question-list">
    <div v-loading="loading" class="list-content">
      <div v-if="articleList.length === 0 && !loading" class="empty-data">
        暂无常见问题数据
      </div>
      <div v-else class="article-list">
        <div v-for="(item, index) in articleList" :key="index" class="article-item" @click="handleViewDetail(item)">
          <div class="article-title">{{ item.title || '无标题' }}</div>
          <div class="article-meta">
            <span class="article-date">{{ formatDate(item.createTime) }}</span>
            <span class="article-author" v-if="item.author">{{ item.author }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCarbonArticleList } from '@/api/carbonArticle'

export default {
  name: 'QuestionList',
  data() {
    return {
      loading: false,
      articleList: [],
      current: 1,
      categoryId: '4' // 常见问题分类ID，根据实际业务调整
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    async loadData() {
      this.loading = true
      try {
        const res = await getCarbonArticleList({
          categoryId: this.categoryId,
          current: this.current,
          asc: false
        })
        if (res.code === 200 && res.data) {
          this.articleList = res.data.records || res.data.list || []
        }
      } catch (error) {
        // 接口未实现时，静默处理，不显示错误
        console.warn('加载常见问题数据失败，接口可能未实现', error)
        this.articleList = []
      } finally {
        this.loading = false
      }
    },
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    },
    handleViewDetail(item) {
      console.log('查看详情', item)
    }
  }
}
</script>

<style scoped>
.question-list {
  min-height: 200px;
}

.list-content {
  padding: 10px 0;
}

.empty-data {
  text-align: center;
  color: #909399;
  padding: 40px;
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.article-item {
  padding: 12px;
  background: #f5f7fa;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.article-item:hover {
  background: #E5F9F0;
}

.article-title {
  font-size: 14px;
  color: #172B4D;
  margin-bottom: 8px;
  font-weight: 500;
}

.article-meta {
  display: flex;
  gap: 15px;
  font-size: 12px;
  color: #909399;
}

.article-date,
.article-author {
  display: inline-block;
}
</style>

