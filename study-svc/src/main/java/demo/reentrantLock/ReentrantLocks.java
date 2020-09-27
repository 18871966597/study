package demo.reentrantLock;

import java.math.BigDecimal;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 实现银行转账的可重入锁(模拟卡上一有钱就被转走的情况)
 * </p>
 *
 * @author ll Create on 20/9/25 17:26
 * @version 1.0
 */
public class ReentrantLocks {

    //第二种方法
    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {


        // 初始化银行账户有10000块钱
        Bank bank = new Bank(new BigDecimal("0"), "SHANGHAI110");

        Thread threadOut = new Thread(new SubMoney(bank, new BigDecimal("100"), lock, condition), "SubMoney");
        threadOut.start();
        // 保证先执行转出金额的线程
        Thread.sleep(100);
        Thread threadIn1 = new Thread(new AddMoney(bank, new BigDecimal("99"), lock, condition), "AddMoney1");
        threadIn1.start();
        Thread threadIn2 = new Thread(new AddMoney(bank, new BigDecimal("1"), lock, condition), "AddMoney2");
        threadIn2.start();
        threadOut.join();
        threadIn1.join();
        threadIn2.join();

        System.out.println(bank.getBankCode() + "账户余额：" + bank.getMoney());
    }
}