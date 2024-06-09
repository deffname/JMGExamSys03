import request from "@/utils/request"

export function getSExam() {
  return request({
    url: '/student/getExaml',
    method: 'get',

  })
}