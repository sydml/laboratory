# 实验项目
#### 拆分为common,redis,rabbitmq,elasticsearch,db(mysql,oracle,mongodb,redis)等项目单独实验
#### 以上实验项目均在springboot2.x版本构建
一. advanced-redis 
- 已增加延迟消息队列实现 **RedisDelayQueue**
- 实现分布式可重入锁 **RedisReentrantLock**

二. common 模块为平时使用的工具类的封装

 - CGLibDynamicProxy
 - JDKDynamicProxy
 - ReflectionUtil
 - CastUtil
 - ClassUtil
 - StreamUtil
 - JsonUtil
 - FileUtil
 - PropsUtil
 - StringUtil
 - TimeUtil
  ...
  
三. RabbitMq 基础项目后期会合并

四. 后期增加Spring 事务相关实验
- 实现脚手架，
 - ORM：spring-data-jpa,
 - DB: Mysql
 - 增加注解 @PrintLog 通过注解形式解决接口调用入参，出参打印入侵代码问题

五. ElasticSearch 只做简单demo

六. 重写了framework
 - IOC 容器支持接口多实现注入
 - 增加了@Primary,@Order 解决接口多个实现类问题
 - 增加@ResponseBody注解
 - 目前存在问题：测试该框架时，IOC 容器初始化时，如果项目域名与框架域名不一致，则IOC容器为空，需要调试IOC 初始化路径问题
 
七. Web项目Authorization 鉴权token方式简单实现redis存储（此处没有加密，仅做简单功能验证），Interceptor实现类获取并鉴权
    _技术栈：redis,jpa_
 ##### 对于Interceptor实现类后续增加处理方案
 - todo 指定拦截：可以增加注解的方式，根据注解拦截需要授权的接口
 - todo 统一处理返回结果：可以根据是否授权在这里直接写入返回结果到response中

