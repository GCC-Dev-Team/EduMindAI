<a name="JjySf"></a>
## API风格
统一使用[RESTful API](https://restfulapi.cn/)。
<a name="NemgW"></a>
## 响应格式
```typescript
interface APIResponse<T> {
  data: T;
  code: number;
  msg: string;
}
```

- data：JSON格式的响应数据。
   - 出于可扩展性考虑，data必须是对象不能是数组。
   - 如果请求失败，data可以为null。
- code：自定义状态码，非[HTTP状态码](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status)。
- msg：额外消息，可用于请求失败时的错误提示。
- 字段命名风格：返回的字段统一采用[驼峰命名法](https://baike.baidu.com/item/%E9%A9%BC%E5%B3%B0%E5%91%BD%E5%90%8D%E6%B3%95/7560610)。
<a name="rhnUq"></a>
## ID生成
统一使用uuid。
<a name="ty2t6"></a>
## 时间格式
返回的时间格式统一用[Unix时间戳](https://baike.baidu.com/item/unix%E6%97%B6%E9%97%B4%E6%88%B3/2078227)。
<a name="Ke3SN"></a>
## HTTP状态码
![](https://cdn.nlark.com/yuque/0/2024/png/34697499/1716033862300-5e335e11-9944-4a82-8f57-0ef90dc79cdc.png#averageHue=%23f9f9f9&clientId=ufda9e364-9061-4&from=paste&id=ue6d01aad&originHeight=260&originWidth=634&originalType=url&ratio=1&rotation=0&showTitle=false&status=done&style=none&taskId=u0cea0608-48b5-4c69-bf1d-5de39f3c3d4&title=)<br />遵循[HTTP状态码](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status)规范，例如：

- 资源创建成功返回201状态码。
- 授权认证失败返回401状态码。
- 资源不存在则返回404状态码。
- ......
<a name="WTVsu"></a>
## OpenAPI
参见[OpenAPI 规范 (中文版)](https://openapi.apifox.cn/)。
<a name="dIYQu"></a>
## JWT
[https://juejin.cn/post/7180234792313552933](https://juejin.cn/post/7180234792313552933)<br />过期时间：7天<br />请求头格式：
```typescript
Authorization: Bearer <token>token
```
<a name="WeIvR"></a>
## 参数验证
[https://github.com/any86/any-rule](https://github.com/any86/any-rule)
<a name="JvdfC"></a>
## 文档书写:

- 请求路由:
- 请求方式:
- 请求参数:
- 返回结果:
<a name="AWLHX"></a>
## 控制器:
跟后台管理相关的API，控制器和路由前缀可以命名为 dashboard。<br />示例：/dashboard/students/heat-map<br />后台管理相关API暂时不需要登录验证，支持未登录调用。
<a name="SnCQr"></a>
# API路由
根据模块定义API路由，每个API路由需要包括以下信息：

1. 路由地址。示例：/user/login
2. 请求方法。示例：GET、POST 
3. 请求参数。示例：
   1. Body参数，需要包括每个字段的验证逻辑：
```typescript
interface RequestBody {
  name: string; //最小长度为1，最大长度为20，只能包含字母、数字、下划线
  password: string; //最小长度为6，最大长度为20，只能包含字母、数字、下划线
}
```

   1. Query参数，：
```typescript
interface QueryParams {
  userId?: number; //可选，用户id
}
```

1. 返回结果。示例：
```typescript
interface ResponseBody {
  user: User;
  content: string;
}
```

