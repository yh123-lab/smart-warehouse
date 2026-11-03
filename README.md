# 智慧仓储管理系统 (Smart Warehouse)

基于 Spring Boot + MyBatis-Plus 的智慧仓储管理系统，涵盖商品管理、库存管理、出入库、低库存预警等核心功能。

## 技术栈

- **后端**: Spring Boot 2.7.18、Spring Security、MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0
- **工具**: Lombok、Hibernate Validator

## 模块结构

```
smart-warehouse/
├── pom.xml
├── sql/init.sql                          # 数据库初始化脚本
└── src/main/
    ├── java/com/smartwarehouse/
    │   ├── SmartWarehouseApplication.java # 启动类
    │   ├── common/Result.java             # 统一响应
    │   ├── config/                        # 配置（MyBatis、Security）
    │   ├── entity/                       # 实体类
    │   │   ├── Product.java              # 商品
    │   │   ├── Inventory.java            # 库存
    │   │   ├── StockOrder.java           # 出入库单
    │   │   └── AlertRecord.java          # 预警记录
    │   ├── mapper/                       # 数据访问层
    │   ├── service/                      # 业务逻辑层
    │   └── controller/                   # 接口控制层
    └── resources/
        └── application.yml
```

## 快速启动

### 1. 环境准备
- JDK 11+
- Maven 3.6+
- MySQL 8.0

### 2. 初始化数据库
```bash
mysql -u root -p < sql/init.sql
```

### 3. 修改配置
编辑 `src/main/resources/application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smart_warehouse
    username: your_username
    password: your_password
```

### 4. 启动项目

**方式一：IDEA 启动**
- 用 IDEA 打开项目，等待 Maven 依赖下载完成
- 找到 `SmartWarehouseApplication.java`，右键运行

**方式二：命令行启动**
```bash
mvn clean install -DskipTests
java -jar target/smart-warehouse-1.0.0.jar
```

启动成功后访问：http://localhost:8080

## API 接口

### 商品管理
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/product` | 新增商品 |
| PUT | `/api/product/{id}` | 修改商品 |
| DELETE | `/api/product/{id}` | 删除商品 |
| GET | `/api/product/{id}` | 查询详情 |
| GET | `/api/product/page?keyword=` | 分页搜索 |

### 库存管理
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/inventory/available/{skuCode}` | 查询可用库存 |
| POST | `/api/inventory/lock?skuCode=&quantity=` | 锁定库存 |
| POST | `/api/inventory/unlock?skuCode=&quantity=` | 释放库存 |

### 出入库管理
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/stock/inbound` | 创建入库单 |
| POST | `/api/stock/outbound` | 创建出库单 |
| GET | `/api/stock/page?orderType=IN` | 分页查询 |

### 预警管理
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/alert/scan` | 手动触发低库存扫描 |
| GET | `/api/alert/unhandled` | 查询未处理预警 |

## 测试示例

```bash
# 新增商品
curl -X POST http://localhost:8080/api/product \
  -H "Content-Type: application/json" \
  -d '{"skuCode":"SKU0006","productName":"测试商品","minStock":10,"status":1}'

# 查询商品列表
curl "http://localhost:8080/api/product/page?pageNum=1&pageSize=10"

# 创建入库单
curl -X POST http://localhost:8080/api/stock/inbound \
  -H "Content-Type: application/json" \
  -d '{"skuCode":"SKU0001","quantity":50,"operator":"admin"}'

# 创建出库单
curl -X POST http://localhost:8080/api/stock/outbound \
  -H "Content-Type: application/json" \
  -d '{"skuCode":"SKU0001","quantity":20,"operator":"admin"}'

# 触发低库存扫描
curl -X POST http://localhost:8080/api/alert/scan
```

## 核心设计说明

### 库存并发控制
- 采用**锁定库存**机制防止超卖：下单时先锁定，支付后扣减实际库存
- 使用 MyBatis-Plus 乐观锁 `@Version` 防止并发更新丢失

### 低库存预警
- 定时任务每 30 分钟扫描一次（可通过 `warehouse.alert.scan-interval` 配置）
- 预警级别根据当前库存/阈值比例动态计算：
  - `CRITICAL`（紧急）：比例 ≤ 30%
  - `WARNING`（警告）：比例 ≤ 60%
  - `NOTICE`（通知）：其他

### 可扩展方向
- 接入 Redis 缓存热点库存数据
- 接入 Elasticsearch 实现全文检索
- 集成 RabbitMQ 实现异步消息通知
- 完善 RBAC 权限体系（用户/角色/数据权限）
- 添加报表统计（库存周转率、ABC分类）

## 许可
MIT License
