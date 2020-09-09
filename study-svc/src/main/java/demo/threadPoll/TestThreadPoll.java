package demo.threadPoll;

import java.util.concurrent.Callable;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/9/9 15:57
 * @version 1.0
 */
public class TestThreadPoll implements Callable {

    private String name;

    TestThreadPoll(String name) {
        this.name = name;
    }

    @Override
    public String call() {
        System.out.println(name + "当前线程名称：" + Thread.currentThread().getName());
        return name;
    }
}
