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
