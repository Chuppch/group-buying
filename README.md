# 🚀 Group-Buying 团购营销系统

[![Java Version](https://img.shields.io/badge/Java-1.8+-blue.svg)](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.x-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-orange.svg)](https://maven.apache.org/)

🎉 一个基于 **DDD 架构设计** 的团购营销系统，提供灵活的商品营销活动和团购交易功能，支持多种营销策略和优惠计算。

---

## ✨ 主要功能

- 🛍️ **商品营销活动管理** - 支持创建和管理多种类型的营销活动
- 👥 **团购订单处理** - 实现团购订单的创建、锁定和支付流程
- 💰 **优惠计算引擎** - 灵活配置多种优惠策略和折扣计算规则
- 📊 **活动数据统计** - 提供活动参与度和销售数据统计
- 🔒 **订单状态管理** - 完整的订单生命周期管理
- 🎯 **用户参与限制** - 支持设置用户参与活动的次数限制
- 📱 **多渠道支持** - 支持多来源和多渠道的订单处理

---

## ⚙️ 安装指南

### 🧩 环境要求

- ☕ JDK 1.8+
- 🛠️ Maven 3.8+
- 🐬 MySQL 5.7+
- 🔁 Redis 6.0+

### 🛠️ 安装步骤

1. 📥 克隆项目
```bash
git clone https://github.com/your-username/group-buying.git
cd group-buying
```

2. 🔧 编译项目
```bash
mvn clean install
```

3. 🗄️ 配置数据库
```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE group_buying;
```

4. 📝 修改配置
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/group_buying
    username: your_username
    password: your_password
```

---

## 📖 使用说明

### 🔧 基础配置

1. 配置数据库连接信息
2. 配置 Redis 连接信息
3. 配置日志输出路径

### ▶️ 启动服务

```bash
mvn spring-boot:run
```

---

### 🧪 示例代码

创建团购订单：
```java
LockMarketPayOrderRequestDTO requestDTO = new LockMarketPayOrderRequestDTO();
requestDTO.setUserId("user123");
requestDTO.setActivityId(100123L);
requestDTO.setGoodsId("9890001");
requestDTO.setSource("s01");
requestDTO.setChannel("c01");
requestDTO.setOutTradeNo("ORDER123456");

Response<LockMarketPayOrderResponseDTO> response = marketTradeService.lockMarketPayOrder(requestDTO);
```

---

## 🗂️ 项目结构

```
group-buying/
├── group-buying-api            # 🌐 API接口定义
├── group-buying-app            # 🧠 应用服务层
├── group-buying-domain         # 🧱 领域模型层
├── group-buying-infrastructure # 🛠️ 基础设施层
├── group-buying-trigger        # 🔔 触发器层
└── group-buying-types          # 📦 公共类型定义
```

---

## 📌 注意事项

- ✅ 确保数据库和 Redis 服务正常运行
- 🔐 生产环境部署时注意修改配置文件中的敏感信息
- 🐳 建议使用 Docker 容器化部署

---

