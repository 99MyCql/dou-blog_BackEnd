## myblog_Backend

### 简介

博客系统后端

使用`spring boot`框架

### 项目配置

`/src/main/resources`目录下的`application.properties`文件为项目配置文件。

由于存在数据库账号密码，未用`git`跟踪，需要自己新建。

其文件内容大致如下：

```properties
# mysql配置
spring.datasource.url = jdbc:mysql://数据库地址(localhost):3306/数据库名?characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username = 用户名
spring.datasource.password = 密码
spring.datasource.driver-class-name = com.mysql.jdbc.Driver

swagger.title = Swagger页面标题

# http请求响应，设置utf-8编码
spring.http.encoding.charset = UTF-8
spring.http.encoding.enabled = true
spring.http.encoding.force = true

server.tomcat.uri-encoding = UTF-8
```

### 项目规范

#### `http`响应内容格式

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