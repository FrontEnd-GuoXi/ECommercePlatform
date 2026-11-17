<script setup lang="ts">
import { useRouter } from 'vue-router'
import { showConfirmDialog } from 'vant'
import MainTabBar from '@/components/MainTabBar.vue'
import { useUserStore } from '@/stores/user'
import { useCartStore } from '@/stores/cart'

const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()

const stats = [
  { label: '全部订单', value: 3, path: '/order/list' },
  { label: '优惠券', value: 5 },
  { label: '积分', value: 1280 },
  { label: '收藏', value: 18 }
]

const orderTabs = [
  { label: '待付款', icon: 'gold-coin-o', status: 0 },
  { label: '待发货', icon: 'logistics', status: 1 },
  { label: '待收货', icon: 'passed', status: 1 },
  { label: '已完成', icon: 'description', status: 3 }
]

const menus = [
  { label: '收货地址', icon: 'location-o' },
  { label: '我的优惠券', icon: 'coupon-o' },
  { label: '客服中心', icon: 'service-o' },
  { label: '设置', icon: 'setting-o' }
]

async function handleLogout() {
  try {
    await showConfirmDialog({ title: '提示', message: '确定退出登录吗？' })
    userStore.logout()
    router.replace('/login')
  } catch {
    // noop
  }
}
</script>

<template>
  <div class="fc-page mine-page">
    <!-- 头部背景 -->
    <div class="mine-header">
      <van-nav-bar transparent :title="'我的'" fixed placeholder safe-area-inset-top>
        <template #right>
          <van-icon name="settings-o" color="#fff" size="20" />
        </template>
      </van-nav-bar>

      <div class="user-info" v-if="userStore.isLogin">
        <img :src="userStore.userInfo?.avatar" class="avatar" />
        <div class="user-text">
          <div class="username">{{ userStore.userInfo?.username }}</div>
          <div class="phone">{{ userStore.userInfo?.phone }}</div>
        </div>
        <van-button round plain type="primary" size="small" @click="handleLogout">
          退出登录
        </van-button>
      </div>

      <div class="user-info" v-else>
        <img
          src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=default%20user%20avatar%20gray%20silhouette&image_size=square"
          class="avatar"
        />
        <div class="user-text" @click="router.push('/login')">
          <div class="username">点击登录</div>
          <div class="phone">登录后享受更多服务</div>
        </div>
      </div>
    </div>

    <!-- 统计 -->
    <div class="stats-bar">
      <div v-for="s in stats" :key="s.label" class="stat-item" @click="s.path && router.push(s.path)">
        <div class="stat-val">{{ s.value }}</div>
        <div class="stat-lbl">{{ s.label }}</div>
      </div>
    </div>

    <!-- 我的订单 -->
    <div class="fc-card">
      <div class="card-head">
        <span class="card-title">我的订单</span>
        <span class="card-more" @click="router.push('/order/list')">
          查看全部 <van-icon name="arrow" />
        </span>
      </div>
      <van-grid :column-num="4" :border="false">
        <van-grid-item
          v-for="t in orderTabs"
          :key="t.label"
          @click="router.push('/order/list')"
        >
          <van-icon :name="t.icon" size="22" color="#666" />
          <span>{{ t.label }}</span>
        </van-grid-item>
      </van-grid>
    </div>

    <!-- 更多菜单 -->
    <div class="fc-card menu-card">
      <van-cell-group inset>
        <van-cell
          v-for="m in menus"
          :key="m.label"
          :icon="m.icon"
          :title="m.label"
          is-link
        />
      </van-cell-group>
    </div>

    <MainTabBar />
  </div>
</template>

<style lang="scss" scoped>
.mine-header {
  background: linear-gradient(135deg, #ff5a4c 0%, #ff8a65 100%);
  color: #fff;
  padding-bottom: 60px;
  margin-bottom: -40px;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 30px 20px;
}
.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 2px solid #fff;
}
.user-text { flex: 1; }
.username { font-size: 17px; font-weight: 600; }
.phone { font-size: 12px; opacity: 0.85; margin-top: 4px; }

.stats-bar {
  background: #fff;
  margin: 0 12px;
  border-radius: 10px;
  padding: 16px 0;
  display: flex;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  position: relative;
  z-index: 1;
}
.stat-item { flex: 1; text-align: center; }
.stat-val { font-size: 18px; font-weight: 700; color: #323233; }
.stat-lbl { font-size: 12px; color: #999; margin-top: 2px; }

.card-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px 4px 8px;
}
.card-title { font-size: 14px; font-weight: 600; }
.card-more {
  font-size: 12px;
  color: #999;
  display: flex;
  align-items: center;
}
.menu-card { padding: 0; }
</style>
