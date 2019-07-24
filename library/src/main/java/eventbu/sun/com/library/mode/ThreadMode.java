package eventbu.sun.com.library.mode;

/**
 * 线程状态
 */
public enum ThreadMode {
        //默认,发布是什么进程 订阅就是什么进程
        POSTING,
       //主线程
        MAIN,
        //主线程队列形式
        MAIN_ORDERED,
        //后台进程做一些耗时的操作
        BACKGROUND,
        //异步的线程
        ASYNC
}
