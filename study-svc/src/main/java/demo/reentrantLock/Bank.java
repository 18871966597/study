package demo.reentrantLock;

import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/9/25 17:31
 * @version 1.0
 */
public class Bank {

    /**
     * 余额
     */
    private volatile BigDecimal money;

    /**
     * 银行账号
     */
    private String bankCode;

    Bank(BigDecimal money, String bankCode) {
        this.money = money;
        this.bankCode = bankCode;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
