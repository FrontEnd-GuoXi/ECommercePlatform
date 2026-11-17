# ECommercePlatform 项目总结

> 基于实际源码与配置文件整理，信息边界以本地仓库文件为准。

---

## 一、项目概述

**ECommercePlatform** 是一个面向 C 端用户的**移动端电商平台**（项目别名 FlashCommerce），覆盖用户注册登录、商品浏览、购物车、订单管理与**限时秒杀**等核心电商场景。

项目采用**前后端分离**架构：后端为单体式 Spring Boot 应用，前端为基于 Vue 3 + Vant 的移动端 H5 应用。

| 维度 | 说明 |
|------|------|
| 项目名称 | ECommercePlatform |
| GroupId | `org.guoxi` |
| 版本 | `0.0.1-SNAPSHOT` |
| 前端名称 | `FlashCommerce`（路由 title 中可见） |
| 架构风格 | 前后端分离 · 单体后端 |
| 目标平台 | 移动端 H5 |

---

## 二、技术栈

### 2.1 后端技术栈

| 分类 | 技术 | 版本 | 用途 |
|------|------|------|------|
| **语言** | Java | 17 | - |
| **框架** | Spring Boot | 4.1.2 | 应用基础框架 |
| **ORM** | MyBatis-Plus | 3.5.9（`mybatis-plus-spring-boot3-starter`） | 数据访问层 |
| **数据库** | MySQL | - | 关系型数据存储 |
| **数据库驱动** | mysql-connector-j | - | MySQL JDBC 驱动 |
| **参数校验** | Jakarta Validation | - | 请求参数合法性校验 |
| **工具库** | Lombok | - | 简化实体类代码 |
| **加密** | Spring Security Crypto（BCrypt） | - | 用户密码加密存储 |
| **认证** | JJWT | 0.12.6 | JWT Token 生成与解析 |
| **构建工具** | Maven | - | 项目构建与依赖管理 |

### 2.2 前端技术栈

| 分类 | 技术 | 版本 | 用途 |
|------|------|------|------|
| **语言** | TypeScript | ~6.0.3 | 类型安全开发 |
| **框架** | Vue | ^3.5.44 | 前端核心框架 |
| **路由** | Vue Router | ^5.5.1 | SPA 路由管理 |
| **状态管理** | Pinia | ^4.1.0 | 全局状态（用户、购物车） |
| **UI 组件库** | Vant | ^4.10.2 | 移动端 UI 组件 |
| **CSS 预处理器** | Sass | ^1.108.2 | 样式编写 |
| **构建工具** | Vite | ^8.3.6 | 开发服务器 & 打包 |
| **自动导入** | unplugin-vue-components | ^32.1.0 | Vant 组件按需导入 |
| **类型检查** | vue-tsc | ^3.3.14 | Vue 模板类型检查 |

---

## 三、项目结构

### 3.1 后端目录结构

```
ECommercePlatform/
├── pom.xml                                    # Maven 父工程配置
├── doc/
│   ├── table.sql                              # 数据库 DDL 脚本
│   └── 需求文档.txt / .doc                    # 需求文档
└── src/main/java/org/guoxi/ecommerceplatform/
    ├── ECommercePlatformApplication.java      # Spring Boot 启动类
    ├── common/
    │   └── Result.java                        # 统一响应封装（待完善）
    ├── config/
    │   └── MyBatisPlusMetaObjectHandler.java  # MP 自动填充处理器
    ├── entity/
    │   ├── User.java                          # 用户实体
    │   ├── Product.java                       # 商品实体
    │   ├── ProductStock.java                  # 库存实体（含乐观锁）
    │   ├── Order.java                         # 订单主实体
    │   ├── OrderItem.java                     # 订单明细实体
    │   └── SeckillActivity.java               # 秒杀活动实体
    ├── enums/
    │   └── OrderStatus.java                   # 订单状态枚举
    └── resources/
        └── application.properties              # 应用配置
```

### 3.2 前端目录结构

