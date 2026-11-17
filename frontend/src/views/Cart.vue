<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { showConfirmDialog, showToast } from 'vant'
import MainTabBar from '@/components/MainTabBar.vue'
import { products } from '@/mock/data'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const cartList = computed(() =>
  cartStore.items.map((it) => ({
    ...it,
    product: products.find((p) => p.id === it.productId)!
  }))
)

function goProduct(id: number) {
  router.push(`/product/${id}`)
}

async function handleRemove(productId: number) {
  try {
    await showConfirmDialog({ title: '提示', message: '确定要删除该商品吗？' })
    cartStore.remove(productId)
  } catch {
    // cancel
  }
}

function handleCheckout() {
  if (cartStore.totalCount === 0) {
    showToast('请选择商品')
    return
  }
  if (!userStore.isLogin) {
    showToast('请先登录')
    router.push('/login')
    return
  }
  router.push('/order/confirm')
}
</script>

<template>
  <div class="fc-page cart-page">
    <van-nav-bar title="购物车" fixed placeholder safe-area-inset-top />

    <van-empty v-if="cartList.length === 0" description="购物车空空如也">
      <van-button round type="danger" @click="router.push('/home')">去逛逛</van-button>
    </van-empty>

    <template v-else>
      <!-- 商品列表 -->
      <div class="cart-list">
        <van-swipe-cell v-for="it in cartList" :key="it.productId">
          <template #default>
            <div class="cart-item" @click="goProduct(it.productId)">
              <van-checkbox
                :model-value="it.checked"
                @update:model-value="cartStore.toggleCheck(it.productId)"
                @click.stop
              />
              <img :src="it.product?.picUrl" class="item-img" />
              <div class="item-info">
                <div class="item-title">{{ it.product?.title }}</div>
                <div class="item-spec">库存 {{ it.product?.stock }}</div>
                <div class="item-footer">
                  <span class="item-price">
                    <span class="sym">¥</span>
                    <span class="val">{{ it.product?.price }}</span>
                  </span>
                  <van-stepper
                    :model-value="it.quantity"
                    :min="1"
                    :max="it.product?.stock"
                    @update:model-value="cartStore.updateQuantity(it.productId, Number($event))"
                    @click.stop
                  />
                </div>
              </div>
            </div>
          </template>
          <template #right>
            <van-button square type="danger" plain @click.stop="handleRemove(it.productId)">
              删除
            </van-button>
          </template>
        </van-swipe-cell>
      </div>

      <!-- 结算栏 -->
      <van-submit-bar
        :price="Math.round(cartStore.totalPrice * 100)"
        button-text="去结算"
        @submit="handleCheckout"
      >
        <template #default>
          <van-checkbox
            :model-value="cartStore.allChecked"
            @update:model-value="cartStore.toggleAll(Boolean($event))"
          >
            全选
          </van-checkbox>
        </template>
      </van-submit-bar>
    </template>

    <MainTabBar />
  </div>
</template>

<style lang="scss" scoped>
.cart-list {
  padding: 8px 12px 100px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.cart-item {
  display: flex;
  align-items: center;
  gap: 10px;
  background: #fff;
  border-radius: 10px;
  padding: 10px;
}
.item-img {
  width: 80px;
  height: 80px;
  border-radius: 6px;
  object-fit: cover;
  flex-shrink: 0;
}
.item-info { flex: 1; display: flex; flex-direction: column; gap: 4px; min-width: 0; }
.item-title {
  font-size: 13px;
  color: #323233;
  line-height: 1.3;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}
.item-spec { color: #999; font-size: 11px; }
.item-footer { display: flex; justify-content: space-between; align-items: center; }
.item-price .sym { color: #ff5a4c; font-size: 12px; }
.item-price .val { color: #ff5a4c; font-size: 18px; font-weight: 700; }
</style>
