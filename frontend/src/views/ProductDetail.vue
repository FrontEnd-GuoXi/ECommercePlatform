<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { showToast, showSuccessToast } from 'vant'
import { products } from '@/mock/data'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const productId = computed(() => Number(route.params.id))
const product = computed(() => products.find((p) => p.id === productId.value))

const localQty = ref(1)

function addToCart() {
  if (!product.value) return
  cartStore.add(product.value.id, localQty.value)
  showSuccessToast({ message: '已加入购物车', position: 'top' })
}

function buyNow() {
  if (!product.value) return
  if (!userStore.isLogin) {
    showToast('请先登录')
    router.push('/login')
    return
  }
  cartStore.add(product.value.id, localQty.value)
  router.push('/order/confirm')
}
</script>

<template>
  <div class="fc-page detail-page" v-if="product">
    <van-nav-bar
      :title="product.title.length > 14 ? product.title.slice(0, 14) + '...' : product.title"
      fixed
      placeholder
      safe-area-inset-top
    >
      <template #right>
        <van-icon name="cart-o" size="20" @click="router.push('/cart')" />
      </template>
    </van-nav-bar>

    <!-- 轮播图 -->
    <van-swipe class="detail-swipe" :autoplay="4000" indicator-color="#fff">
      <van-swipe-item>
        <img :src="product.picUrl" class="detail-img" />
      </van-swipe-item>
      <van-swipe-item v-for="(pic, i) in product.pics" :key="i">
        <img :src="pic" class="detail-img" />
      </van-swipe-item>
    </van-swipe>

    <!-- 价格区 -->
    <div class="price-card fc-card">
      <div class="price-main">
        <span class="sym">¥</span>
        <span class="val">{{ product.price }}</span>
        <span v-if="product.originalPrice" class="origin">¥{{ product.originalPrice }}</span>
      </div>
      <div class="title-row">
        <span class="title">{{ product.title }}</span>
        <van-icon name="share-o" size="18" color="#999" />
      </div>
      <div class="tags-row" v-if="product.tags?.length">
        <van-tag v-for="t in product.tags" :key="t" type="danger" plain round size="medium">
          {{ t }}
        </van-tag>
      </div>
      <div class="meta-row">
        <span>已售 {{ product.sales }}</span>
        <span>库存 {{ product.stock }}</span>
      </div>
    </div>

    <!-- 服务保障 -->
    <div class="fc-card service-bar">
      <span><van-icon name="passed" color="#07c160" /> 正品保障</span>
      <span><van-icon name="shield-o" color="#07c160" /> 7天无理由</span>
      <span><van-icon name="logistics" color="#07c160" /> 极速发货</span>
      <span><van-icon name="gold-coin-o" color="#07c160" /> 假一赔十</span>
    </div>

    <!-- 规格选择 -->
    <div class="fc-card spec-row">
      <span class="label">数量</span>
      <van-stepper v-model="localQty" :min="1" :max="product.stock" />
    </div>

    <!-- 商品详情 -->
    <div class="detail-section-title">商品详情</div>
    <div class="fc-card detail-card">
      <p>{{ product.detail }}</p>
      <img v-if="product.pics?.[0]" :src="product.pics[0]" class="detail-extra" />
      <img v-if="product.pics?.[1]" :src="product.pics[1]" class="detail-extra" />
    </div>

    <!-- 底部操作栏 -->
    <van-submit-bar
      :price="product.price * localQty * 100"
      button-text="立即购买"
      @submit="buyNow"
    >
      <template #default>
        <div class="bottom-actions">
          <van-icon name="chat-o" size="22" badge="3" class="bottom-icon" />
          <van-icon name="cart-o" size="22" class="bottom-icon" @click="router.push('/cart')" />
        </div>
      </template>
      <template #button>
        <van-button round type="primary" block @click="buyNow">立即购买</van-button>
      </template>
    </van-submit-bar>

    <div style="padding: 0 12px 80px; background: #fff">
      <van-button round type="warning" block @click="addToCart">加入购物车</van-button>
    </div>
  </div>

  <van-empty v-else description="商品不存在" />
</template>

<style lang="scss" scoped>
.detail-swipe {
  width: 100%;
  height: 375px;
  background: #fff;
}
.detail-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.price-card {
  .price-main {
    .sym { color: #ff5a4c; font-size: 16px; font-weight: 600; }
    .val { color: #ff5a4c; font-size: 30px; font-weight: 700; margin-right: 8px; }
    .origin { color: #999; font-size: 13px; text-decoration: line-through; }
  }
  .title-row {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 8px;
    margin: 10px 0 6px;
  }
  .title { font-size: 16px; font-weight: 600; color: #323233; line-height: 1.4; flex: 1; }
  .tags-row { display: flex; gap: 6px; margin-bottom: 8px; }
  .meta-row { display: flex; gap: 16px; color: #999; font-size: 12px; }
}
.service-bar {
  display: flex;
  justify-content: space-around;
  font-size: 12px;
  color: #666;
  padding: 14px 8px;
  span { display: flex; align-items: center; gap: 4px; }
}
.spec-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  .label { color: #323233; }
}
.detail-section-title {
  padding: 16px 16px 8px;
  font-size: 15px;
  font-weight: 600;
  color: #323233;
}
.detail-card {
  p { color: #666; font-size: 14px; line-height: 1.7; margin: 0 0 12px; }
}
.detail-extra {
  width: 100%;
  border-radius: 8px;
  margin-bottom: 8px;
}
.bottom-actions {
  display: flex;
  gap: 20px;
  padding-right: 12px;
}
.bottom-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #666;
}
</style>
