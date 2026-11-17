import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export interface UserInfo {
  id: number
  username: string
  phone: string
  avatar: string
}

export const useUserStore = defineStore('user', () => {
  const token = ref<string | null>(null)
  const userInfo = ref<UserInfo | null>(null)

  const isLogin = computed(() => !!token.value)

  function login(username: string) {
    token.value = 'mock-jwt-token-' + Date.now()
    userInfo.value = {
      id: 1,
      username,
      phone: '138****8888',
      avatar: `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${encodeURIComponent(
        'cute cartoon avatar, portrait'
      )}&image_size=square`
    }
  }

  function logout() {
    token.value = null
    userInfo.value = null
  }

  return { token, userInfo, isLogin, login, logout }
})
