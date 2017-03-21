package gjw.bibi.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class ThreadPool {

    private ThreadPool() {
    }

    private static ThreadPool threadPool = new ThreadPool();

    private static ThreadPool getInstance() {
        return threadPool;
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ExecutorService getGlobalTread() {
        return executorService;
    }
}
