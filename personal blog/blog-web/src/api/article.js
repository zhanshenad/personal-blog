import request from '../utils/request'

export function getArticles(params) {
  return request.get('/articles', { params })
}

export function getArticleDetail(id) {
  return request.get(`/articles/${id}`)
}

export function searchArticles(params) {
  return request.get('/articles/search', { params })
}

// Admin
export function getAdminArticles(params) {
  return request.get('/admin/articles', { params })
}

export function createArticle(data) {
  return request.post('/admin/articles', data)
}

export function updateArticle(id, data) {
  return request.put(`/admin/articles/${id}`, data)
}

export function deleteArticle(id) {
  return request.delete(`/admin/articles/${id}`)
}
