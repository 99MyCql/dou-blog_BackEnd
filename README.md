# dou-blog_Backend

## Introduction

博客系统后端，使用`spring boot`框架。

前端见[dou-blog_FrontEnd](https://github.com/99MyCql/dou-blog_FrontEnd)。

## Requirement Analysis

- [ ] 用户
    - [x] 登录
    - [x] 注册
        - [x] 创建用户名、密码
        - [ ] 邮箱验证
    - [x] 用户修改自己信息
    - [x] 管理员可以删除用户
    - [x] 分类：管理员与普通用户
- [ ] 文章
    - [x] 管理员才能发布文章
    - [x] 管理员可以删除文章
    - [x] 用户可以浏览文章
    - [x] 评论
        - [x] 所有用户登录后都能评论文章
        - [x] 管理员可以删除评论
    - [ ] 分类
        - [ ] 管理员可以添加分类
        - [ ] 分类具有树级关系
        - [ ] 管理员可以设置文章的分类
        - [ ] 用户可以根据分类筛选文章

## Usage

### Add Configuration File

由于配置文件中保存了数据库账号和密码，未用`git`跟踪，需要自己新建。

在`src/main/resources`目录下，新建配置文件`application.properties`。内容大致如下：

```config
# Mysql
spring.datasource.url = jdbc:mysql://数据库地址(localhost):3306/数据库名?characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username = 用户名
spring.datasource.password = 密码
spring.datasource.driver-class-name = com.mysql.jdbc.Driver

# Swagger
swagger.title = Swagger页面标题

# Http
spring.http.encoding.charset = UTF-8
spring.http.encoding.enabled = true
spring.http.encoding.force = true

# Built in web server --- Tomcat
server.tomcat.uri-encoding = UTF-8
```

### Run

在命令行中直接运行：

```cmd
mvn spring-boot:run
```

在命令行中编译：

```cmd
mvn install
```

生成`dou-blog-0.0.1-SNAPSHOT.jar`于`target/`目录下，运行`.jar`包：

```cmd
cd target
java -jar dou-blog-0.0.1-SNAPSHOT.jar
```

以上操作可在IDEA中进行。

## Format

### Git Commit

```cmd
git commit -m "type: description"
```

- type:
    - feat：新功能（feature）
    - fix：修补bug
    - docs：文档（documentation）
    - style：格式（不影响代码运行的变动）
    - refactor：重构（即不是新增功能，也不是修改bug的代码变动）
    - test：增加测试
    - chore：构建过程或辅助工具的变动
- description: 详细描述

### Http Response

采用`json`格式，`json`中具体内容如下：

|字段名|类型|含义|可否为空|
|---|---|---|---|
|`code`|整型|状态码，具体要求见**状态码表**|不可为空|
|`msg`|字符串|请求响应的相关信息|不可为空|
|`data`|可`json`化的字符串|响应的数据|可为空|

`code`字段对应的状态码：

|状态码|含义|
|---|---|
|`0`|失败(如未找到该用户，失败)|
|`1`|成功(如查询到该用户，成功)|
|`2`|未登录|
|`3`|莫得权限|
|`4`|服务端出现错误(如更新用户信息出现错误)|

示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": "{username:aaa}"
}
```
