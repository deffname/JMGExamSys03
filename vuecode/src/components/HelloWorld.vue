<template>
  <div class="hello">
    <!-- {{ this.$store.state.count }} -->
    {{ count }}
    <button @click="add">+1</button>
    
    <ul>
      <li v-for="todo in todos" :key="todo.id">{{ todo.text }}</li>
    </ul>

    <ul>
      <li v-for="todo in doneTodos" :key="todo.id">{{ todo.text }}</li>
    </ul>
  </div>
</template>

<script>
import { mapState,mapGetters } from 'vuex'

export default {
  name: 'HelloWorld',
  // computed是vue里面的计算属性，根据依赖关系进行缓存的计算
  // 只有在它的相关依赖发生变化的时候才会进行更新，当一个数据受多个数据影响的时候可以使用
  // 计算属性会自动监听内部的数据，如果页面发生变化会自动刷新
  // computed:{
  //   count(){
  //     return this.$store.state.count
  //   }
  // },
  // 下面是利用mapState的简写方法
  computed:{
      ...mapState([
      // 方法名称 ：你要取state里面的什么东西
      // count : state=> state.count

      // 或者直接写名称（此时计算属性名称和state里面的子节点名称相同）
      'count','todos'
    ]),
    ...mapGetters([
      'doneTodos'
    ])
  },
  methods:{
    add(){
      this.$store.commit("increment",2)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