```
frontend/
├── package.json                               # 依赖声明
├── index.html                                 # HTML 入口
├── dist/                                      # 构建产物（已编译）
└── src/
    ├── main.ts                                # 应用入口
    ├── App.vue                                # 根组件
    ├── router/index.ts                        # 路由配置
    ├── mock/data.ts                           # Mock 数据
    ├── components/
    │   ├── MainTabBar.vue                     # 底部 Tab 导航栏
    │   └── PageNavBar.vue                     # 页面顶部导航栏
    ├── stores/
    │   ├── user.ts                            # 用户状态（Pinia）
    │   └── cart.ts                            # 购物车状态（Pinia）
    └── views/
        ├── Home.vue                           # 首页
        ├── Category.vue                       # 商品分类
        ├── ProductDetail.vue                  # 商品详情
        ├── Cart.vue                           # 购物车
        ├── Mine.vue                           # 我的页面
        ├── Login.vue                          # 登录 / 注册
        ├── OrderConfirm.vue                   # 确认订单
        ├── OrderList.vue                      # 订单列表
        ├── Seckill.vue                        # 限时秒杀
        └── SeckillResult.vue                  # 秒杀结果
```

---

## 四、数据库设计

### 4.1 数据库信息

| 项 | 值 |
|----|----|
| 数据库名 | `e_commerce_system` |
| 字符集 | `utf8mb4` |
| 存储引擎 | InnoDB |
| 表数量 | 6 |

### 4.2 表结构总览

| 表名 | 说明 | 关键字段 |
|------|------|---------|
| `tb_user` | 用户表 | `username`（唯一）、`phone`（唯一）、`password`（BCrypt） |
| `tb_product` | 商品表 | `category_id`、`price`、`status`（上架状态） |
| `tb_product_stock` | 商品库存表 | `available_stock`、`locked_stock`、`version`（乐观锁） |
| `tb_order` | 订单主表 | `order_no`（业务唯一）、`total_amount`、`pay_amount`、`status`（4 种状态） |
| `tb_order_item` | 订单明细表 | `order_no`、`product_name`/`price`（快照字段） |
| `tb_seckill_activity` | 秒杀活动表 | `seckill_price`、`seckill_stock`、`start_time`、`end_time` |

### 4.3 设计亮点

- **库存表拆分**：`tb_product_stock` 独立于商品主表，降低高频写操作对商品查询的行级锁影响。
- **乐观锁**：`tb_product_stock.version` 字段配合 MyBatis-Plus `@Version` 注解实现并发更新保护。
- **订单快照**：`tb_order_item` 冗余存储商品名和单价，避免历史订单受商品信息变更影响。
- **分布式订单号**：`tb_order.order_no` 为业务唯一标识，区别于自增主键。

---

## 五、核心模块说明

### 5.1 用户认证模块

| 组件 | 技术方案 |
|------|---------|
| 密码存储 | BCrypt（`spring-security-crypto`） |
| Token 方案 | JWT（JJWT 0.12.6） |
| Token 有效期 | 24 小时（`86400000` ms） |
| 参数校验 | Jakarta Validation |

### 5.2 数据访问层

基于 MyBatis-Plus，已实现的配置包括：

- **自动填充**：`MyBatisPlusMetaObjectHandler` 统一处理 `created_at` / `updated_at`。
- **驼峰映射**：全局开启 `map-underscore-to-camel-case`。
- **乐观锁**：`ProductStock.version` 配合 `@Version` 注解。
- **枚举映射**：`OrderStatus` 通过 `@EnumValue` 将枚举值持久化为数据库整数。

### 5.3 前端路由

采用 **Hash 模式**（`createWebHashHistory`），共定义 **10 个页面**：

| 路由 | 页面 | 说明 |
|------|------|------|
| `/home` | 首页 | 默认入口 |
| `/category` | 分类 | 商品分类浏览 |
| `/seckill` | 限时秒杀 | 秒杀活动列表 |
| `/seckill/result/:orderId` | 秒杀结果 | 秒杀下单结果展示 |
| `/product/:id` | 商品详情 | 动态路由 |
| `/cart` | 购物车 | - |
| `/mine` | 我的 | 用户中心 |
| `/login` | 登录 / 注册 | - |
| `/order/confirm` | 确认订单 | - |
| `/order/list` | 订单列表 | - |

