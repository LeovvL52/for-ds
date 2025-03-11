# 医院火灾预警监控管理系统 - 后端服务

## 项目介绍
本项目是医院火灾预警监控管理系统的后端服务，基于 Spring Boot 3 开发，提供了用户管理、设备管理、火灾检测、消防检查等功能的 RESTful API 接口。

## 技术栈
- Spring Boot 3.2.1
- MyBatis Plus 3.5.4.1
- MySQL 8.0.33
- Maven
- Java 17
- Hutool 5.8.25
- Lombok 1.18.30
- Swagger 3 (springdoc-openapi)

## 功能模块
1. 用户认证模块
   - 用户登录
   - 用户注册
   - 修改密码
   - 获取用户信息

2. 用户管理模块
   - 用户信息维护
   - 用户权限控制

3. 摄像头设备模块
   - 设备管理
   - 状态监控
   - 设备配置

4. 火灾事件记录模块
   - 事件记录
   - 事件处理
   - 历史查询

5. 消防检查记录模块
   - 检查记录管理
   - 异常处理
   - 记录查询

6. 用户反馈模块
   - 反馈管理
   - 反馈处理
   - 历史查询

7. 系统日志模块
   - 操作日志记录
   - 日志查询
   - 日志清理

8. 数据统计分析模块
   - 火灾事件统计
   - 设备状态统计
   - 检查记录统计
   - 反馈情况统计

## 项目结构
```
src/main/java/com/gjq/
├── config/           # 配置类
├── controller/       # 控制器
├── service/         # 服务层
├── mapper/          # MyBatis接口
├── entity/          # 实体类
├── dto/             # 数据传输对象
├── common/          # 公共组件
└── utils/           # 工具类
```

## API 文档
项目集成了 Swagger3 (OpenAPI 3) 接口文档，可通过以下方式访问：

1. Swagger UI 界面
   - 访问地址：`http://localhost:8080/api/swagger-ui.html`
   - 提供了可视化的 API 接口文档
   - 支持在线调试 API 接口

2. OpenAPI 规范文档
   - 访问地址：`http://localhost:8080/api/v3/api-docs`
   - 提供标准的 OpenAPI 3.0 规范的 JSON 格式文档
   - 可导入到 Postman 等工具中使用

## 开发环境要求
- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- IDE（推荐使用 IntelliJ IDEA）

## 本地开发
1. 克隆项目到本地
2. 导入项目到 IDE
3. 修改 `application.yml` 中的数据库配置
4. 执行 `db/init.sql` 初始化数据库
5. 运行 `FireMonitoringApplication.java` 启动项目

## 接口文档
项目启动后，访问以下地址查看接口文档：
- http://localhost:8080/api/swagger-ui.html

## 部署说明
1. 打包：`mvn clean package`
2. 运行：`java -jar fire-monitoring-1.0.0.jar`

## 注意事项
- 默认管理员账号：admin
- 默认管理员密码：admin123
- 项目默认端口：8080
- 接口统一前缀：/api
- 文档访问路径：/api/swagger-ui.html
- 数据库名称：fire_monitoring 
