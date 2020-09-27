package demo.reentrantLock;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/9/25 17:29
 * @version 1.0
 */
public class SubMoney implements Runnable {

    private Bank bank;

    private BigDecimal changeMoney;

    private ReentrantLock lock;

    private Condition condition;

    SubMoney(Bank bank, BigDecimal changeMoney, ReentrantLock lock, Condition condition) {
        this.bank = bank;
        this.changeMoney = changeMoney;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始执行！！！");
        try {
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                // 当余额不足时一直等待金额的转入
                while (changeMoney.compareTo(bank.getMoney()) > 0) {
                    System.out.println("余额不足！！！等待中...");
                    condition.signalAll();
                    condition.await();
                }
                bank.setMoney(bank.getMoney().subtract(changeMoney));
                System.out.println(Thread.currentThread().getName() + "转出成功！！！");
                System.out.println(Thread.currentThread().getName() + "账户余额：" + bank.getMoney());
            } else {
                System.out.println("转出失败！！！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
