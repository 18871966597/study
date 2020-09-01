package demo.Exception;

import org.springframework.stereotype.Component;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/25 18:06
 * @version 1.0
 */
@Component
public class MyException extends RuntimeException {

    protected Integer errorCode;
    protected String errorMsg;

    public MyException() {

    }

    public MyException(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
