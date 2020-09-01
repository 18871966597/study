package demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * <p>
 * filter依赖于servlet，只能在初始化的时候被调用一次！
 * 是基于函数回调实现。
 * 过滤器则可以对几乎所有请求起作用。不能访问上下文
 * </p>
 *
 * @author ll Create on 20/8/31 10:57
 * @version 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "myfilter")
public class MyFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器：init方法开始执行！！！");
        // 注意 init 方法只执行一次
        // init 方法执行的时刻有两种：
        //（1） load-on-startup 的值大于等于0，则伴随 Servlet 实例化后执行。
        //（2） load-on-startup 的值小于0 或者 不配置， 则在第一次 Servlet 请求的时候执行。
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("过滤器：doFilter方法开始执行！！！");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("过滤器：destroy方法开始执行！！！");
    }
}
