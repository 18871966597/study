package demo.controllerAdvice;

import com.alibaba.fastjson.JSONObject;
import demo.Exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * controller 增强器
 * </p>
 *
 * @author ll Create on 20/8/25 18:04
 * @version 1.0
 */
@RestControllerAdvice
public class ControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public JSONObject errorHandler(Exception ex) {
        logger.error("全局异常处理器执行开始！！！");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "-1");
        jsonObject.put("message", ex.getMessage());
        return jsonObject;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    public JSONObject myErrorHandler(MyException ex) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", ex.getErrorCode());
        jsonObject.put("message", ex.getErrorMsg());
        return jsonObject;
    }


}
