-- 1. 用户表
CREATE TABLE `tb_user` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `username` VARCHAR(64) NOT NULL COMMENT '用户名',
                           `password` VARCHAR(128) NOT NULL COMMENT 'BCrypt加密密码',
                           `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
                           `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
                           `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uk_username` (`username`),
                           UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 商品主表
CREATE TABLE `tb_product` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
                              `title` VARCHAR(128) NOT NULL COMMENT '商品标题',
                              `category_id` BIGINT NOT NULL COMMENT '分类ID',
                              `price` DECIMAL(10,2) NOT NULL COMMENT '销售原价',
                              `pic_url` VARCHAR(255) DEFAULT '' COMMENT '商品主图',
                              `detail` TEXT COMMENT '商品详情',
                              `status` TINYINT NOT NULL DEFAULT 1 COMMENT '上架状态: 0-下架, 1-上架',
                              `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              KEY `idx_category_status` (`category_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 3. 商品库存表 (拆分高频写字段，降低行级锁对商品主表读的影响)
CREATE TABLE `tb_product_stock` (
                                    `product_id` BIGINT NOT NULL COMMENT '商品ID',
                                    `available_stock` INT NOT NULL DEFAULT 0 COMMENT '可用可售库存',
                                    `locked_stock` INT NOT NULL DEFAULT 0 COMMENT '下单锁定的库存',
                                    `version` INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
                                    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品库存表';

-- 4. 订单主表
CREATE TABLE `tb_order` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                            `order_no` VARCHAR(64) NOT NULL COMMENT '分布式业务唯一订单号',
                            `user_id` BIGINT NOT NULL COMMENT '买家用户ID',
                            `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
                            `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
                            `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-待支付, 1-已支付, 2-已取消, 3-已完成',
                            `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
                            `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_order_no` (`order_no`),
                            KEY `idx_user_status` (`user_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- 5. 订单明细表
CREATE TABLE `tb_order_item` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `order_no` VARCHAR(64) NOT NULL COMMENT '关联业务订单号',
                                 `product_id` BIGINT NOT NULL COMMENT '商品ID',
                                 `product_name` VARCHAR(128) NOT NULL COMMENT '快照商品名',
                                 `price` DECIMAL(10,2) NOT NULL COMMENT '快照单价',
                                 `quantity` INT NOT NULL COMMENT '购买数量',
                                 `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 6. 秒杀活动与商品表
CREATE TABLE `tb_seckill_activity` (
                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                       `product_id` BIGINT NOT NULL COMMENT '关联商品ID',
                                       `seckill_price` DECIMAL(10,2) NOT NULL COMMENT '秒杀特价',
                                       `seckill_stock` INT NOT NULL COMMENT '秒杀专属库存',
                                       `start_time` DATETIME NOT NULL COMMENT '开始时间',
                                       `end_time` DATETIME NOT NULL COMMENT '结束时间',
                                       `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-未上线, 1-上线, 2-已结束',
                                       PRIMARY KEY (`id`),
                                       KEY `idx_product_time` (`product_id`, `start_time`, `end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀活动表';