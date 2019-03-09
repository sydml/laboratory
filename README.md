# 实验项目
#### 拆分为common,redis,rabbitmq,elasticsearch,db(mysql,oracle,mongodb,redis)等项目单独实验
#### 以上实验项目均在springboot2.x版本构建
1. advanced-redis 
- 已增加延迟消息队列实现 **RedisDelayQueue**
- 实现分布式可重入锁 **RedisReentrantLock**
2. common 模块为平时使用的工具类的封装

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
  ...
3. RabbitMq 基础项目后期会合并

4. 后期增加Spring 事务相关实验

5. ElasticSearch 只做简单demo

