<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { showToast, showSuccessToast } from 'vant'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const mode = ref<'login' | 'register'>('login')
const form = ref({
  username: '',
  phone: '',
  password: '',
  confirmPassword: ''
})
const showPwd = ref(false)

function submit() {
  if (!form.value.username) return showToast('请输入用户名')
  if (!form.value.password) return showToast('请输入密码')
  if (mode.value === 'register') {
    if (!form.value.phone) return showToast('请输入手机号')
    if (form.value.password !== form.value.confirmPassword)
      return showToast('两次密码不一致')
  }
  userStore.login(form.value.username)
  showSuccessToast({ message: mode.value === 'login' ? '登录成功' : '注册成功', position: 'top' })
  setTimeout(() => {
    router.replace('/home')
  }, 800)
}
</script>

<template>
  <div class="fc-page login-page">
    <div class="login-header">
      <div class="logo">
        <span class="logo-emoji">🛒</span>
        <div class="logo-text">FlashCommerce</div>
      </div>
      <div class="slogan">轻松购物 · 尽情秒杀</div>
    </div>

    <van-tabs v-model:active="mode" sticky shrink :border="false" title-active-color="#ff5a4c">
      <van-tab title="登录" name="login" />
      <van-tab title="注册" name="register" />
    </van-tabs>

    <div class="form-area">
      <van-cell-group inset>
        <van-field
          v-model="form.username"
          label="用户名"
          placeholder="请输入用户名"
          :rules="[{ required: true, message: '请输入用户名' }]"
        />
        <van-field
          v-if="mode === 'register'"
          v-model="form.phone"
          label="手机号"
          placeholder="请输入手机号"
        />
        <van-field
          v-model="form.password"
          label="密码"
          :type="showPwd ? 'text' : 'password'"
          placeholder="请输入密码（BCrypt 强加密存储）"
          :right-icon="showPwd ? 'eye' : 'closed-eye'"
          @click-right-icon="showPwd = !showPwd"
        />
        <van-field
          v-if="mode === 'register'"
          v-model="form.confirmPassword"
          label="确认密码"
          :type="showPwd ? 'text' : 'password'"
          placeholder="请再次输入密码"
        />
      </van-cell-group>

      <div class="submit-btn">
        <van-button round block type="primary" @click="submit">
          {{ mode === 'login' ? '登 录' : '注 册' }}
        </van-button>
      </div>

      <div class="tip-area">
        <div class="tip-text">登录即代表同意</div>
        <a class="link">《用户协议》</a>
        <a class="link">《隐私政策》</a>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-header {
  padding: 60px 40px 30px;
  text-align: center;
  .logo-emoji { font-size: 48px; display: block; margin-bottom: 8px; }
  .logo-text { font-size: 22px; font-weight: 700; color: #323233; }
  .slogan { color: #999; font-size: 12px; margin-top: 6px; }
}
.form-area { padding-top: 12px; }
.submit-btn { padding: 24px 20px 10px; }
.tip-area {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  padding: 10px 20px;
  font-size: 12px;
  color: #999;
  .link { color: #ff5a4c; text-decoration: none; }
}
</style>
