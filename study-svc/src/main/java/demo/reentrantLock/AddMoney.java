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
public class AddMoney implements Runnable {

    private Bank bank;

    private BigDecimal changeMoney;

    //第二种方法
    private ReentrantLock lock;

    private Condition condition;

    AddMoney(Bank bank, BigDecimal changeMoney, ReentrantLock lock, Condition condition) {
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
                bank.setMoney(bank.getMoney().add(changeMoney));
                System.out.println(Thread.currentThread().getName() + "转入成功！！！");
                System.out.println(Thread.currentThread().getName() + "账户余额：" + bank.getMoney());
                condition.signalAll();
            } else {
                System.out.println("转入失败！！！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
