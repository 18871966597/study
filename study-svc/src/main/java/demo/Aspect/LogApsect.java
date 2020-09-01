package demo.Aspect;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.Response;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 *     切面处理类
 * </p>
 *
 * @author ll Create on 20/8/25 14:42
 * @version 1.0
 */

@Aspect
@Component
// Component 不能缺少。Aspect只会把当前类标记为切面类 但不会注入到spring容器中
public class LogApsect {
    private static final Logger logger = LoggerFactory.getLogger(LogApsect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 第一个*代表返回类型不限
    // 第二个*代表所有类
    // 第三个*代表所有方法
    // (..) 代表参数不限
    // execution(<修饰符>? <返回类型> <包名>?<方法名>(<参数>)异常?)
    @Pointcut("execution(public * demo.controller.*.*(..))")
    public void pointCut() {
        logger.info("切面进入切点！！！");
    }





    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("切面：before方法开始执行！！！");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("<=====================================================");
        logger.info("请求来源： =》" + request.getRemoteAddr());
        logger.info("请求URL：" + request.getRequestURL().toString());
        logger.info("请求方式：" + request.getMethod());
        logger.info("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("请求参数：" + Arrays.toString(joinPoint.getArgs()));
        logger.info("------------------------------------------------------");
        startTime.set(System.currentTimeMillis());
    }

    // 定义需要匹配的切点表达式，同时需要匹配参数
    // @Around(value = "pointCut()&&args(arg)")
    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取入参参数
        // System.out.println("name:" + arg);
        System.out.println("切面：around方法开始执行！！！");
        Object object = pjp.proceed();
        System.out.println(object);
        System.out.println("切面：around方法结束执行！！！");
        return object;
    }

    @After("within(demo.controller.*Controller)")
    public void after() {
        System.out.println("切面：after方法开始执行!!!");
    }

    @AfterReturning(pointcut = "pointCut()", returning = "rst")
    public void afterRunning(Response rst) {
        if (startTime.get() == null) {
            startTime.set(System.currentTimeMillis());
        }
        System.out.println("切面：afterRunning方法开始执行！！！");
        logger.info("耗时（毫秒）：" + (System.currentTimeMillis() - startTime.get()));
        logger.info("返回数据：{"+rst+"}");
        logger.info("==========================================>");
    }

    @AfterThrowing(throwing="ex",pointcut = "pointCut()")
    public JSONObject afterThrowing(Throwable ex) throws IOException {
        System.out.println("切面：异常出现之后afterThrowing开始执行！！！");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","-1");
        jsonObject.put("message","服务端异常！"+ex.getMessage());
        return jsonObject;
    }
}
