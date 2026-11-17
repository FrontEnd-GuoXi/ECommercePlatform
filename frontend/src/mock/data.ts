// 模拟数据 - 商品
export interface Product {
  id: number
  title: string
  categoryId: number
  price: number
  originalPrice?: number
  picUrl: string
  detail: string
  stock: number
  sales: number
  pics?: string[]
  tags?: string[]
}

export interface Category {
  id: number
  name: string
  icon: string
  children?: { id: number; name: string }[]
}

export interface SeckillActivity {
  id: number
  productId: number
  seckillPrice: number
  originalPrice: number
  stock: number
  sold: number
  startTime: string
  endTime: string
  product: Product
  status: 'upcoming' | 'ongoing' | 'ended'
}

export interface CartItem {
  productId: number
  quantity: number
  checked: boolean
}

export interface OrderItem {
  productId: number
  productName: string
  price: number
  quantity: number
  picUrl: string
}

export interface Order {
  id: number
  orderNo: string
  totalAmount: number
  payAmount: number
  status: 0 | 1 | 2 | 3 // 0待支付 1已支付 2已取消 3已完成
  payTime?: string
  createdAt: string
  items: OrderItem[]
}

const img = (keyword: string) =>
  `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${encodeURIComponent(
    `${keyword} product photography, white background, professional e-commerce photo`
  )}&image_size=square`

export const categories: Category[] = [
  {
    id: 1,
    name: '数码电器',
    icon: '📱',
    children: [
      { id: 101, name: '手机通讯' },
      { id: 102, name: '电脑办公' },
      { id: 103, name: '智能家居' },
      { id: 104, name: '数码配件' }
    ]
  },
  {
    id: 2,
    name: '服饰鞋包',
    icon: '👕',
    children: [
      { id: 201, name: '男装' },
      { id: 202, name: '女装' },
      { id: 203, name: '运动鞋包' }
    ]
  },
  {
    id: 3,
    name: '美妆个护',
    icon: '💄',
    children: [
      { id: 301, name: '面部护肤' },
      { id: 302, name: '彩妆' },
      { id: 303, name: '香水' }
    ]
  },
  {
    id: 4,
    name: '食品生鲜',
    icon: '🍎',
    children: [
      { id: 401, name: '新鲜水果' },
      { id: 402, name: '休闲零食' },
      { id: 403, name: '进口食品' }
    ]
  },
  {
    id: 5,
    name: '家居生活',
    icon: '🏠',
    children: [
      { id: 501, name: '床上用品' },
      { id: 502, name: '收纳整理' }
    ]
  }
]

export const products: Product[] = [
  {
    id: 1001,
    title: 'iPhone 15 Pro Max 256GB 钛原色',
    categoryId: 101,
    price: 9999,
    originalPrice: 10999,
    picUrl: img('iPhone 15 Pro Max'),
    detail:
      '搭载 A17 Pro 芯片，钛金属设计，4800万像素主摄，USB-C 接口。全新 Action 按钮，专业级相机控制。',
    stock: 128,
    sales: 3521,
    tags: ['新品', '热销', '免息'],
    pics: [img('iPhone 15 Pro Max front'), img('iPhone 15 Pro Max side'), img('iPhone 15 Pro Max camera')]
  },
  {
    id: 1002,
    title: '华为 Mate 60 Pro 12+256GB 雅川青',
    categoryId: 101,
    price: 6999,
    originalPrice: 7499,
    picUrl: img('Huawei Mate 60 Pro'),
    detail: '灵犀通信，卫星通话，5000万像素超感知影像，88W 有线超级快充。',
    stock: 256,
    sales: 8912,
    tags: ['爆款', '现货']
  },
  {
    id: 1003,
    title: 'MacBook Air M3 13英寸 8+256GB',
    categoryId: 102,
    price: 7999,
    originalPrice: 8799,
    picUrl: img('MacBook Air M3'),
    detail: 'M3 芯片，13.6英寸 Liquid Retina 显示屏，18小时超长续航，仅1.24kg。',
    stock: 64,
    sales: 1204,
    tags: ['新品', '教育优惠']
  },
  {
    id: 1004,
    title: '小米净化器 Pro H 除甲醛除菌',
    categoryId: 103,
    price: 1299,
    originalPrice: 1599,
    picUrl: img('Xiaomi air purifier'),
    detail: '600CADR 大CADR，OLED 屏幕显示，智能联动，静音模式。',
    stock: 512,
    sales: 5621,
    tags: ['智能', '除菌']
  },
  {
    id: 1005,
    title: 'Nike 空军一号 运动鞋 男女同款',
    categoryId: 203,
    price: 699,
    originalPrice: 899,
    picUrl: img('Nike Air Force 1 sneakers'),
    detail: '经典百搭款，全白皮革鞋面，气垫缓震，适合日常通勤与运动。',
    stock: 1024,
    sales: 12045,
    tags: ['百搭', '经典']
  },
  {
    id: 1006,
    title: '兰蔻小黑瓶精华肌底液 50ml',
    categoryId: 301,
    price: 1080,
    originalPrice: 1280,
    picUrl: img('Lancome serum bottle'),
    detail: '二裂酵母精华，修复肌肤屏障，提亮肤色，减少细纹。',
    stock: 300,
    sales: 4521,
    tags: ['明星单品', '进口']
  },
  {
    id: 1007,
    title: '阿克苏冰糖心苹果 5斤装 新鲜水果',
    categoryId: 401,
    price: 39.9,
    originalPrice: 68,
    picUrl: img('red apples'),
    detail: '新疆阿克苏冰糖心苹果，果心透明，甜度16+，脆嫩多汁。',
    stock: 2048,
    sales: 23421,
    tags: ['产地直发', '限时特惠']
  },
  {
    id: 1008,
    title: '戴森 V15 Detect 无线吸尘器',
    categoryId: 103,
    price: 4990,
    originalPrice: 5490,
    picUrl: img('Dyson V15 vacuum'),
    detail: '激光探测微尘，240AW 强劲吸力，60分钟续航，LCD 实时显示。',
    stock: 32,
    sales: 892,
    tags: ['高端', '激光探测']
  },
  {
    id: 1009,
    title: '优衣库 春季纯棉T恤 男女同款',
    categoryId: 201,
    price: 79,
    originalPrice: 99,
    picUrl: img('white cotton t-shirt'),
    detail: '100%纯棉，舒适亲肤，多色可选，版型简约百搭。',
    stock: 4096,
    sales: 34521,
    tags: ['基础款', '多色可选']
  },
  {
    id: 1010,
    title: '三只松鼠 每日坚果 30包 750g',
    categoryId: 402,
    price: 89.9,
    originalPrice: 128,
    picUrl: img('mixed nuts package'),
    detail: '7种坚果果干科学配比，独立小包装，每日一包补充能量。',
    stock: 1536,
    sales: 18923,
    tags: ['营养早餐', '独立包装']
  }
]

