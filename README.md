<div align="center">

# Personal Blog

一个基于 Vue 3 + Spring Boot 的个人博客系统

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.4-42b883.svg)](https://vuejs.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

</div>

---

## 项目简介

Personal Blog 是一个功能完整的全栈博客系统，支持 Markdown 文章编写、代码高亮、分类标签管理、嵌套评论等功能。前端使用 Vue 3 + Element Plus 构建，后端采用 Spring Boot + MyBatis-Plus，数据库为 MySQL。

---

## 功能特性

- **文章管理** — 支持 Markdown 编写，代码语法高亮，草稿/发布状态切换
- **分类与标签** — 文章分类归档，多标签关联，支持分类筛选
- **全文搜索** — 按关键词快速检索文章
- **评论系统** — 支持嵌套/楼中楼评论
- **用户认证** — JWT 令牌登录，路由守卫保护管理页面
- **后台管理** — 文章、分类、标签、评论的完整 CRUD 操作
- **数据统计** — 管理后台 Dashboard 展示文章、评论等统计数据

---

## 技术栈

| 层级 | 技术 | 说明 |
|:---:|:---:|:---|
| **前端** | Vue 3 + Vite | 响应式框架 + 构建工具 |
| **UI** | Element Plus | Vue 3 组件库 |
| **状态管理** | Pinia | Vue 3 官方推荐状态管理 |
| **HTTP** | Axios | 前后端通信 |
| **Markdown** | markdown-it + highlight.js | 文章渲染 + 代码高亮 |
| **后端** | Spring Boot 3.2 | Java Web 框架 |
| **ORM** | MyBatis-Plus | 数据库访问 |
| **认证** | JWT + BCrypt | 安全认证与密码加密 |
| **数据库** | MySQL | 数据存储 |

---

## 项目结构

```
personal-blog/
├── blog-server/                    # 后端 (Spring Boot)
│   └── src/main/java/com/blog/
│       ├── controller/             # API 控制器
│       ├── service/                # 业务逻辑层
│       ├── mapper/                 # 数据访问层
│       ├── entity/                 # 数据实体
│       ├── config/                 # 配置类
│       ├── interceptor/            # JWT 拦截器
│       └── util/                   # 工具类
├── blog-web/                       # 前端 (Vue 3)
│   └── src/
│       ├── api/                    # API 请求封装
│       ├── components/             # 公共组件
│       ├── views/                  # 页面视图
│       │   └── admin/              # 后台管理页面
│       ├── router/                 # 路由配置
│       ├── stores/                 # Pinia 状态管理
│       └── utils/                  # 工具函数
└── blog.sql                        # 数据库建表脚本
```

---

## 快速开始

### 环境要求

- **JDK 17+**
- **Node.js 18+**
- **MySQL 8.0+**
- **Maven 3.6+**

### 1. 初始化数据库

创建数据库并导入 SQL 脚本：

```sql
CREATE DATABASE blog;
USE blog;
SOURCE blog.sql;
```

### 2. 配置后端

修改 `blog-server/src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 你的数据库密码
```

### 3. 启动后端

```bash
cd blog-server
mvn spring-boot:run
```

后端默认运行在 `http://localhost:8080`

### 4. 启动前端

```bash
cd blog-web
npm install
npm run dev
```

前端默认运行在 `http://localhost:5173`

---

## 页面预览

| 页面 | 路径 | 说明 |
|:---:|:---:|:---|
| 首页 | `/` | 文章列表，支持分页和分类筛选 |
| 文章详情 | `/article/:id` | Markdown 渲染，评论区 |
| 搜索 | `/search` | 关键词搜索文章 |
| 登录 | `/login` | 用户登录 |
| 注册 | `/register` | 用户注册 |
| 管理后台 | `/admin/dashboard` | 数据统计面板 |
| 文章管理 | `/admin/articles` | 文章增删改查 |
| 分类管理 | `/admin/categories` | 分类增删改 |
| 标签管理 | `/admin/tags` | 标签管理 |
| 评论管理 | `/admin/comments` | 评论审核与管理 |

---

## API 概览

| 模块 | 方法 | 路径 | 说明 |
|:---:|:---:|:---:|:---|
| 认证 | POST | `/api/auth/register` | 用户注册 |
| 认证 | POST | `/api/auth/login` | 用户登录 |
| 文章 | GET | `/api/articles` | 文章列表（分页） |
| 文章 | GET | `/api/articles/{id}` | 文章详情 |
| 文章 | GET | `/api/articles/search?keyword=` | 文章搜索 |
| 分类 | GET | `/api/categories` | 分类列表 |
| 标签 | GET | `/api/tags` | 标签列表 |
| 评论 | GET | `/api/comments/article/{id}` | 文章评论 |
| 管理 | GET | `/api/admin/dashboard` | 后台统计 |
| 管理 | POST | `/api/admin/articles` | 创建文章 |
| 管理 | PUT | `/api/admin/articles/{id}` | 更新文章 |
| 管理 | DELETE | `/api/admin/articles/{id}` | 删除文章 |

---

## License

本项目基于 [MIT License](LICENSE) 开源。