### 5.4 前端状态管理

使用 **Pinia** 管理两个核心 Store：

- `user.ts` — 用户登录态、Token
- `cart.ts` — 购物车商品列表

---

## 六、配置说明

### 6.1 后端 `application.properties`

```properties
# 数据源
spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce_system
spring.datasource.username=root
spring.datasource.password=123456

# MyBatis-Plus
mybatis-plus.configuration.map-underscore-to-camel-case=true

# JWT
jwt.secret=<Base64编码的密钥，建议256位>
jwt.expiration=86400000
```

### 6.2 后端启动

```bash
mvn spring-boot:run
```

### 6.3 前端启动

```bash
cd frontend
npm run dev     # 开发模式
npm run build   # 生产打包
```

---

## 七、当前进度与待完善项

> 以下基于源码实际情况整理，未做推测。

### ✅ 已完成

| 模块 | 内容 |
|------|------|
| 数据库 | 6 张表 DDL 脚本就绪（`doc/table.sql`） |
| 实体层 | 6 个实体类 + 1 个枚举类 + MP 自动填充 |
| 前端页面 | 10 个视图组件已创建，包含已编译产物 |
| 前端路由 | 完整的路由配置与页面跳转 |
| 状态管理 | 用户 + 购物车两个 Pinia Store |
| UI 组件 | Vant 移动端组件库集成 |

### ⚠️ 待完善 / 尚未实现

| 模块 | 说明 |
|------|------|
| Controller 层 | 未发现 REST Controller 代码 |
| Service 层 | 未发现业务 Service 实现 |
| Mapper 层 | 未发现 MyBatis-Plus Mapper 接口 |
| 统一响应 | `Result.java` 当前为空类 |
| JWT 工具 | 未发现 JWT 生成/解析工具类 |
| 密码加密 | 依赖已引入，但未发现使用代码 |
| 前端 API 调用 | 路由和 Mock 数据已有，但未发现真实 API 请求逻辑 |
| 秒杀核心逻辑 | 秒杀活动表结构已定义，但业务实现未发现 |

---

## 八、架构示意

```
┌─────────────────────────────────────────────────┐
│                   前端 (H5)                      │
│  Vue 3 + Vant + Pinia + Vue Router + TypeScript │
└───────────────────────┬─────────────────────────┘
                        │ HTTP / JSON
┌───────────────────────▼─────────────────────────┐
│               后端 (Spring Boot)                 │
│  ┌─────────┐ ┌─────────┐ ┌───────────────────┐ │
│  │ MyBatis │ │  JWT    │ │ BCrypt             │ │
│  │  -Plus  │ │ (JJWT)  │ │ (Spring Security)  │ │
│  └────┬────┘ └─────────┘ └───────────────────┘ │
└───────┼─────────────────────────────────────────┘
        │ JDBC
┌───────▼─────────────────────────────────────────┐
│              MySQL (e_commerce_system)            │
│  tb_user · tb_product · tb_product_stock          │
│  tb_order · tb_order_item · tb_seckill_activity   │
└─────────────────────────────────────────────────┘
```

---

## 九、总结

ECommercePlatform 是一个**设计清晰、分层合理**的电商平台项目，具备以下特点：

1. **技术选型现代**：后端采用 Spring Boot 4 + Java 17 + MyBatis-Plus，前端采用 Vue 3 + Vite + TypeScript，均为当前主流技术。
2. **数据库设计规范**：库存表拆分、乐观锁、订单快照等设计体现了对电商场景并发一致性的考虑。
3. **秒杀场景完备**：专门的秒杀活动表设计支持限时秒杀业务。
4. **前端框架已就绪**：移动端 H5 架构完整，10 个页面 + 状态管理 + 路由均可直接运行。

项目目前处于**骨架搭建完成、业务代码待补充**阶段，实体层、数据库、前端视图已就绪，核心的 Controller / Service / Mapper 业务层与后端 API 调用逻辑尚未实现，是下一步开发的重点。
