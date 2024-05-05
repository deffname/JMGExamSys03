/**
 * @param {string} path
 * @returns {Boolean}
 * 接收一个path参数，使用正则式来判断path是否以https://、http://、mailto:、tel:结尾，是就返回true，不是就返回false
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
// 接收一个参数str,valid_map是一个数组，里面包括一些有效的用户名，去除str左右的空格之后测试其是否在valid_map数组中，如果在就返回true，否则就返回false
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}
