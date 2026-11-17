import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { initialCart, products, type CartItem } from '@/mock/data'

export const useCartStore = defineStore('cart', () => {
  const items = ref<CartItem[]>([...initialCart])

  const totalCount = computed(() =>
    items.value.reduce((sum, it) => sum + (it.checked ? it.quantity : 0), 0)
  )

  const totalPrice = computed(() =>
    items.value
      .filter((it) => it.checked)
      .reduce((sum, it) => {
        const p = products.find((pr) => pr.id === it.productId)
        return sum + (p ? p.price * it.quantity : 0)
      }, 0)
  )

  const allChecked = computed(
    () => items.value.length > 0 && items.value.every((it) => it.checked)
  )

  function add(productId: number, quantity = 1) {
    const exist = items.value.find((it) => it.productId === productId)
    if (exist) {
      exist.quantity += quantity
    } else {
      items.value.push({ productId, quantity, checked: true })
    }
  }

  function updateQuantity(productId: number, quantity: number) {
    const it = items.value.find((x) => x.productId === productId)
    if (it) {
      it.quantity = Math.max(1, quantity)
    }
  }

  function toggleCheck(productId: number) {
    const it = items.value.find((x) => x.productId === productId)
    if (it) it.checked = !it.checked
  }

  function toggleAll(checked: boolean) {
    items.value.forEach((it) => (it.checked = checked))
  }

  function remove(productId: number) {
    items.value = items.value.filter((it) => it.productId !== productId)
  }

  function clearChecked() {
    items.value = items.value.filter((it) => !it.checked)
  }

  return {
    items,
    totalCount,
    totalPrice,
    allChecked,
    add,
    updateQuantity,
    toggleCheck,
    toggleAll,
    remove,
    clearChecked
  }
})
