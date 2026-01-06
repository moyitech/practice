<template>
  <div class="news-container">
    <div class="news-section">
      <h3 class="section-title">碳行业资讯</h3>
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="动态" name="news">
          <news-list />
        </el-tab-pane>
        <el-tab-pane label="知识库" name="knowledge">
          <knowledge-list />
        </el-tab-pane>
        <el-tab-pane label="公告" name="notice">
          <notice-list />
        </el-tab-pane>
        <el-tab-pane label="常见问题" name="question">
          <question-list />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import NewsList from './newsList.vue'
import KnowledgeList from './knowleageList.vue'
import NoticeList from './noticeList.vue'
import QuestionList from './questionList.vue'

export default {
  name: 'NewsContainer',
  components: {
    NewsList,
    KnowledgeList,
    NoticeList,
    QuestionList
  },
  data() {
    return {
      activeTab: 'news'
    }
  }
}
</script>

<style scoped>
.news-container {
  margin-bottom: 20px;
}

.news-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #172B4D;
  margin-bottom: 20px;
}
</style>

