<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { banners, categories, products, seckillActivities } from '@/mock/data'

const router = useRouter()
const keyword = ref('')
const showSearch = ref(false)

const ongoingSeckill = seckillActivities.find((a) => a.status === 'ongoing')

function goProduct(id: number) {
  router.push(`/product/${id}`)
}

function goSeckill() {
  router.push('/seckill')
}

function goCategory() {
  router.push('/category')
}

function doSearch() {
  if (keyword.value.trim()) {
    router.push('/category')
  }
}

const gridIcons = [
  { icon: '📱', label: '手机', cat: 101 },
  { icon: '💻', label: '电脑', cat: 102 },
  { icon: '👟', label: '鞋包', cat: 203 },
  { icon: '💄', label: '美妆', cat: 301 },
  { icon: '🍎', label: '水果', cat: 401 },
  { icon: '🏠', label: '家居', cat: 501 },
  { icon: '🎧', label: '数码', cat: 104 },
  { icon: '🎁', label: '更多', cat: 0 }
]
</script>

<template>
  <div class="fc-page">
    <van-nav-bar fixed placeholder safe-area-inset-top>
      <template #title>
        <van-search
          v-model="keyword"
          shape="round"
          placeholder="搜索商品 / 品牌"
          @click-input="showSearch = true"
          @search="doSearch"
        />
      </template>
    </van-nav-bar>

    <!-- 轮播图 -->
    <van-swipe class="banner" :autoplay="3500" indicator-color="#fff" height="180px">
      <van-swipe-item v-for="b in banners" :key="b.id">
        <div class="banner-item" :style="{ backgroundImage: `url(${b.img})` }">
          <div class="banner-text">
            <div class="banner-title">{{ b.title }}</div>
            <div class="banner-sub">{{ b.subtitle }}</div>
          </div>
        </div>
      </van-swipe-item>
    </van-swipe>

    <!-- 分类快捷入口 -->
    <van-grid :column-num="4" :border="false" class="quick-grid">
      <van-grid-item v-for="g in gridIcons" :key="g.label" @click="goCategory">
        <div class="quick-icon">{{ g.icon }}</div>
        <div class="quick-label">{{ g.label }}</div>
      </van-grid-item>
    </van-grid>

    <!-- 秒杀专区入口 -->
    <div class="fc-card seckill-entry" @click="goSeckill">
      <div class="seckill-header">
        <div class="seckill-title">
          <van-icon name="fire-o" color="#ff5a4c" />
          <span>限时秒杀</span>
          <van-tag type="danger" round>每日10点开抢</van-tag>
        </div>
        <van-icon name="arrow" color="#999" />
      </div>
      <div class="seckill-body" v-if="ongoingSeckill">
        <div class="seckill-product">
          <img :src="ongoingSeckill.product.picUrl" class="seckill-img" />
          <div class="seckill-info">
            <div class="seckill-price">
              <span class="symbol">¥</span>
              <span class="value">{{ ongoingSeckill.seckillPrice }}</span>
              <span class="origin">¥{{ ongoingSeckill.originalPrice }}</span>
            </div>
            <div class="seckill-progress">
              <van-progress
                :percentage="Math.round((ongoingSeckill.sold / ongoingSeckill.stock) * 100)"
                color="#ff5a4c"
                background="#ffe8e6"
                :show-pivot="false"
              />
              <span class="progress-text">已抢{{ ongoingSeckill.sold }}/{{ ongoingSeckill.stock }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 今日推荐 -->
    <div class="fc-section-title">今日推荐</div>
    <div class="product-list">
      <div
        v-for="p in products"
        :key="p.id"
        class="product-card"
        @click="goProduct(p.id)"
      >
        <img :src="p.picUrl" class="product-img" />
        <div class="product-info">
          <div class="product-title">{{ p.title }}</div>
          <div class="product-tags" v-if="p.tags?.length">
            <van-tag
              v-for="t in p.tags"
              :key="t"
              size="medium"
              type="danger"
              plain
              round
            >
              {{ t }}
            </van-tag>
          </div>
          <div class="product-footer">
            <div class="price-row">
              <span class="price-symbol">¥</span>
              <span class="price-value">{{ p.price }}</span>
              <span v-if="p.originalPrice && p.originalPrice > p.price" class="price-origin">
                ¥{{ p.originalPrice }}
              </span>
            </div>
            <span class="sales">已售{{ p.sales }}</span>
          </div>
        </div>
      </div>
    </div>

    <MainTabBar />
  </div>
</template>

<script lang="ts">
import MainTabBar from '@/components/MainTabBar.vue'
export default { components: { MainTabBar } }
</script>

<style lang="scss" scoped>
.banner {
  margin: 8px 12px;
  border-radius: 10px;
  overflow: hidden;

  .banner-item {
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: center;
    position: relative;
    &::after {
      content: '';
      position: absolute;
      inset: 0;
      background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 40%, rgba(0, 0, 0, 0.4));
    }
  }
  .banner-text {
    position: absolute;
    left: 16px;
    bottom: 16px;
    color: #fff;
    z-index: 1;
  }
  .banner-title { font-size: 18px; font-weight: 600; margin-bottom: 4px; }
  .banner-sub { font-size: 12px; opacity: 0.9; }
}

.quick-grid {
  margin: 4px 12px;
  background: #fff;
  border-radius: 10px;

  :deep(.van-grid-item__content) {
    padding: 10px 0;
  }
}
.quick-icon { font-size: 24px; margin-bottom: 4px; }
.quick-label { font-size: 12px; color: #666; }

.seckill-entry {
  .seckill-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 12px;
  }
  .seckill-title {
    display: flex;
    align-items: center;
    gap: 6px;
    font-weight: 600;
  }
  .seckill-product {
    display: flex;
    gap: 12px;
  }
  .seckill-img {
    width: 80px;
    height: 80px;
    border-radius: 8px;
    object-fit: cover;
  }
  .seckill-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .seckill-price {
    margin-bottom: 8px;
    .symbol { color: #ff5a4c; font-size: 14px; font-weight: 600; }
    .value { color: #ff5a4c; font-size: 24px; font-weight: 700; }
    .origin { color: #999; font-size: 12px; text-decoration: line-through; margin-left: 8px; }
  }
  .seckill-progress {
    display: flex;
    align-items: center;
    gap: 8px;
    :deep(.van-progress) { flex: 1; }
    .progress-text { font-size: 11px; color: #ff5a4c; white-space: nowrap; }
  }
}

.product-list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  padding: 4px 12px 12px;
}
.product-card {
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
.product-img {
  width: 100%;
  aspect-ratio: 1;
  object-fit: cover;
}
.product-info {
  padding: 8px 10px 10px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.product-title {
  font-size: 13px;
  color: #323233;
  line-height: 1.3;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  min-height: 34px;
}
.product-tags { display: flex; gap: 4px; flex-wrap: wrap; }
.price-row {
  .price-symbol { color: #ff5a4c; font-size: 12px; font-weight: 600; }
  .price-value { color: #ff5a4c; font-size: 18px; font-weight: 700; }
  .price-origin { color: #999; font-size: 11px; text-decoration: line-through; margin-left: 4px; }
}
.product-footer {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
}
.sales { color: #999; font-size: 11px; }
</style>
