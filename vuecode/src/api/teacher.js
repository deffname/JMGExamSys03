import request from "@/utils/request"

export function createExam(data) {
  return request({
    url: '/teacher/exam/create',
    method: 'post',
    data
  })
}

export function getExam() {
  return request({
    url: '/teacher/exam/getlist',
    method: 'get',
  })
}

export function getStudent() {
  return request({
    url: '/teacher/getstu',
    method: 'get',
  })
}

export function addStudent(data) {
  return request({
    url: '/teacher/exam/addStu',
    method: 'post',
    data
  })
}