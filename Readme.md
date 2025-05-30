# 接口文档

统一接口前缀：`/api/v1`

## HTTP普通

### 1. 用户登录接口

📍 **URL**

```http
POST /user/login
```

🔧 **请求参数**

| 参数名   | 类型     | 是否必填 | 说明     |
| -------- | -------- | -------- | -------- |
| username | `String` | 是       | 用户名   |
| password | `String` | 是       | 用户密码 |

✅ **返回参数**（`ResultVO<String>`）

| 字段 | 类型      | 说明            |
| ---- | --------- | --------------- |
| code | `int`     | 状态码，200成功 |
| msg  | `String?` | 错误信息        |
| data | `String`  | 登录成功的token|

📌 **示例**

请求

```json
{
  "username": "root",
  "password": "root"
}
```

响应

```json
{
  "code": 200,
  "msg": null,
  "data": 一串token
}
```

```json
{
  "code": 401,
  "msg": "用户名或密码错误",
  "data": null
}
```

------

### 2. 获取全部题目简要信息

📍 **URL**

```http
GET /problem
```

🔧 **请求参数**

无

✅ **返回参数**（`ResultVO<List<SimpleProblemVO>>`）

📌 **示例**

```json
{
  "code": 200,
  "msg": null,
  "data": [
    {
      "uuid": "xxxx-xxxx-xxxx-xxxx",
      "title": "两数之和"
    }
  ]
}
```

------

### 3. 获取单个题目详细信息

📍 **URL**

```http
GET /problem/{uuid}
```

🔧 **路径参数**

| 参数名    | 类型     | 是否必填 | 说明    |
| --------- | -------- | -------- | ------- |
| uuid | `String` | 是       | 题目 ID |

✅ **返回参数**（`ResultVO<ProblemVO>`）

**示例**

```json
{
  "code": 200,
  "msg": null,
  "data": {
	"uuid": "xxxx-xxxx-xxxx-xxxx",
    "title": "两数之和",
    "description": "...",
    "exampleStdio": [
      ["1 1", "2", ""]
    ],
    "timeLimit": 1000,
    "memoryLimit": 256000
  }
}
```

------

### 4. 提交代码

📍 **URL**

```http
POST /problem/{uuid}
```

🔧 **请求参数**

| 参数名    | 类型     | 是否必填 | 说明       |
| --------- | -------- | -------- | ---------- |
| uuid | `String` | 是       | 题目 ID    |
| lang      | `String` | 是       | 语言       |
| code      | `String` | 是       | 提交的代码 |

✅ **返回参数**（`ResultVO<String>`）

**示例**

```json
{
  "code": 200,
  "msg": null,
  "data": "提交代码成功"
}
```

如果10s内提交过代码，拒绝评测

```json
{
  "code": 403,
  "msg": "提交过于频繁",
  "data": null
}
```

------

### 5. 获取用户信息

📍 **URL**

```http
GET /user
```

🔧 **请求参数**

无

✅ **返回参数**（`ResultVO<UserInfo>`）

**示例**

```json
{
  "code": 200,
  "msg": null,
  "data": {
    "username": "admin",
    "email": "admin@example.com",
    "nickname": "ding",
    "role": "admin"
  }
}
```

------

### 6. 修改用户信息

📍 **URL**

```http
PUT /updateUser
```

🔧 **请求体**

| 参数名   | 类型     | 是否必填 | 说明   |
| -------- | -------- | -------- | ------ |
| username | `String` | 是       | 用户名 |
| email    | `String` | 是       | 邮箱   |
| nickname | `String` | 是       | 昵称   |

**示例**

```json
{
  "username": "admin",
  "email": "admin@example.com",
  "name": "zheng"
}
```

✅ **返回参数**（`ResultVO<String>`）

```json
{
  "code": 200,
  "msg": null,
  "data": "更新信息成功"
}
```

------

### 7. 修改用户密码

📍 **URL**

```http
POST /user/password
```

🔧 **请求体**

| 参数名      | 类型     | 是否必填 | 说明   |
| ----------- | -------- | -------- | ------ |
| oldPassword | `String` | 是       | 旧密码 |
| newPassword | `String` | 是       | 新密码 |

**示例**

```json
{
  "oldPassword": "114514",
  "newPassword": "1919810",
}
```

✅ **返回参数**（`ResultVO<String>`）

```json
{
  "code": 200,
  "msg": null,
  "data": "密码修改成功"
}
```

```json
{
  "code": 401,
  "msg": "旧密码不正确",
  "data": null
}
```
---

### 8. 查询单个评测记录

📍 **URL**

```http
GET /problem/submit/{submitId}
```

