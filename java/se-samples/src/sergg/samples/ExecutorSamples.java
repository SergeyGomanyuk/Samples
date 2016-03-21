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
        invokeAllWithTimeout1();
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
