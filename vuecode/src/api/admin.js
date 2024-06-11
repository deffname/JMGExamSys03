import request from "@/utils/request"

export function getAllUser() {
  return request({
    url: '/student/getAllUser',
    method: 'get',
  })
}

export function getAllExam() {
  return request({
    url: '/student/getAllExam',
    method: 'get',
  })
}

/**
 * 修改对应用户的密码
 * @param {要修改的用户的id} uid 
 * @returns 
 */
export function updatePassword(data) {
  return request({
    url: '/student/updatepwd',
    method: 'post',
    data
  })
}

/**
 * 设置开启考试最长时间
 * @param {uid:,newped:,} data 
 * @returns 
 */
export function setStartTime(data) {
  return request({
    url: '/student/setSTime',
    method: 'post',
    data
  })
}

/**
 * 
 * @param {eid:,state:,} data 
 * @returns 
 */
export function setEState(data) {
  return request({
    url: '/student/setstate',
    method: 'post',
    data
  })
}