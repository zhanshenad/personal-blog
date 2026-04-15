import request from '../utils/request'

export function getTags() {
  return request.get('/tags')
}

export function createTag(data) {
  return request.post('/admin/tags', data)
}

export function deleteTag(id) {
  return request.delete(`/admin/tags/${id}`)
}
