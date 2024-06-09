const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  urole: state => state.user.urole,
  baseApi: state => state.api.baseApi,
  rid: state => state.user.rid
}
export default getters
