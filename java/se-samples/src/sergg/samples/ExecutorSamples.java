package sergg.samples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by sergeygo on 17.03.2016.
 */
public class ExecutorSamples {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = new ThreadPoolExecutor(1, 1000, 100000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(false));
        Future monitorTask = threadPool.submit(new MonitorTask(3000));
        Thread.sleep(10000);
        threadPool.shutdownNow();
    }

//    public static void invokeAllWithTimeout2() throws InterruptedException {
//        final ExecutorService executorService = Executors.newFixedThreadPool(2);
//
//            Collection<Callable<Object>> callables = new ArrayList<>();
//            callables.add(new Task("" + i));
//            System.out.println("run task: " + i);
//            final List<Future<Object>> futures = executorService.invokeAll(callables, 1, TimeUnit.SECONDS);
//            System.out.println("canceled: " + futures.get(0).isCancelled());
//            System.out.println("done: " + futures.get(0).isDone());
//            try {
//                System.out.println("return: " + futures.get(0).get());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        executorService.shutdown();
//    }
//

    public static void invokeAllWithTimeout1() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(4);

        Collection<Callable<Object>> callables = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            callables.add(new InterruptibleTask("" + i));
        }
        for (int i = 2; i < 4; i++) {
            callables.add(new NonInterruptibleTask("" + i));
        }
        final List<Future<Object>> futures = executorService.invokeAll(callables, 1, TimeUnit.SECONDS);
        System.out.println("canceled: " + futures.get(0).isCancelled());
        System.out.println("done: " + futures.get(0).isDone());
        try {
            System.out.println("return: " + futures.get(0).get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int activeCount = ((ThreadPoolExecutor)executorService).getActiveCount();
        while(activeCount != 0) {
            System.out.println(executorService);
            Thread.sleep(1000);
            activeCount = ((ThreadPoolExecutor)executorService).getActiveCount();
        }

        executorService.shutdown();
    }

    private static class MonitorTask implements Runnable {
        private int msek;

        public MonitorTask(int ms) {
            this.msek = ms;
        }

        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(msek);
                    System.out.println("Monitor task run...");
                } catch (InterruptedException e) {
                    System.out.println("Monitor task sleep interrupted: " + e);
                    Thread.currentThread().interrupt();
                } catch (Throwable t) {
                    System.out.println("Error has happened" + t);
                }
            }
        }
    }


    private static class InterruptibleTask implements Callable<Object> {

        private final String name;

        public InterruptibleTask(String name) {
            this.name = name;
        }

        @Override
        public Object call() throws Exception {
            try {
                System.out.println("task started: " + name);
                Thread.sleep(5000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("task finished: " + name + "; interruped:" + Thread.currentThread().isInterrupted());
            }
            return null;
        }
    }

    private static class NonInterruptibleTask implements Callable<Object> {

        private final String name;

        public NonInterruptibleTask(String name) {
            this.name = name;
        }

        @Override
        public Object call() throws Exception {
            try {
                System.out.println("task started: " + name);
                Thread.currentThread().interrupt();
                Thread.sleep(5000);
            } catch(InterruptedException e) {
//                Thread.sleep(5000);
            } finally {
                System.out.println("task finished: " + name);
            }
            return null;
        }
    }
}
