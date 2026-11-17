<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { categories, products } from '@/mock/data'

const router = useRouter()
const activeCat = ref(categories[0])
const activeChild = ref(activeCat.value.children?.[0])

const filteredProducts = computed(() => {
  const subId = activeChild.value?.id
  if (!subId) return []
  return products.filter((p) => p.categoryId === subId)
})

function selectCat(cat: typeof categories[number]) {
  activeCat.value = cat
  activeChild.value = cat.children?.[0]
}

function goProduct(id: number) {
  router.push(`/product/${id}`)
}
</script>

<template>
  <div class="fc-page category-page">
    <van-nav-bar title="商品分类" fixed placeholder safe-area-inset-top />

    <div class="category-body">
      <!-- 左侧分类 -->
      <div class="left-nav">
        <div
          v-for="cat in categories"
          :key="cat.id"
          :class="['cat-item', { active: activeCat.id === cat.id }]"
          @click="selectCat(cat)"
        >
          {{ cat.icon }} {{ cat.name }}
        </div>
      </div>

      <!-- 右侧子分类和商品 -->
      <div class="right-content">
        <div class="sub-cats">
          <van-tag
            v-for="sub in activeCat.children"
            :key="sub.id"
            :type="activeChild?.id === sub.id ? 'danger' : 'primary'"
            :plain="activeChild?.id !== sub.id"
            round
            class="sub-tag"
            @click="activeChild = sub"
          >
            {{ sub.name }}
          </van-tag>
        </div>

        <div class="product-list">
          <div
            v-for="p in filteredProducts"
            :key="p.id"
            class="product-row"
            @click="goProduct(p.id)"
          >
            <img :src="p.picUrl" class="p-img" />
            <div class="p-info">
              <div class="p-title">{{ p.title }}</div>
              <div class="p-price">
                <span class="sym">¥</span>
                <span class="val">{{ p.price }}</span>
              </div>
              <div class="p-sales">已售 {{ p.sales }}</div>
            </div>
          </div>
          <van-empty v-if="filteredProducts.length === 0" description="暂无商品" />
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
.category-body {
  display: flex;
  height: calc(100vh - 56px);
}
.left-nav {
  width: 90px;
  background: #f7f8fa;
  overflow-y: auto;
}
.cat-item {
  padding: 16px 8px;
  text-align: center;
  font-size: 13px;
  color: #666;
  border-left: 3px solid transparent;
  &.active {
    background: #fff;
    color: #ff5a4c;
    font-weight: 600;
    border-left-color: #ff5a4c;
  }
}
.right-content {
  flex: 1;
  background: #fff;
  overflow-y: auto;
  padding: 12px;
}
.sub-cats {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 12px;
}
.sub-tag { cursor: pointer; }

.product-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.product-row {
  display: flex;
  gap: 10px;
  background: #fafafa;
  border-radius: 8px;
  padding: 8px;
}
.p-img {
  width: 90px;
  height: 90px;
  border-radius: 6px;
  object-fit: cover;
}
.p-info { flex: 1; display: flex; flex-direction: column; justify-content: space-between; }
.p-title {
  font-size: 13px;
  color: #323233;
  line-height: 1.3;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}
.p-price .sym { color: #ff5a4c; font-size: 12px; }
.p-price .val { color: #ff5a4c; font-size: 18px; font-weight: 700; }
.p-sales { color: #999; font-size: 11px; }
</style>
