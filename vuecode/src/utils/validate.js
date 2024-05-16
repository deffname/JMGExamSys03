// 这个文件初始化的时候store还没有完成初始化
// import store from "@/store"
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
  // const valid_map = ['admin', 'editor']
  return true
}

// /**
//  * @param {string} role
//  * @returns {Boolean}
//  * 传入一个参数，表示当前路由应该在哪个身份下显示，在这里和当前的vuex进行判断此时是否应该隐藏
//  */
// export function isHidden(role) {
//   // console.log('验证是否隐藏的函数调用', store.state.user.urole);
//   // return store.getters.urole != role
//   console.log('store可能尚未初始化');
//   return store.getters.urole!=role
// }
