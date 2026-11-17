<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()

const tabs = [
  { name: '/home', title: '首页', icon: 'home-o' },
  { name: '/category', title: '分类', icon: 'apps-o' },
  { name: '/seckill', title: '秒杀', icon: 'fire-o' },
  { name: '/cart', title: '购物车', icon: 'cart-o', badge: cartStore.totalCount },
  { name: '/mine', title: '我的', icon: 'user-o' }
]

function isActive(name: string) {
  return route.path.startsWith(name)
}

function switchTab(name: string) {
  if (route.path !== name) router.push(name)
}
</script>

<template>
  <van-tabbar route inactive-color="#969799" active-color="#ff5a4c" fixed placeholder>
    <van-tabbar-item
      v-for="tab in tabs"
      :key="tab.name"
      :to="tab.name"
      :icon="tab.icon"
      :badge="tab.badge && tab.badge > 0 ? tab.badge : ''"
    >
      {{ tab.title }}
    </van-tabbar-item>
  </van-tabbar>
</template>
