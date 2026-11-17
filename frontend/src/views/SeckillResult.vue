<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const orderId = route.params.orderId as string
</script>

<template>
  <div class="fc-page result-page">
    <van-nav-bar title="秒杀结果" fixed placeholder safe-area-inset-top />

    <div class="result-wrap">
      <div class="result-icon">🎉</div>
      <div class="result-title">恭喜抢购成功！</div>
      <div class="result-sub">订单号：{{ orderId }}</div>

      <div class="result-actions">
        <van-button round block type="primary" @click="router.push('/order/list')">
          查看订单
        </van-button>
        <van-button round block plain type="primary" @click="router.push('/home')">
          继续购物
        </van-button>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.result-wrap {
  text-align: center;
  padding: 60px 40px;
}
.result-icon { font-size: 60px; margin-bottom: 20px; }
.result-title { font-size: 20px; font-weight: 700; color: #323233; margin-bottom: 10px; }
.result-sub { color: #999; font-size: 13px; margin-bottom: 40px; }
.result-actions { display: flex; flex-direction: column; gap: 12px; }
</style>
