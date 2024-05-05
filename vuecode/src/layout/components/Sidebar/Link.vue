<!-- 一个vue组件的模版和脚本部分 -->
<template>
  <!-- vue的component组件，允许你通过:is绑定来动态切换要渲染的组件(根据computed属性type) -->
  <!-- 绑定了linkReops方法返回的对象 -->
  <component :is="type" v-bind="linkProps(to)">
    <!--  slot允许你在使用这个组件时插入自定义的内容。 -->
    <slot />
  </component>
</template>

<script>
// 根据传入的to prop来动态决定使用哪种类型的组件来渲染链接，并为其绑定相应的属性
import { isExternal } from "@/utils/validate";

export default {
  props: {
    // 这个to可能是外部链接url 或者内部链接vue router的路由路径
    to: {
      type: String,
      required: true,
    },
  },
  computed: {
    isExternal() {
      return isExternal(this.to);
    },
    // 判断链接类型是外部链接还是内部链接
    type() {
      if (this.isExternal) {
        return "a";
      }
      return "router-link";
    },
  },
  methods: {
    linkProps(to) {
      if (this.isExternal) {
        // 如果是外部链接，返回一个对象
        // 对象包括href(链接的url)target控制链接的打开方式(_blank是在新的标签页中打开)
        // rel 属性描述了当前文档与被链接文档之间的关系
        return {
          href: to,
          target: "_blank",
          rel: "noopener",
        };
      }
      // 如果是内部链接就直接返回结果
      return {
        to: to,
      };
    },
  },
};
</script>
