package demo.threadPoll;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/9/9 15:46
 * @version 1.0
 */
public class ThreadPoll {

    // 线程最大线程数什么时候会被创建
    // 1.线程池执行过程,先看线程数是否已经到达核心数。没有就创建新的线程
    // 2.如果线程数和核心数相等就将任务加到阻塞队列中,
    // 3.如果塞队队列满了的话就去判断线程数量是否到达最大线程数,没有就创建新的线程
    // 4.如果已经创建了最大线程数就执行拒绝策略
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 10, 5L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));
        List<Future<String>> fatureList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<String> faFutureTask = threadPoolExecutor.submit(new TestThreadPoll(String.valueOf(i)));
            fatureList.add(faFutureTask);
        }
        for (Future<String> future : fatureList) {
            String s = future.get();
            // System.out.println(s);
        }
        threadPoolExecutor.shutdown();
    }


}
