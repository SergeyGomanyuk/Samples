package sergg.samples;

public class ParallelAccess {

    private static long x = 0;
    private static long y = 0;

    public static void main(String[] args) throws InterruptedException {
        new ParallelAccess().performParallelAccess();
    }

    private void performParallelAccess() {

        for(;;) {
            x = 0;
            y = 0;
            Thread p = new Thread(() -> {
                x = 1;
                y = 1;
            });

            Thread q = new Thread(() -> {
                System.out.println("x=" + x + ", y=" + y);
            });

            q.start();
            p.start();

            try {
                q.join();
                p.join();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

/**
 *         final Thread mainThread = Thread.currentThread();
 *         Thread p = new Thread(() -> {
 *             try {
 *                 Thread.sleep(1000);
 *             } catch (InterruptedException e) {
 *                 e.printStackTrace();
 *             }
 *             mainThread.interrupt();
 *         });
 *         p.start();
 */
