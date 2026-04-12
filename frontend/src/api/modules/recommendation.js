import service from '../index'

export const recommendationApi = {
  getRecommendList: (params) => service.get('/recommendation/list', { params }),
  getHotList: (params) => service.get('/recommendation/hot', { params }),
  getPersonalized: (params) => service.get('/recommendation/personalized', { params }),
  refreshRecommendation: () => service.post('/recommendation/refresh')
}