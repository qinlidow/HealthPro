# HealthPro - 个人健康管理平台

## 项目简介

HealthPro 是一个全栈个人健康管理平台，采用 Spring Boot + 原生 HTML/CSS/JavaScript 实现，集成智谱 AI 提供智能健康对话与 RAG 知识库检索。

## 技术栈

### 后端

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | JDK |
| Spring Boot | 3.5.13 | 主框架 |
| MyBatis | 3.0.4 | ORM 框架，XML Mapper 映射 |
| MySQL | - | 关系型数据库 |
| HikariCP | - | 数据库连接池 |
| JJWT | 0.13.0 | JWT Token 生成与验证 |
| OkHttp | 4.12.0 | HTTP 客户端（调用 AI API） |
| Gson | 2.10.1 | JSON 序列化/反序列化 |
| Apache POI | 5.2.5 | 文档解析（知识库文档上传） |
| Lombok | - | 注解简化 POJO |
| Maven | 3.9.14 | 构建工具 |

### AI 集成

| 技术 | 说明 |
|------|------|
| 智谱 GLM-4-Flash | AI 聊天补全 |
| 智谱 Embedding-3 | 向量嵌入，RAG 知识库语义检索 |

### 前端

| 技术 | 说明 |
|------|------|
| HTML/CSS/JavaScript | 原生实现，无框架依赖 |
| Chart.js | CDN 引入，数据趋势图表 |
| 自定义 Canvas 图表库 | 折线图/柱状图/饼图/环形图/雷达图/进度环 |
| CSS 变量设计系统 | 自定义主题，Material Design 风格 |

## 功能模块

### 用户认证
- 登录/注册（手机号 + 密码）
- JWT Token 认证，24 小时有效期
- 自动登录状态检测

### 身体数据管理
- 身高、体重、体脂率、血压、血糖记录
- BMI 自动计算与健康评估
- 数据趋势图表与线性回归预测

### 饮食记录
- 三餐记录与热量摄入统计
- 健康饮食建议

### 睡眠管理
- 睡眠时长/质量/深度睡眠记录
- 睡眠建议与趋势预测

### 健身管理
- 运动类型/时长/卡路里消耗记录
- 健身建议与数据统计

### 社交广场
- 帖子发布/评论/点赞/关注/收藏
- 分类讨论区（身体数据、饮食、睡眠、健身）
- 帖子审核

### 每日打卡
- 每日打卡/连续打卡统计
- 月度打卡日历

### AI 健康助手
- 智谱 GLM-4-Flash 智能对话
- RAG 知识库：文档上传、向量嵌入、语义检索

## 项目结构

```
HealthPro/
├── src/main/java/com/learning/healthpro/
│   ├── HealthProApplication.java       # 启动类
│   ├── common/Result.java              # 统一响应封装
│   ├── config/                         # 配置类（AI/CORS/知识库初始化）
│   ├── context/                        # ThreadLocal 用户上下文
│   ├── controller/                     # 12 个控制器
│   ├── entity/                         # 12 个实体类
│   ├── interceptor/                    # JWT 认证拦截器
│   ├── mapper/                         # 12 个 Mapper 接口
│   ├── service/                        # 18 个 Service 接口与实现
│   └── utils/                          # JWT 工具类
├── src/main/resources/
│   ├── application.yml                 # 应用配置
│   └── mybatis/                        # 13 个 XML Mapper
├── frontend/
│   ├── index.html                      # 入口（重定向到登录页）
│   ├── login.html                      # 登录/注册页
│   ├── css/
│   │   ├── common.css                  # 全局样式变量与基础样式
│   │   └── components.css              # 组件与页面样式
│   ├── js/
│   │   ├── api.js                      # API 接口封装（fetch + JWT）
│   │   ├── utils.js                    # 工具函数（日期/BMI/Toast 等）
│   │   └── charts.js                   # 自定义 Canvas 图表库
│   ├── pages/
│   │   ├── index.html                  # 首页仪表盘
│   │   ├── body.html                   # 身体数据
│   │   ├── diet.html                   # 饮食记录
│   │   ├── sleep.html                  # 睡眠管理
│   │   ├── fitness.html                # 健身管理
│   │   ├── social.html                 # 社交广场
│   │   ├── social-detail.html          # 帖子详情
│   │   ├── social-post.html            # 发帖页
│   │   ├── chat.html                   # AI 健康助手
│   │   ├── user.html                   # 用户中心
│   │   ├── profile.html                # 个人资料编辑
│   │   └── favorites.html              # 收藏列表
│   └── assets/                         # 静态资源
└── pom.xml                             # Maven 配置
```

## 使用说明

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.9+（或使用项目自带的 Maven Wrapper）

### 2. 配置数据库
创建 MySQL 数据库 `healthpro`，并在 `src/main/resources/application.yml` 中配置连接信息。

### 3. 启动后端
```bash
./mvnw spring-boot:run
```
后端服务默认运行在 `http://localhost:8080`

### 4. 打开前端
直接用浏览器打开 `frontend/index.html` 或 `frontend/login.html`

### 5. 注册/登录
- 首次使用请先注册账号
- 使用手机号和密码登录

## 开发说明

### API 配置
修改 `frontend/js/api.js` 中的 `API_BASE_URL` 可更改后端地址

### 样式定制
- `css/common.css` - 全局样式变量和基础样式
- `css/components.css` - 组件和页面样式

### 添加新功能
1. 在 `pages/` 目录创建新页面
2. 在 `js/api.js` 添加 API 接口
3. 在导航栏添加链接

## 注意事项

1. **跨域问题**：后端已配置 CORS，允许所有来源
2. **Token 过期**：JWT Token 有效期 24 小时，过期后需重新登录
3. **浏览器兼容**：建议使用 Chrome、Firefox、Edge 等现代浏览器
4. **文件上传**：单文件最大 10MB，单次请求最大 20MB
