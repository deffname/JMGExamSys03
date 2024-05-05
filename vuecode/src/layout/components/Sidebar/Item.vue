<script>
// 这个组件是用来渲染侧边栏中的各个item的
// 定义一个组件，这是一个函数式组件，无状态无实例
export default {
  name: "MenuItem",
  functional: true,
  props: {
    // icon表示图标的类名或者svg图标的类名前缀
    // 如果它包含 'el-icon'，则被视为 Element UI 的图标类；否则，被视为 SVG 图标的类名前缀。
    icon: {
      type: String,
      default: "",
    },
    title: {
      type: String,
      default: "",
    },
  },
  // 一个渲染函数
  render(h, context) {
    const { icon, title } = context.props;
    const vnodes = [];
    // 如果icon存在，执行下面的判断
    if (icon) {
      // 检查是否包含el-icon标签
      if (icon.includes("el-icon")) {
        // 包含时，创建一个<i>标签(表示斜体)，添加icon对应的类名以及一个额外的sub-el-icon类名
        vnodes.push(<i class={[icon, "sub-el-icon"]} />);
      } else {
        // 不包含时，创建一个svg-icon组件，并且传递一个名为icon-class的prop，属性为icon
        vnodes.push(<svg-icon icon-class={icon} />);
      }
    }

    if (title) {
      vnodes.push(<span slot="title">{title}</span>);
    }
    // 函数最后返回 vnodes数组，vue将根据这个数组渲染出实际的dom
    return vnodes;
  },
};
</script>

<style scoped>
.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
}
</style>
