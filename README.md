# Beauty Contest Voting System（美丽评选投票系统）

基于 Spring Boot 的美丽评选投票系统，提供用户注册、登录、候选人浏览和投票功能。

## 技术栈

- **后端框架**: Spring Boot 2.7.6
- **Java 版本**: JDK 1.8
- **ORM 框架**: MyBatis-Plus 3.4.2
- **数据库**: MySQL
- **认证方式**: JWT (JSON Web Token)
- **构建工具**: Maven
- **其他依赖**:
  - Lombok - 简化实体类
  - FastJSON - JSON 处理
  - JJWT - JWT 生成和解析

## 功能特性

### 用户模块
- ✅ 用户注册（用户名、密码、出生年份、手机号）
- ✅ 用户登录（返回 access_token 和 refresh_token）
- ✅ 用户登出（Token 黑名单机制）
- ✅ JWT 认证拦截器

### 候选人模块
- ✅ 候选人分页列表（每页默认 20 条，按 ID 倒序）
- ✅ 每个候选人包含最新 20 张图片（按创建时间倒序）

### 投票模块
- ✅ 用户投票（每人只能投一票）
- ✅ 投票记录日志
- ✅ 候选人票数统计

## 项目结构

```
src/main/java/com/voter/
├── auth/                      # 认证相关
│   ├── AuthUtil.java          # 认证工具类
│   └── TokenBlacklistService.java  # Token黑名单服务
├── config/                    # 配置类
│   ├── AuthUtilConfig.java    # AuthUtil配置
│   └── WebMvcConfig.java      # Web MVC配置（拦截器）
├── controller/                # 控制器层
│   ├── CandidateController.java   # 候选人接口
│   ├── VoteController.java        # 投票接口
│   └── VoterController.java       # 用户接口
├── dto/                       # 数据传输对象
│   ├── CandidateResponseDTO.java
│   ├── LoginDTO.java
│   ├── LoginResponseDTO.java
│   ├── PageRequestDTO.java
│   ├── RegisterUserDTO.java
│   ├── RespBody.java
│   └── VoteDTO.java
├── entity/                    # 实体类
│   ├── CandidatePhotos.java  # 候选人图片
│   ├── Candidates.java        # 候选人
│   ├── VoteRecords.java       # 投票记录
│   └── VotingUser.java        # 投票用户
├── exception/                 # 异常处理
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
├── interceptor/               # 拦截器
│   └── AuthInterceptor.java   # 认证拦截器
├── mapper/                    # MyBatis Mapper
│   ├── CandidatePhotosMapper.java
│   ├── CandidatesMapper.java
│   ├── VoteRecordsMapper.java
│   └── VotingUserMapper.java
├── service/                   # 服务层
│   ├── CandidateService.java
│   ├── VoteService.java
│   └── VoterService.java
└── utils/                     # 工具类
    ├── DigestUtil.java        # 加密工具
    └── JwtUtil.java           # JWT工具
```

## API 接口文档

### 基础信息

- **Base URL**: `http://localhost:8080`
- **认证方式**: Bearer Token（除登录、注册接口外，其他接口需要在 Header 中携带）

```
Authorization: Bearer <access_token>
```

### 用户相关接口

#### 1. 用户注册

```http
POST /voter/regist
Content-Type: application/json

{
  "username": "user001",
  "password": "123456",
  "birthdayYear": 1990,
  "phone": "13800138000"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

**字段校验**:
- `username`: 必填
- `password`: 必填
- `birthdayYear`: 必填，4位整数（1000-9999）
- `phone`: 可选，符合中国大陆手机号格式（11位，1开头）

#### 2. 用户登录

```http
POST /voter/login
Content-Type: application/json

{
  "username": "user001",
  "password": "123456"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
    "expiresIn": 7200,
    "userInfo": {
      "id": 1,
      "userName": "user001",
      "birthdayYear": 1990,
      "phone": "13800138000",
      "isVoted": false,
      "createTime": "2024-06-25 10:00:00"
    }
  }
}
```

**说明**:
- `accessToken`: 访问令牌，有效期 2 小时
- `refreshToken`: 刷新令牌，有效期 7 天
- `expiresIn`: 访问令牌过期时间（秒）

#### 3. 用户登出

```http
POST /voter/logout
Authorization: Bearer <access_token>
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": "登出成功"
}
```

**说明**: 登出后，该 token 会被加入黑名单，无法再使用。

### 候选人相关接口

#### 4. 候选人分页列表

```http
GET /candidate/list?pageNum=1&pageSize=20
Authorization: Bearer <access_token>
```

**请求参数**:
- `pageNum`: 页码（可选，默认 1）
- `pageSize`: 每页数量（可选，默认 20，最大 100）

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 100,
        "candidateName": "张三",
        "age": 25,
        "videoUrl": "https://example.com/video.mp4",
        "introduction": "候选人介绍...",
        "votesNumber": 1234,
        "createTime": "2024-06-25 10:00:00",
        "updateTime": "2024-06-25 10:00:00",
        "photos": [
          {
            "id": 1001,
            "candidateId": 100,
            "imageUrl": "https://example.com/photo1.jpg",
            "createTime": "2024-06-25 09:50:00"
          },
          {
            "id": 1002,
            "candidateId": 100,
            "imageUrl": "https://example.com/photo2.jpg",
            "createTime": "2024-06-25 09:45:00"
          }
        ]
      }
    ],
    "total": 100,
    "size": 20,
    "current": 1,
    "pages": 5
  }
}
```