// 秒杀活动
const now = Date.now()
const hour = 3600 * 1000
export const seckillActivities: SeckillActivity[] = [
  {
    id: 1,
    productId: 1007,
    seckillPrice: 19.9,
    originalPrice: 39.9,
    stock: 100,
    sold: 78,
    startTime: new Date(now - hour).toISOString(),
    endTime: new Date(now + hour * 5).toISOString(),
    product: products[6],
    status: 'ongoing'
  },
  {
    id: 2,
    productId: 1005,
    seckillPrice: 499,
    originalPrice: 699,
    stock: 50,
    sold: 0,
    startTime: new Date(now + hour * 2).toISOString(),
    endTime: new Date(now + hour * 8).toISOString(),
    product: products[4],
    status: 'upcoming'
  },
  {
    id: 3,
    productId: 1004,
    seckillPrice: 899,
    originalPrice: 1299,
    stock: 30,
    sold: 30,
    startTime: new Date(now - hour * 10).toISOString(),
    endTime: new Date(now - hour * 2).toISOString(),
    product: products[3],
    status: 'ended'
  },
  {
    id: 4,
    productId: 1010,
    seckillPrice: 49.9,
    originalPrice: 89.9,
    stock: 200,
    sold: 132,
    startTime: new Date(now - hour * 0.5).toISOString(),
    endTime: new Date(now + hour * 6).toISOString(),
    product: products[9],
    status: 'ongoing'
  },
  {
    id: 5,
    productId: 1001,
    seckillPrice: 8999,
    originalPrice: 9999,
    stock: 20,
    sold: 0,
    startTime: new Date(now + hour * 4).toISOString(),
    endTime: new Date(now + hour * 12).toISOString(),
    product: products[0],
    status: 'upcoming'
  }
]

export const banners = [
  {
    id: 1,
    title: '新用户专享 立减50元',
    subtitle: '全场满99可用',
    img: `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${encodeURIComponent(
      'e-commerce promotional banner with red theme, big sale, shopping, festival'
    )}&image_size=landscape_16_9`
  },
  {
    id: 2,
    title: '限时秒杀 19.9元起',
    subtitle: '每日10点开抢',
    img: `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${encodeURIComponent(
      'flash sale banner, lightning deal, countdown, red background'
    )}&image_size=landscape_16_9`
  },
  {
    id: 3,
    title: '数码盛典 至高24期免息',
    subtitle: '全场大牌齐聚',
    img: `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${encodeURIComponent(
      'electronics sale banner, smartphones laptop gadgets, blue gradient'
    )}&image_size=landscape_16_9`
  }
]

export const initialCart: CartItem[] = [
  { productId: 1001, quantity: 1, checked: true },
  { productId: 1005, quantity: 2, checked: true },
  { productId: 1010, quantity: 1, checked: false }
]

export const initialOrders: Order[] = [
  {
    id: 10001,
    orderNo: 'FC20241114000001',
    totalAmount: 2198,
    payAmount: 2198,
    status: 1,
    payTime: '2024-11-13 10:32:15',
    createdAt: '2024-11-13 10:28:00',
    items: [
      { productId: 1005, productName: 'Nike 空军一号', price: 699, quantity: 2, picUrl: products[4].picUrl },
      { productId: 1009, productName: '优衣库 春季纯棉T恤', price: 79, quantity: 1, picUrl: products[8].picUrl }
    ]
  },
  {
    id: 10002,
    orderNo: 'FC20241114000002',
    totalAmount: 1080,
    payAmount: 1080,
    status: 0,
    createdAt: '2024-11-14 09:15:00',
    items: [
      { productId: 1006, productName: '兰蔻小黑瓶精华肌底液', price: 1080, quantity: 1, picUrl: products[5].picUrl }
    ]
  },
  {
    id: 10003,
    orderNo: 'FC20241112000003',
    totalAmount: 19.9,
    payAmount: 19.9,
    status: 3,
    payTime: '2024-11-12 14:20:00',
    createdAt: '2024-11-12 14:10:00',
    items: [
      { productId: 1007, productName: '阿克苏冰糖心苹果 5斤装', price: 19.9, quantity: 1, picUrl: products[6].picUrl }
    ]
  }
]
