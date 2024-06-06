import request from "@/utils/request"

export function createExam(data) {
  return request({
    url: '/teacher/exam/create',
    method: 'post',
    data
  })
}