import service from '../index'

export const interactionApi = {
  getList: (params) => service.get('/interaction/list', { params }),
  getNotifications: (params) => service.get('/interaction/notifications', { params }),
  like: (data) => service.post('/interaction/like', data),
  unlike: (data) => service.post('/interaction/unlike', data),
  comment: (data) => service.post('/interaction/comment', data),
  getComments: (contentId, params) => service.get(`/interaction/comments/${contentId}`, { params }),
  deleteComment: (commentId) => service.delete(`/interaction/comment/${commentId}`),
  share: (data) => service.post('/interaction/share', data),
  getShareList: (contentId, params) => service.get(`/interaction/shares/${contentId}`, { params }),
  getUnreadCount: () => service.get('/interaction/unread-count')
}