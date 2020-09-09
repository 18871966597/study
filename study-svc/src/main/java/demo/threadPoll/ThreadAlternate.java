package demo.threadPoll;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 实现两个线程交替打印
 * </p>
 *
 * @author ll Create on 20/9/9 17:52
 * @version 1.0
 */
public class ThreadAlternate {
    public static void main(String[] args) {
        Counter counter = new Counter();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 1000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(16));
        threadPoolExecutor.submit(new PrintOdd(counter));
        threadPoolExecutor.submit(new PrintEven(counter));
        threadPoolExecutor.shutdown();
    }
}

class Counter {
    public int value = 1;
    public boolean odd = true;
}

class PrintOdd implements Runnable {
    public Counter counter;

    public PrintOdd(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        while (counter.value <= 10) {
            synchronized (counter) {
                if (counter.odd) {
                    System.out.println(counter.value);
                    counter.value++;
                    counter.odd = false;
                    //很重要，要去唤醒打印偶数的线程
                    counter.notifyAll();
                }
                try {
                    counter.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}

class PrintEven implements Runnable {
    public Counter counter;

    public PrintEven(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        while (counter.value <= 10) {
            synchronized (counter) {
                if (!counter.odd) {
                    System.out.println(counter.value);
                    counter.value++;
                    counter.odd = true;
                    counter.notifyAll();
                }
                try {
                    counter.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}