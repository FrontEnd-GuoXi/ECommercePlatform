<script setup lang="ts">
import { useRouter } from 'vue-router'

defineProps<{
  title: string
  showBack?: boolean
  rightText?: string
}>()

const emit = defineEmits<{
  (e: 'back'): void
  (e: 'right'): void
}>()

const router = useRouter()

function handleBack() {
  emit('back')
  if (window.history.length > 1) router.back()
  else router.push('/home')
}
</script>

<template>
  <van-nav-bar
    :title="title"
    :left-arrow="showBack !== false"
    left-text="返回"
    fixed
    placeholder
    safe-area-inset-top
    @click-left="handleBack"
    @click-right="$emit('right')"
  >
    <template #right>
      <span v-if="rightText" style="color: #ff5a4c">{{ rightText }}</span>
    </template>
  </van-nav-bar>