**说明**:
- 候选人按 ID 倒序排列（最新的在前）
- 每个候选人包含最多 20 张图片，按创建时间倒序排列

### 投票相关接口

#### 5. 用户投票

```http
POST /vote
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "candidateId": 100
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

**业务规则**:
- 每个用户只能投一票
- 投票成功后，用户的 `isVoted` 字段变为 `true`
- 候选人的票数自动 +1
- 生成投票记录日志

**错误示例**:
```json
{
  "code": 400,
  "message": "您已经投过票了，不能重复投票",
  "success": false
}
```

### 统一响应格式

**成功响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": <数据>
}
```

**业务错误**:
```json
{
  "code": 400,
  "message": "错误信息",
  "success": false
}
```

**认证错误**:
```json
{
  "code": 401,
  "message": "未提供认证信息",
  "success": false
}
```

**系统错误**:
```json
{
  "code": 500,
  "message": "系统异常：...",
  "success": false
}
```

## 快速开始

### 1. 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 2. 数据库配置

修改 `src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/voting_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 3. 数据库表结构

SQL 文件位于 `src/main/resources/sql/` 目录下，创建数据库后依次执行以下文件：

1. `voting_user.sql` - 用户表
2. `candidates.sql` - 候选人表
3. `candidate_photos.sql` - 候选人图片表
4. `vote_records.sql` - 投票记录表

**数据库表结构**：

```sql
-- 1. 投票系统用户表
DROP TABLE IF EXISTS `voting_user`;
CREATE TABLE `voting_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `birthday_year` smallint NOT NULL COMMENT '出生年',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `is_voted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已经投票',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_user` varchar(125) NOT NULL COMMENT '创建人信息',
  `update_user` varchar(125) DEFAULT NULL COMMENT '更新人名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_index` (`user_name`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='投票系统用户表';

-- 2. 候选人信息表
DROP TABLE IF EXISTS `candidates`;
CREATE TABLE `candidates` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `candidate_name` varchar(125) NOT NULL COMMENT '候选人姓名',
  `age` int DEFAULT NULL COMMENT '候选人年龄',
  `video_url` varchar(500) NOT NULL COMMENT '候选人视频链接',
  `introduction` varchar(1000) DEFAULT NULL COMMENT '候选人介绍',
  `votes_number` int NOT NULL DEFAULT '0' COMMENT '投票数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `candidate_name_index` (`candidate_name`) USING BTREE COMMENT '候选人唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='候选人信息表';

-- 3. 候选人图片表
DROP TABLE IF EXISTS `candidate_photos`;
CREATE TABLE `candidate_photos` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `candidate_id` bigint NOT NULL COMMENT '候选人ID',
  `image_url` varchar(500) DEFAULT NULL COMMENT '图片链接',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `candidate_id_index` (`candidate_id`) USING BTREE COMMENT '候选人ID 索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='候选人图片表';

-- 4. 投票记录表
DROP TABLE IF EXISTS `vote_records`;
CREATE TABLE `vote_records` (
  `id` bigint NOT NULL COMMENT '自增长主键',
  `candidate_id` bigint NOT NULL COMMENT '候选人ID',
  `voter_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_voter_candidate` (`candidate_id`, `voter_id`) USING BTREE COMMENT '组合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='投票记录表';
```

### 4. 编译运行

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/beauty-contest-voting-0.0.1-SNAPSHOT.jar
```

项目默认运行在 `http://localhost:8080`

## 安全机制

### JWT 认证
- Access Token 有效期：2 小时
- Refresh Token 有效期：7 天
- Token 密钥：`beauty-contest-voting-secret-key-2024`（生产环境请修改）

### Token 黑名单
- 用户登出后，Token 立即加入黑名单
- 黑名单中的 Token 无法继续使用
- 已过期的 Token 自动从黑名单清除

### 密码加密
- 使用 MD5 加密存储用户密码
- 生产环境建议使用 BCrypt 等更安全的加密算法

### 拦截器
- 所有接口（除登录、注册）都需要认证
- 未认证请求返回 401 错误

## 开发说明

### 新增接口
1. 在 `controller` 包下创建控制器
2. 在 `service` 包下实现业务逻辑
3. 如需排除认证，在 `WebMvcConfig` 中配置

### 获取当前用户
在任何 Service 或 Controller 中使用：

```java
// 获取用户ID
Long userId = AuthUtil.getCurrentUserId();

// 获取用户名
String username = AuthUtil.getCurrentUsername();

// 获取完整用户信息
VotingUser user = AuthUtil.getCurrentUser();
```

### 异常处理
业务异常统一使用 `BusinessException`：

```java
throw new BusinessException("错误信息");
throw new BusinessException(400, "自定义错误码和信息");
```

## 许可证

本项目仅用于学习交流。

## 联系方式

如有问题，请提交 Issue。
