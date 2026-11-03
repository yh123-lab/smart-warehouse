-- ========================================
-- 智慧仓储管理系统 数据库初始化脚本
-- ========================================
CREATE DATABASE IF NOT EXISTS smart_warehouse DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE smart_warehouse;

-- 商品表
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku_code VARCHAR(50) UNIQUE NOT NULL COMMENT 'SKU编码',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    category_id BIGINT DEFAULT 0 COMMENT '分类ID',
    unit VARCHAR(20) DEFAULT '件' COMMENT '计量单位',
    weight DECIMAL(10,3) DEFAULT 0 COMMENT '单重(kg)',
    volume DECIMAL(10,3) DEFAULT 0 COMMENT '体积(m³)',
    min_stock INT DEFAULT 0 COMMENT '最低库存阈值',
    max_stock INT DEFAULT 0 COMMENT '最高库存阈值',
    barcode VARCHAR(50) DEFAULT '' COMMENT '条形码',
    description VARCHAR(500) DEFAULT '' COMMENT '商品描述',
    status TINYINT DEFAULT 1 COMMENT '状态 0下架 1上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 库存表
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku_code VARCHAR(50) NOT NULL COMMENT 'SKU编码',
    warehouse_id BIGINT DEFAULT 1 COMMENT '仓库ID',
    shelf_code VARCHAR(50) DEFAULT '' COMMENT '货架编码',
    quantity INT DEFAULT 0 COMMENT '实际库存',
    locked_quantity INT DEFAULT 0 COMMENT '锁定库存',
    last_check_time DATETIME DEFAULT NULL COMMENT '最后盘点时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

-- 出入库单表
DROP TABLE IF EXISTS stock_order;
CREATE TABLE stock_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(100) UNIQUE NOT NULL COMMENT '单据编号',
    order_type VARCHAR(10) NOT NULL COMMENT 'IN-入库 OUT-出库',
    sku_code VARCHAR(50) NOT NULL COMMENT 'SKU编码',
    quantity INT NOT NULL COMMENT '数量',
    status VARCHAR(20) DEFAULT 'COMPLETED' COMMENT '状态',
    operator VARCHAR(50) DEFAULT 'system' COMMENT '操作人',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出入库单表';

-- 预警记录表
DROP TABLE IF EXISTS alert_record;
CREATE TABLE alert_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku_code VARCHAR(50) NOT NULL COMMENT 'SKU编码',
    alert_type VARCHAR(30) DEFAULT 'LOW_STOCK' COMMENT '预警类型',
    alert_level VARCHAR(20) DEFAULT 'WARNING' COMMENT '预警级别',
    current_stock INT DEFAULT 0 COMMENT '当前库存',
    threshold INT DEFAULT 0 COMMENT '阈值',
    handled TINYINT DEFAULT 0 COMMENT '是否处理 0未处理 1已处理',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警记录表';

-- ============ 初始化测试数据 ============

INSERT INTO product (sku_code, product_name, unit, weight, min_stock, max_stock, barcode, description, status) VALUES
('SKU0001', '无线蓝牙耳机', '副', 0.150, 20, 500, '6901234567890', '主动降噪蓝牙耳机', 1),
('SKU0002', '机械键盘', '个', 0.850, 15, 300, '6901234567891', 'RGB背光机械键盘', 1),
('SKU0003', '智能手环', '只', 0.045, 30, 800, '6901234567892', '心率监测智能手环', 1),
('SKU0004', '平板电脑', '台', 0.520, 10, 200, '6901234567893', '10寸高清平板', 1),
('SKU0005', '便携充电宝', '个', 0.280, 50, 1000, '6901234567894', '20000mAh大容量充电宝', 1);

INSERT INTO inventory (sku_code, warehouse_id, shelf_code, quantity, locked_quantity, version) VALUES
('SKU0001', 1, 'A-01-01', 120, 0, 0),
('SKU0002', 1, 'A-01-02', 8, 0, 0),
('SKU0003', 1, 'B-02-01', 200, 5, 0),
('SKU0004', 1, 'B-02-02', 5, 0, 0),
('SKU0005', 1, 'C-03-01', 450, 20, 0);
