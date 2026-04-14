import request from '../utils/request'

export function getComments(articleId) {
  return request.get(`/comments/article/${articleId}`)
}

export function addComment(articleId, data) {
  return request.post(`/admin/comments/article/${articleId}`, data)
}

export function getAdminComments() {
  return request.get('/admin/comments')
}

export function deleteComment(id) {
  return request.delete(`/admin/comments/${id}`)
}
