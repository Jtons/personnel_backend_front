import request from '@/utils/request'

export function reqUserInfo() {
  return request({
    url: '/base/auth/info',
    method: 'get',
  })
}

export function getUsers() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}

export function deleteUser(data) {
  return request({
    url: '/user/delete',
    method: 'post',
    data
  })
}


export function reqValidatUserID(data) {
  return request({
    url: '/user/validatUserID',
    method: 'post',
    data
  })
}

export function getUserListApi(params) {
  return request({
    url: '/base/userInfo/pageList',
    method: 'get',
    params
  })
}
export function addUser(data) {
  return request({
    url: '/base/userInfo/add',
    method: 'post',
    data
  })
}
export function editUser(data) {
  return request({
    url: '/base/userInfo/edit',
    method: 'put',
    data
  })
}
export function delUser(data) {
  return request({
    url: '/base/userInfo/del',
    method: 'delete',
    data
  })
}