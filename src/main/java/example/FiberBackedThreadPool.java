package example;

import org.eclipse.jetty.util.thread.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FiberBackedThreadPool implements ThreadPool {
    ExecutorService executorService = Executors.newWorkStealingPool(10); // TODO tune for the minimum number of threads we need

    @Override
    public void execute(Runnable command) {
        FiberScope.background().schedule(executorService, command);
    }

    @Override
    public void join() throws InterruptedException {
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }

    @Override
    public int getThreads() {
        return 0; // TODO: change?
    }

    @Override
    public int getIdleThreads() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isLowOnThreads() {
        return false;
    }
}
