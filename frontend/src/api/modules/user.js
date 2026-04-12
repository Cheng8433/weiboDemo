import service from '../index'

export const userApi = {
  login: (data) => service.post('/user/login', data),
  register: (data) => service.post('/user/register', data),
  getUserInfo: (id) => service.get(`/user/${id}`),
  updateUserInfo: (id, data) => service.put(`/user/${id}`, data),
  follow: (userId) => service.post(`/user/follow/${userId}`),
  unfollow: (userId) => service.post(`/user/unfollow/${userId}`),
  getUserList: (params) => service.get('/user/list', { params }),
  getFollowingList: (userId, params) => service.get(`/user/following/${userId}`, { params }),
  getFollowerList: (userId, params) => service.get(`/user/follower/${userId}`, { params })
}