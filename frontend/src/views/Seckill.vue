<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { showConfirmDialog, showToast, showSuccessToast } from 'vant'
import MainTabBar from '@/components/MainTabBar.vue'
import { seckillActivities } from '@/mock/data'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const countdowns = ref<Record<number, { text: string; status: string }>>({})
let timer: number | null = null

function updateCountdowns() {
  const now = Date.now()
  seckillActivities.forEach((a) => {
    const start = new Date(a.startTime).getTime()
    const end = new Date(a.endTime).getTime()
    let status: string
    let diff: number
    if (now < start) {
      status = '即将开始'
      diff = start - now
    } else if (now >= start && now < end && a.sold < a.stock) {
      status = '抢购中'
      diff = end - now
    } else {
      status = '已结束'
      diff = 0
    }
    countdowns.value[a.id] = { text: formatDiff(diff), status }
  })
}

function formatDiff(ms: number) {
  if (ms <= 0) return '00:00:00'
  const h = Math.floor(ms / 3600000)
  const m = Math.floor((ms % 3600000) / 60000)
  const s = Math.floor((ms % 60000) / 1000)
  return `${pad(h)}:${pad(m)}:${pad(s)}`
}
const pad = (n: number) => String(n).padStart(2, '0')

onMounted(() => {
  updateCountdowns()
  timer = window.setInterval(updateCountdowns, 1000)
})
onUnmounted(() => {
  if (timer) clearInterval(timer)
})

const byStatus = computed(() => ({
  ongoing: seckillActivities.filter((a) => a.status === 'ongoing'),
  upcoming: seckillActivities.filter((a) => a.status === 'upcoming'),
  ended: seckillActivities.filter((a) => a.status === 'ended')
}))

async function joinSeckill(activityId: number) {
  const activity = seckillActivities.find((a) => a.id === activityId)
  if (!activity) return

  const cd = countdowns.value[activityId]
  if (cd?.status === '已结束') {
    showToast('活动已结束')
    return
  }
  if (cd?.status === '即将开始') {
    showToast(`活动未开始，请等待 ${cd.text}`)
    return
  }
  if (activity.sold >= activity.stock) {
    showToast('已售罄')
    return
  }

  if (!userStore.isLogin) {
    showToast('请先登录')
    router.push('/login')
    return
  }

  try {
    await showConfirmDialog({
      title: '秒杀确认',
      message: `确认以 ¥${activity.seckillPrice} 秒杀「${activity.product.title}」？`,
      confirmButtonText: '立即抢购',
      confirmButtonColor: '#ff5a4c'
    })
    // 模拟秒杀 -> 走异步排队
    showToast({ message: '排队中...', duration: 800 })
    setTimeout(() => {
      // 90% 概率成功
      const success = Math.random() > 0.1
      if (success) {
        const mockOrderId = 'SK' + Date.now()
        showSuccessToast({ message: '抢购成功!', duration: 1200 })
        setTimeout(() => router.push(`/seckill/result/${mockOrderId}`), 800)
      } else {
        showToast('很遗憾，未抢到，下次再来！')
      }
    }, 1500)
  } catch {
    // noop
  }
}

function goProduct(id: number) {
  router.push(`/product/${id}`)
}
</script>

