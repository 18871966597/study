package demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 拦截器是基于java的反射机制的
 * 拦截器只能对action请求起作用
 * 拦截器可以访问action上下文、值栈里的对象
 * 在action的生命周期中，拦截器可以多次被调用
 * </p>
 *
 * @author ll Create on 20/8/31 10:31
 * @version 1.0
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器preHandle开始执行！！！");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器postHandle开始执行！！！");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器afterCompletion开始执行！！！");
    }
}
