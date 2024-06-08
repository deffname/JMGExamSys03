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

export function getEStudent(eid) {
  return request({
    url: '/teacher/getestu',
    method: 'get',
    params: {
      eid: eid
    }
  })
}

export function addStudent(data) {
  return request({
    url: '/teacher/exam/addStu',
    method: 'post',
    data
  })
}

export function deleteExam(data) {
  return request({
    url: '/teacher/exam/deleteExam',
    method: 'post',
    data
  })
}

export function deleteStu(data) {
  return request({
    url: '/teacher/exam/deleteStu',
    method: 'post',
    data
  })
}