<template>
  <div class="fc-page seckill-page">
    <div class="seckill-header">
      <van-nav-bar title="限时秒杀" fixed placeholder safe-area-inset-top />
      <div class="hero-banner">
        <div class="hero-title">⚡ 限时秒杀</div>
        <div class="hero-sub">每日10点开抢 · 先到先得</div>
      </div>
    </div>

    <!-- 抢购中 -->
    <section v-if="byStatus.ongoing.length">
      <div class="section-bar">
        <span class="dot red"></span>
        <span class="lbl">抢购中</span>
      </div>
      <div class="seckill-grid">
        <div
          v-for="a in byStatus.ongoing"
          :key="a.id"
          class="sk-card"
          @click="goProduct(a.productId)"
        >
          <img :src="a.product.picUrl" class="sk-img" />
          <div class="sk-info">
            <div class="sk-title">{{ a.product.title }}</div>
            <div class="sk-price-row">
              <span class="sk-price">
                <span class="sym">¥</span>{{ a.seckillPrice }}
              </span>
              <span class="sk-origin">¥{{ a.originalPrice }}</span>
            </div>
            <div class="sk-countdown">{{ countdowns[a.id]?.text }}</div>
            <div
              class="sk-btn"
              @click.stop="joinSeckill(a.id)"
              :class="{ disabled: a.sold >= a.stock }"
            >
              {{ a.sold >= a.stock ? '已售罄' : '立即抢购' }}
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 即将开始 -->
    <section v-if="byStatus.upcoming.length">
      <div class="section-bar">
        <span class="dot gray"></span>
        <span class="lbl">即将开始</span>
      </div>
      <div class="seckill-grid">
        <div
          v-for="a in byStatus.upcoming"
          :key="a.id"
          class="sk-card coming"
          @click="goProduct(a.productId)"
        >
          <img :src="a.product.picUrl" class="sk-img" />
          <div class="sk-info">
            <div class="sk-title">{{ a.product.title }}</div>
            <div class="sk-price-row">
              <span class="sk-price">
                <span class="sym">¥</span>{{ a.seckillPrice }}
              </span>
              <span class="sk-origin">¥{{ a.originalPrice }}</span>
            </div>
            <div class="sk-countdown gray">{{ countdowns[a.id]?.text }}</div>
            <div class="sk-btn wait">等待开始</div>
          </div>
        </div>
      </div>
    </section>

    <!-- 已结束 -->
    <section v-if="byStatus.ended.length">
      <div class="section-bar">
        <span class="dot gray"></span>
        <span class="lbl">已结束</span>
      </div>
      <div class="seckill-grid">
        <div
          v-for="a in byStatus.ended"
          :key="a.id"
          class="sk-card ended"
          @click="goProduct(a.productId)"
        >
          <img :src="a.product.picUrl" class="sk-img" />
          <div class="sk-info">
            <div class="sk-title">{{ a.product.title }}</div>
            <div class="sk-price-row">
              <span class="sk-price">
                <span class="sym">¥</span>{{ a.seckillPrice }}
              </span>
              <span class="sk-origin">¥{{ a.originalPrice }}</span>
            </div>
            <div class="sk-btn gray">已结束</div>
          </div>
        </div>
      </div>
    </section>

    <MainTabBar />
  </div>
</template>

<style lang="scss" scoped>
.seckill-header {
  background: linear-gradient(135deg, #ff5a4c 0%, #ff8a65 100%);
  padding-bottom: 20px;
  margin-bottom: -10px;
}
.hero-banner {
  text-align: center;
  padding: 30px 20px 10px;
  color: #fff;
}
.hero-title { font-size: 24px; font-weight: 700; }
.hero-sub { font-size: 12px; opacity: 0.85; margin-top: 4px; }

.section-bar {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 14px 14px 8px;
  font-size: 14px;
  font-weight: 600;
  color: #323233;
  .dot {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    &.red { background: #ff5a4c; }
    &.gray { background: #c8c9cc; }
  }
}

.seckill-grid {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 0 12px 20px;
}
.sk-card {
  display: flex;
  gap: 12px;
  background: #fff;
  border-radius: 10px;
  padding: 10px;
  &.ended { opacity: 0.6; }
}
.sk-img {
  width: 100px;
  height: 100px;
  border-radius: 8px;
  object-fit: cover;
  flex-shrink: 0;
}
.sk-info { flex: 1; display: flex; flex-direction: column; gap: 4px; }
.sk-title {
  font-size: 13px;
  color: #323233;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  line-height: 1.3;
}
.sk-price-row { display: flex; align-items: baseline; gap: 6px; margin-top: 2px; }
.sk-price {
  color: #ff5a4c;
  font-weight: 700;
  font-size: 20px;
  .sym { font-size: 13px; }
}
.sk-origin { color: #999; font-size: 12px; text-decoration: line-through; }
.sk-countdown {
  font-size: 14px;
  font-weight: 600;
  color: #ff5a4c;
  letter-spacing: 2px;
  font-family: 'Courier New', monospace;
  margin-top: 4px;
  &.gray { color: #999; }
}
.sk-btn {
  margin-top: auto;
  align-self: flex-start;
  background: linear-gradient(135deg, #ff5a4c, #ff8a65);
  color: #fff;
  border-radius: 20px;
  padding: 4px 14px;
  font-size: 12px;
  font-weight: 600;
  cursor: pointer;
  &.wait,
  &.gray { background: #e6e6e6; color: #999; cursor: not-allowed; }
  &.disabled { opacity: 0.6; cursor: not-allowed; }
}
</style>
