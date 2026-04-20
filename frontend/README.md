# HealthPro 前端项目

## 项目简介

HealthPro 是一个个人健康管理平台的前端展示界面，采用纯HTML/CSS/JavaScript实现，无需构建工具即可运行。

## 功能模块

### 1. 用户认证
- 登录/注册页面 (`login.html`)
- JWT Token认证
- 自动登录状态检测

### 2. 用户中心 (`pages/user.html`)
- 个人信息展示与编辑
- 密码修改
- 健康数据统计概览

### 3. 身体数据 (`pages/body.html`)
- 身高、体重、血压、血糖记录
- BMI自动计算
- 数据趋势图表
- 健康建议模块
- 数据预测功能
- 详情页面 (`pages/body-detail.html`)

### 4. 饮食记录 (`pages/diet.html`)
- 三餐记录
- 热量摄入统计
- 健康饮食建议

### 5. 睡眠管理 (`pages/sleep.html`)
- 睡眠时长记录
- 睡眠质量评估
- 深度睡眠统计
- 睡眠建议与预测

### 6. 健身管理 (`pages/fitness.html`)
- 运动类型记录
- 卡路里消耗统计
- 运动时长追踪
- 健身建议

### 7. 社交广场 (`pages/social.html`)
- 健康经验分享
- 分类讨论区（身体数据、饮食、睡眠、健身）
- 点赞、评论功能
- 热门话题

## 技术特点

### 可视化展示
- 使用 Chart.js 绘制趋势图表
- 数据卡片可视化展示
- 进度环、饼图等多种图表

### 数据预测
- 基于历史数据的线性回归预测
- 体重、睡眠、运动等数据预测

### 健康建议
- BMI状态分析与建议
- 血压状态评估
- 血糖健康建议
- 睡眠质量建议

### 响应式设计
- 支持移动端和桌面端
- 底部导航栏（移动端）
- 顶部导航栏（桌面端）

## 项目结构

```
frontend/
├── index.html          # 入口文件（重定向到登录页）
├── login.html          # 登录/注册页面
├── css/
│   ├── common.css      # 公共样式
│   └── components.css  # 组件样式
├── js/
│   ├── api.js          # API接口封装
│   ├── utils.js        # 工具函数
│   └── charts.js       # 图表组件
├── pages/
│   ├── index.html      # 首页
│   ├── user.html       # 用户中心
│   ├── body.html       # 身体数据
│   ├── body-detail.html# 身体数据详情
│   ├── diet.html       # 饮食记录
│   ├── sleep.html      # 睡眠管理
│   ├── fitness.html    # 健身管理
│   └── social.html     # 社交广场
└── assets/             # 静态资源
```

## 后端接口状态

### 已实现接口
- ✅ 用户注册 `/Register`
- ✅ 用户登录 `/Login`
- ✅ 用户登出 `/Logout`
- ✅ 获取用户信息 `/GetInfo`
- ✅ 更新用户信息 `/UpdateInfo`
- ✅ 更新密码 `/UpdatePassword`
- ✅ 获取身体数据 `/Body/GetBody`
- ✅ 添加身体数据 `/Body/AddBody`
- ✅ 更新身体数据 `/Body/SetBody/{id}`
- ✅ 删除身体数据 `/Body/DeleteBody/{id}`

### 未实现接口（使用模拟数据）
- ⚠️ 饮食记录相关接口
- ⚠️ 睡眠管理相关接口
- ⚠️ 健身管理相关接口
- ⚠️ 社交广场相关接口

## 使用说明

### 1. 启动后端服务
确保后端服务运行在 `http://localhost:8080`

### 2. 打开前端页面
直接用浏览器打开 `frontend/index.html` 或 `frontend/login.html`

### 3. 注册/登录
- 首次使用请先注册账号
- 使用手机号和密码登录

### 4. 开始使用
登录后即可使用各项健康管理功能

## 注意事项

1. **跨域问题**：如果遇到跨域问题，请确保后端已配置CORS
2. **Token过期**：Token有效期为24小时，过期后需重新登录
3. **模拟数据**：部分功能使用模拟数据，等待后端接口实现
4. **浏览器兼容**：建议使用Chrome、Firefox、Edge等现代浏览器

## 开发说明

### API配置
修改 `js/api.js` 中的 `API_BASE_URL` 可更改后端地址

### 样式定制
- `css/common.css` - 全局样式变量和基础样式
- `css/components.css` - 组件和页面样式

### 添加新功能
1. 在 `pages/` 目录创建新页面
2. 在 `js/api.js` 添加API接口
3. 在导航栏添加链接

## 版本信息

- 版本：1.0.0
- 更新日期：2024-04-15
- 开发框架：原生HTML/CSS/JavaScript
- 图表库：Chart.js
