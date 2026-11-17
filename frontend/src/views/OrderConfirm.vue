<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { showToast, showSuccessToast } from 'vant'
import { products } from '@/mock/data'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const address = ref({
  name: userStore.userInfo?.username || '张小明',
  phone: userStore.userInfo?.phone || '138****8888',
  detail: '北京市朝阳区建国路88号SOHO现代城A座1208'
})

const remark = ref('')

const checkedItems = computed(() =>
  cartStore.items
    .filter((it) => it.checked)
    .map((it) => ({
      ...it,
      product: products.find((p) => p.id === it.productId)!
    }))
)

const goodsAmount = computed(() => cartStore.totalPrice)
const freight = computed(() => (cartStore.totalPrice >= 99 ? 0 : 8))
const totalAmount = computed(() => goodsAmount.value + freight.value)

function submitOrder() {
  if (checkedItems.value.length === 0) {
    showToast('请选择商品')
    return
  }
  // 模拟提交订单 -> 生成订单号 -> 清空已选购物车 -> 跳订单列表
  const orderNo = 'FC' + Date.now()
  cartStore.clearChecked()
  showSuccessToast({ message: `下单成功！订单号 ${orderNo}`, position: 'top' })
  setTimeout(() => {
    router.push('/order/list')
  }, 1200)
}
</script>

<template>
  <div class="fc-page confirm-page">
    <van-nav-bar title="确认订单" fixed placeholder safe-area-inset-top />

    <!-- 地址 -->
    <van-cell-group inset class="addr-card">
      <van-cell>
        <template #title>
          <div class="addr-main">
            <div class="addr-user">
              <span class="name">{{ address.name }}</span>
              <span class="phone">{{ address.phone }}</span>
              <van-tag type="danger" round plain size="medium">默认</van-tag>
            </div>
            <div class="addr-detail">{{ address.detail }}</div>
          </div>
        </template>
        <template #right-icon>
          <van-icon name="arrow" color="#999" />
        </template>
      </van-cell>
    </van-cell-group>

    <!-- 商品 -->
    <div class="fc-section-title">商品清单</div>
    <div class="goods-card">
      <div v-for="it in checkedItems" :key="it.productId" class="goods-row">
        <img :src="it.product.picUrl" class="g-img" />
        <div class="g-info">
          <div class="g-title">{{ it.product.title }}</div>
          <div class="g-spec">商品快照</div>
          <div class="g-footer">
            <span class="g-price">
              <span class="sym">¥</span>{{ it.product.price }}
            </span>
            <span class="g-qty">x{{ it.quantity }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 金额 -->
    <van-cell-group inset class="amount-card">
      <van-cell title="商品金额">
        <template #value>¥{{ goodsAmount.toFixed(2) }}</template>
      </van-cell>
      <van-cell title="运费">
        <template #value>{{ freight === 0 ? '免运费' : `¥${freight.toFixed(2)}` }}</template>
      </van-cell>
      <van-cell title="备注">
        <template #value>
          <van-field v-model="remark" placeholder="选填，建议10字内" maxlength="20" />
        </template>
      </van-cell>
    </van-cell-group>

    <!-- 提交 -->
    <van-submit-bar
      :price="Math.round(totalAmount * 100)"
      button-text="提交订单"
      @submit="submitOrder"
    />
  </div>
</template>

<style lang="scss" scoped>
.addr-card {
  margin-top: 12px;
  border-radius: 10px;
}
.addr-main { padding: 8px 0; }
.addr-user {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 6px;
  .name { font-weight: 600; font-size: 15px; }
  .phone { color: #666; font-size: 14px; }
}
.addr-detail { color: #323233; font-size: 13px; line-height: 1.5; }

.goods-card {
  background: #fff;
  border-radius: 10px;
  margin: 8px 12px;
  padding: 4px 12px;
}
.goods-row {
  display: flex;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px solid #f5f5f5;
  &:last-child { border-bottom: none; }
}
.g-img { width: 78px; height: 78px; border-radius: 6px; object-fit: cover; }
.g-info { flex: 1; display: flex; flex-direction: column; gap: 4px; min-width: 0; }
.g-title {
  font-size: 13px;
  color: #323233;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}
.g-spec { color: #999; font-size: 11px; }
.g-footer { display: flex; justify-content: space-between; align-items: center; margin-top: auto; }
.g-price .sym { color: #ff5a4c; font-size: 12px; }
.g-price { color: #ff5a4c; font-weight: 600; }
.g-qty { color: #666; font-size: 13px; }

.amount-card { margin: 12px; border-radius: 10px; }
</style>
