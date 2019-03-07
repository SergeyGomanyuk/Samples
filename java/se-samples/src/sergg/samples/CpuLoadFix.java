package sergg.samples;

import java.util.Arrays;

/**
 * Generates Load on the CPU by keeping it busy for the given load percentage
 *
 * @author https://caffinc.github.io/2016/03/cpu-load-generator/
 * @version $Revision:$
 */
public class CpuLoadFix {
    /**
     * Starts the Load Generation
     * @param args Command line arguments, ignored
     */
    public static void main(String[] args) {
        System.out.println("args=" + Arrays.toString(args));
        System.out.println("security manager=" + System.getSecurityManager());
        int numCore = Integer.parseInt(args[0]); // 2;
        int numThreadsPerCore = Integer.parseInt(args[1]); // 2;
        double load = Double.parseDouble(args[2]); // 0.8;
        final long duration = Long.parseLong(args[3]); // 100000;
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }
    }

    /**
     * Thread that actually generates the given load
     * @author Sriram
     */
    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep(50);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}