✅ **返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": {
	"submitId": "xxxx-xxxx-xxxx-xxxx",
	"problemId": "xxxx-xxxx-xxxx-xxxx",
    "lang": "C++",
    "code": "int main() {}",
    "time": 1919,
    "memory": 1145141,
    "result": "答案正确",
    "submissionTime": "2025-05-25 22:52:49.031323",
    "score": 100
  }
}
```

对于正在评测的记录

```json
{
  "code": 200,
  "msg": null,
  "data": {
	"submitId": "xxxx-xxxx-xxxx-xxxx",
	"problemId": "xxxx-xxxx-xxxx-xxxx",
    "lang": "C++",
    "code": "int main() {}",
    "memory": null,
    "time": null,
    "result": "正在评测",
    "dateTime": "2025-05-25 22:52:49.031323",
    "score": null
  }
}
```

对于不存在的记录，返回code 404(Not Found)

```json
{
  "code": 404,
  "msg": "评测记录不存在",
  "data": null
}
```

### 9.查询当前用户所有简略评测记录

**URL**

```http
GET /problem/submit
```

**返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": [
    {
      "submitId": "xxxx-xxxx-xxxx-xxxx",
	  "problemId": "xxxx-xxxx-xxxx-xxxx",
      "lang": "C++",
      "time": null,
      "memory": null,
      "result": "正在评测",
      "exitCode": 0,
      "dateTime": "2025-05-25 22:52:49.031323",
      "score": null
    },
    {
      "submitId": "xxxx-xxxx-xxxx-xxxx",
	  "problemId": "xxxx-xxxx-xxxx-xxxx",
      "lang": "Python",
      "time": 114,
      "memory": 244000,
      "result": "答案正确",
      "dateTime": "2025-05-24 22:52:49.031323",
      "score": 100
    }
  ]
}
```

如果发现数组中有正在评测的记录，1s后重新发送请求。

## HTTP管理

统一接口前缀：`/mgr`

### 1. 创建用户

🎈 **URL**

```http
POST /user/create
```

🔨**请求参数**

| 参数名   | 类型     | 是否必填 | 说明   |
| -------- | -------- | -------- | ------ |
| username | `String` | 是       | 用户名 |
| password | `String` | 是       | 密码   |
| email    | `String` | 是       | 邮箱   |
| nickname | `String` | 是       | 昵称   |

✅ **返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": "创建用户成功"
}
```

```json
{
  "code": 409,
  "msg": "用户名已存在",
  "data": null
}
```

### 2. 删除用户

 **URL**

```http
DELETE /user/{username}
```

✅ **返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": "删除用户成功"
}
```

```json
{
  "code": 403,
  "msg": "权限不足",
  "data": null,
}
```

```json
{
  "code": 404,
  "msg": "用户不存在",
  "data": null
}
```

### 3. 创建题目

**URL**

```http
POST /problem/create
```

**请求参数**

| 参数名          | 类型                                  | 是否必填 | 说明                           |
| --------------- | ------------------------------------- | -------- | ------------------------------ |
| title           | String                                | 是       | 标题                           |
| description     | String                                | 是       | 描述                           |
| time_limit      | Int                                   | 是       | 时间限制                       |
| time_reserved   | Int                                   | 是       | 时间保留限制                   |
| memory_limit    | Int                                   | 是       | 内存限制                       |
| memory_reserved | Int                                   | 是       | 内存保留限制                   |
| large_stack     | Boolean                               | 是       | 是否开大栈                     |
| output_limit    | Int                                   | 是       | 输出行数限制（0为无限制）      |
| process_limit   | Int                                   | 是       | 进程限制（0为无限制）          |
| example_stdio   | `List<Tuple<String, String, String>>` | 否       | 样例标准输入，输出，错误输出   |
| stdio           | `List<Tuple<String, String, String>>` | 否       | 测试点标准输入，输出，错误输出 |

**返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": "题目创建成功"
}
```

### 4. 删除题目

**URL**

```http
DELETE /problem/{problemId}
```

**返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": "题目删除成功"
}
```

```json
{
  "code": 404,
  "msg": "题目不存在",
  "data": null
}
```

### 5. 修改题目

**URL**

```http
PUT /problem
```

**请求参数**

| 参数名        | 类型                                  | 是否必填 | 说明                           |
| ------------- | ------------------------------------- | -------- | ------------------------------ |
| problemId     | String                                | 是       | 题目编号 
| title         | String                                | 是       | 标题                           |
| description   | String                                | 是       | 描述                           |
| time_limit      | Int                                   | 是       | 时间限制                       |
| time_reserved   | Int                                   | 是       | 时间保留限制                   |
| memory_limit    | Int                                   | 是       | 内存限制                       |
| memory_reserved | Int                                   | 是       | 内存保留限制                   |
| large_stack     | Boolean                               | 是       | 是否开大栈                     |
| output_limit    | Int                                   | 是       | 输出行数限制（0为无限制）      |
| process_limit   | Int                                   | 是       | 进程限制（0为无限制）          |
| example_stdio | `List<Tuple<String, String, String>>` | 否       | 样例标准输入，输出，错误输出   |
| stdio         | `List<Tuple<String, String, String>>` | 否       | 测试点标准输入，输出，错误输出 |

**返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": "修改题目成功"
}
```

```json
{
  "code": 404,
  "msg": "题目不存在",
  "data": null
}
```

### 6. 获取单个题目详细信息

> 管理员也可使用HTTP普通接口，因此可以通过`获取题目简要信息列表`来获得所有题目的`problemId`

**URL**

```http
GET /problem/{problemId}
```

**返回参数**

```json
{
  "code": 200,
  "msg": null,
  "data": {
	"problemId": "xxxx-xxxx-xxxx-xxxx",
    "title": "两数之和",
    "description": "给定两个整数，输出它们的和",
    "time_limit": 1,
    "time_reserved": 1,
    "memory_limit": 256000,
    "memory_reserved": 256000,
    "large_stack": false,
    "output_limit": 0,
    "process_limit": 0,
    "example_stdio": [
      ["1 1", "2", ""]
    ],
    "stdio": [
      ["114 514", "1919", ""]
    ]
  }
}
```

```json
{
  "code": 404,
  "msg": "题目不存在",
  "data": null
}
```
