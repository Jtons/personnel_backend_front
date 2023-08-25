import request from '@/utils/request'
export function getDictListApi() {
  return request({
    url: '/base/dict/list',
    method: 'get'
  })
}