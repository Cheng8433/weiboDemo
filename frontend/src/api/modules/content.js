import service from '../index'

export const contentApi = {
  getContentList: (params) => service.get('/content/list', { params }),
  getContentById: (id) => service.get(`/content/${id}`),
  createContent: (data) => service.post('/content', data),
  updateContent: (id, data) => service.put(`/content/${id}`, data),
  deleteContent: (id) => service.delete(`/content/${id}`),
  like: (data) => service.post('/content/like', data),
  unlike: (data) => service.post('/content/unlike', data),
  share: (data) => service.post('/content/share', data),
  getUserContentList: (userId, params) => service.get(`/content/user/${userId}`, { params }),
  getFollowingContentList: (params) => service.get('/content/following', { params })
}