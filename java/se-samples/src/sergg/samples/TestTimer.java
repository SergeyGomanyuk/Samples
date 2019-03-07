package sergg.samples;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @version $Revision:$
 */
public class TestTimer {
    public static void main(String[] args) throws InterruptedException {
        Timer t = new Timer();

        TimerTask[] tts = new TimerTask[] {
            new MyTimerTask(0), new MyTimerTask(2), new MyTimerTask(3),
        };


        System.err.println("1");
        t.schedule(tts[0], 1000);
        t.schedule(tts[1], 2000);

        System.err.println("2");
//        tts[0].cancel();
//        t.purge();
//        tts[1].cancel();
//        t.purge();
        Thread.sleep(5000);
        System.err.println("3");
        t.schedule(tts[2], 3000);
    }

    static class MyTimerTask extends TimerTask {
        private final int i;

        public MyTimerTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.err.println("run " + i);
            throw new RuntimeException();
        }
    }
}
