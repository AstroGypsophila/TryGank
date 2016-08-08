package com.gypsophila.commonlib.net;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gypsophila on 2016/7/31.
 */
public class DefaultThreadPool {

    static final int THREAD_POOL_CORE_SIZE = 6;
    static final int THREAD_POOL_MAX_SIZE = 10;
    /**
     * 阻塞队列最大任务数量
     */
    static final int BLOCKING_QUEUE_SIZE = 20;

    private static DefaultThreadPool mInstance = null;

    public static synchronized DefaultThreadPool getInstance() {
        if (mInstance == null) {
            mInstance = new DefaultThreadPool();
        }
        return mInstance;
    }

    /**
     * 缓冲BaseRequest任务队列
     */
    static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
            DefaultThreadPool.BLOCKING_QUEUE_SIZE);

    static AbstractExecutorService pool = new ThreadPoolExecutor(
            THREAD_POOL_CORE_SIZE,
            THREAD_POOL_MAX_SIZE,
            15L, TimeUnit.SECONDS, blockingQueue,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * 执行任务
     * @param r
     */
    public void execute(final Runnable r) {
        if (r != null) {
            try {
                DefaultThreadPool.pool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭，但等待任务执行完成，不再接受新任务
     */
    public void shutDown() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdown();
        }
    }

    /**
     * 立即执行关闭，并挂起所有正执行的任务，不接受新任务
     */
    public void shutDownRightNow() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdown();
            try {
                DefaultThreadPool.pool.awaitTermination(1L, TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeTaskFromQueue(final Object object) {
        DefaultThreadPool.blockingQueue.remove(object);
    }

    public void removeAllTasks() {
        DefaultThreadPool.blockingQueue.clear();
    }

}
