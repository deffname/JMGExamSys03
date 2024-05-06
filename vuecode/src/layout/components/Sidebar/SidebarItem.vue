<!-- 用于渲染侧边栏或菜单中的一个item -->
<template>
  <div v-if="!item.hidden && ifshow(item)">
    <template
      v-if="
        hasOneShowingChild(item.children, item) &&
        (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
        !item.alwaysShow
      "
    >
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item
          :index="resolvePath(onlyOneChild.path)"
          :class="{ 'submenu-title-noDropdown': !isNest }"
        >
          <item
            :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)"
            :title="onlyOneChild.meta.title"
          />
        </el-menu-item>
      </app-link>
    </template>

    <el-submenu
      v-else
      ref="subMenu"
      :index="resolvePath(item.path)"
      popper-append-to-body
    >
      <template slot="title">
        <item
          v-if="item.meta"
          :icon="item.meta && item.meta.icon"
          :title="item.meta.title"
        />
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      />
    </el-submenu>
  </div>
</template>

<script>
import path from "path";
import { isExternal } from "@/utils/validate";
import Item from "./Item";
import AppLink from "./Link";
import FixiOSBug from "./FixiOSBug";

export default {
  name: "SidebarItem",
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    // 包含item的相关信息
    item: {
      type: Object,
      required: true,
    },
    // 标记这个item是否有子菜单
    isNest: {
      type: Boolean,
      default: false,
    },
    // 构建或解析项目的路径
    basePath: {
      type: String,
      default: "",
    },
  },
  data() {
    // To fix https://github.com/PanJiaChen/vue-admin-template/issues/237
    // TODO: refactor with render function
    // 存储唯一被显示的子项
    this.onlyOneChild = null;
    return {};
  },
  methods: {
    ifshow(item) {
      console.log(item.path, "被调用判断");
      if (item.meta) {
        // 如果当前router的分类不应该出现在当前用户界面中
        if (item.meta.belong != this.$store.getters.urole) {
          return false;
        }
      }
      return true;
    },
    // 接收两个对象，一个是子项数组，一个是父项对象
    hasOneShowingChild(children = [], parent) {
      // 用filter方法筛选出子项中不隐藏的对象，并把当前的onlyOneChild设置为这个对象
      // 找到所有没有隐藏的对象放入showingChildren数组中
      const showingChildren = children.filter((item) => {
        console.log(children, "----", parent);
        if (item.hidden) {
          return false;
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item;
          return true;
        }
      });

      // 当只有一个子路由器时，默认显示子路由器
      if (showingChildren.length === 1) {
        return true;
      }

      // 如果没有子项对象就返回父类对象
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: "", noShowingChildren: true };
        return true;
      }

      // 显示子项多于一个，返回false
      return false;
    },

    resolvePath(routePath) {
      if (isExternal(routePath)) {
        // 是外部链接就返回这个链接
        return routePath;
      }
      if (isExternal(this.basePath)) {
        return this.basePath;
      }
      // 使用node.js的resolve方法将basePath与routePath合并为一个完整的路径
      // console.log("basepath = ", this.basePath, " routePath = ", routePath);
      return path.resolve(this.basePath, routePath);
    },
  },
};
</script>
