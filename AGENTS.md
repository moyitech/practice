# Repository Guidelines

## 项目结构与模块组织
本仓库包含 Spring Boot 微服务后端与 Vue 2 前端。

- `backend/carbon-auth`：认证服务（JWT、Redis）。源码在 `src/main/java`，配置在 `src/main/resources`，测试在 `src/test/java`。
- `backend/carbon-system`：系统/数据面板服务。MyBatis 映射文件位于 `src/main/resources/mapper`。
- `backend/carbon-assets`：资产服务，结构同上，映射文件位于 `src/main/resources/mapper`。
- `frontend`：Vue 2 + Element UI 前端。源码在 `frontend/src`，静态资源在 `frontend/public`，图片等资源在 `frontend/src/assets`。

构建产物位于 `backend/*/target` 与 `frontend/dist`。

## 构建、测试与开发命令
请在各模块目录内执行命令。

- 后端（任一服务）：`mvn clean package`（构建），`mvn test`（测试），`mvn spring-boot:run`（本地运行）。
- 前端：`npm install`（安装依赖），`npm run serve`（本地运行，默认 `http://localhost:8080`），`npm run build`（生产构建）。

服务端口：auth `9091`，system `9092`，assets `9093`。

## 本地脚本
- 后端启动：`scripts/start-backend.sh`，停止：`scripts/stop-backend.sh`（日志在 `logs/`）。
- 前端启动：`scripts/start-frontend.sh`，停止：`scripts/stop-frontend.sh`。
- 数据库初始化：`scripts/init-db.sh`（支持 `MYSQL_CONTAINER`、`MYSQL_ROOT_PASSWORD` 覆盖）。

## 代码风格与命名约定
- Java：4 空格缩进；类名 `PascalCase`；方法/字段 `camelCase`；常量 `UPPER_SNAKE_CASE`；包名 `com.carbon.*`。
- Vue：2 空格缩进；组件名 `PascalCase`；文件名 `kebab-case` 或 `PascalCase`。
- 未启用统一格式化/静态检查工具，请保持与邻近代码一致。

## 测试指南
- 后端使用 `spring-boot-starter-test`（JUnit），测试位于 `backend/*/src/test/java`，命名以 `*Test.java` 结尾。
- 前端未配置测试框架；如需新增，请同时补充运行说明。

## 提交与 PR 规范
- Git 历史提交信息为简洁句式，无强制前缀；建议延续该风格。
- PR 需包含：简要说明、关联问题（如有）、验证步骤（执行了哪些命令）、UI 变更截图。若涉及端口或配置变更，请明确标注。

## 配置与环境提示
- 本地配置在 `backend/*/src/main/resources/application-dev.yml`。不要提交敏感信息，数据库与 Redis 密码请用本地覆盖。
- RocketMQ 和区块链相关能力为可选功能；启用时需在 PR 中补充环境配置与启动步骤。
- 本地依赖可通过 `docker-compose.yml` 启动（MySQL/Redis/RocketMQ/Nacos）。
- 建议使用 JDK 21 运行后端：`JAVA_HOME=$(/usr/libexec/java_home -v 21)`。

## 测试账号
- 初始化脚本：`backend/db/init.sql`
- 默认账号：`admin` / `admin123`
