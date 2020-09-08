# study
基于springboot注解实现java的基础实现

基于理解实现了注册中心的Server和Client简单Demo
1.Server实现服务上线后对服务的注册以及服务的定时剔除
2.Client实现定时发送心跳向Server注册自己

集成了slf4j日志

实现了aop,Interceptor,filter以及filter和Interceptor的区别

Interceptor
 * 拦截器是基于java的反射机制的
 * 拦截器只能对action请求起作用
 * 拦截器可以访问action上下文、值栈里的对象
 * 在action的生命周期中，拦截器可以多次被调用
 
filter
 * filter依赖于servlet，只能在初始化的时候被调用一次！
 * 是基于函数回调实现。
 * 过滤器则可以对几乎所有请求起作用。不能访问上下文
 
实现增强处理器（ControllerAdvice）
对所有的错误返回统一的错误信息。不会出现Error界面 

实现自定义错误统一处理
对所有可识别的错误抛出对应自定义错误,以便错误定位

集成redis的发布订阅模式
简单demo实现的redis队列以及redis的订阅发布模式

实现mysql，orcal数据源的使用
实现的底层数据的存储

集成了rabbitMQ  
简单了解rabbitMQ的消息队列
rabbitMQ由连接工厂创建连接然后创建信道，给信道绑定交换器名称以及交换器名称
然后由信道发送消息,消息需要指定交换器和路由键（rabbitmq只能已[]byte形式的数据传输）.
消费者消费消息时需要获取 随机队列 或者 指定队列 绑定交换器和路由键

了解消息的客户端确认以及拒绝的实现。以及拒绝后的配置实现重新入队
四种交换器:
direct,默认的交换器  根据路由键完全匹配消费者
fanout,广播,广播到所有的消费者
topic,(*,#)两种类型匹配路由键(*代表一层结构匹配,#代表全匹配),命名以.分割（比如aaa.bbb）
head：消息头交换器。未深入了解