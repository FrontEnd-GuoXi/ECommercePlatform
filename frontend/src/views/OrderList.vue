<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { showConfirmDialog, showSuccessToast } from 'vant'
import { initialOrders } from '@/mock/data'

const router = useRouter()
const tab = ref<number | 'all'>(0)
const orders = ref([...initialOrders])

const tabs = [
  { key: 'all', label: '全部' },
  { key: 0, label: '待支付' },
  { key: 1, label: '已支付' },
  { key: 3, label: '已完成' },
  { key: 2, label: '已取消' }
]

const filteredOrders = computed(() => {
  if (tab.value === 'all') return orders.value
  return orders.value.filter((o) => o.status === tab.value)
})

function statusText(s: number) {
  return ['待支付', '已支付', '已取消', '已完成'][s] || '-'
}
function statusType(s: number) {
  return ['warning', 'success', 'default', 'primary'][s] || 'default'
}

async function cancelOrder(id: number) {
  try {
    await showConfirmDialog({ title: '提示', message: '确定取消该订单吗？' })
    const o = orders.value.find((x) => x.id === id)
    if (o) o.status = 2
    showSuccessToast('订单已取消')
  } catch {
    // noop
  }
}

function payOrder(id: number) {
  const o = orders.value.find((x) => x.id === id)
  if (o) {
    o.status = 1
    o.payTime = new Date().toLocaleString('zh-CN')
    showSuccessToast('支付成功')
  }
}
</script>

<template>
  <div class="fc-page order-page">
    <van-nav-bar title="我的订单" fixed placeholder safe-area-inset-top />

    <van-tabs v-model:active="tab" sticky shrink>
      <van-tab v-for="t in tabs" :key="String(t.key)" :name="t.key">
        {{ t.label }}
      </van-tab>
    </van-tabs>

    <div class="order-list">
      <van-empty v-if="filteredOrders.length === 0" description="暂无订单" />

      <div v-for="o in filteredOrders" :key="o.id" class="order-card">
        <div class="order-header">
          <span class="order-no">订单号: {{ o.orderNo }}</span>
          <van-tag :type="statusType(o.status) as any" round>{{ statusText(o.status) }}</van-tag>
        </div>

        <div
          v-for="item in o.items"
          :key="item.productId"
          class="order-item"
          @click="router.push(`/product/${item.productId}`)"
        >
          <img :src="item.picUrl" class="oi-img" />
          <div class="oi-info">
            <div class="oi-title">{{ item.productName }}</div>
            <div class="oi-spec">快照价格</div>
          </div>
          <div class="oi-right">
            <div class="oi-price">
              <span class="sym">¥</span>{{ item.price }}
            </div>
            <div class="oi-qty">x{{ item.quantity }}</div>
          </div>
        </div>

        <div class="order-footer">
          <span class="total-label">实付:</span>
          <span class="total-val">
            <span class="sym">¥</span>{{ o.payAmount }}
          </span>
        </div>

        <div class="order-actions">
          <van-button
            v-if="o.status === 0"
            size="small"
            type="primary"
            @click="cancelOrder(o.id)"
          >
            取消订单
          </van-button>
          <van-button
            v-if="o.status === 0"
            size="small"
            type="danger"
            @click="payOrder(o.id)"
          >
            立即支付
          </van-button>
          <van-button v-if="o.status === 1" size="small" plain type="primary">
            查看物流
          </van-button>
          <van-button v-if="o.status === 3" size="small" plain type="danger">
            再次购买
          </van-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.order-list {
  padding: 10px 12px 30px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.order-card {
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
}
.order-header {
  padding: 10px 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #f5f5f5;
  .order-no { color: #999; font-size: 12px; }
}
.order-item {
  display: flex;
  gap: 10px;
  padding: 10px 12px;
}
.oi-img { width: 72px; height: 72px; border-radius: 6px; object-fit: cover; }
.oi-info { flex: 1; min-width: 0; }
.oi-title {
  font-size: 13px;
  color: #323233;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  line-height: 1.3;
}
.oi-spec { color: #999; font-size: 11px; margin-top: 4px; }
.oi-right { text-align: right; }
.oi-price { color: #ff5a4c; font-weight: 600; }
.oi-qty { color: #999; font-size: 12px; margin-top: 4px; }
.order-footer {
  text-align: right;
  padding: 6px 12px;
  border-top: 1px solid #f5f5f5;
  background: #fafafa;
  .total-label { color: #666; font-size: 13px; }
  .total-val { color: #ff5a4c; font-size: 18px; font-weight: 700; margin-left: 4px; }
  .sym { font-size: 12px; }
}
.order-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  padding: 10px 12px;
}
</style>
