import request from "@/utils/request"

export function getSExam() {
  return request({
    url: '/student/getExaml',
    method: 'get',

  })
}

export function getSExamPaper(eid) {
  return request({
    url: '/student/getExampaper',
    method: 'get',
    params: {
      eid: eid
    }
  })
}