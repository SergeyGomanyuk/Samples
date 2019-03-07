import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @version $Revision:$
 */
public class Main {

    static int THREAD_NUM = 2;

    static final SimpleDateFormat globalGmtDateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    static {
        globalGmtDateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<THREAD_NUM; i++) {
            executorService.submit(new Worker());
        }
    }

    static class Worker implements Runnable {
        final SimpleDateFormat localGmtDateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        {
            localGmtDateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }

        @Override
        public void run() {
            while (true) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(1000, 120000));
                Date time = calendar.getTime();
                String global = localGmtDateFormatter.format(time);
                String local = globalGmtDateFormatter.format(time);
                if (!global.equals(local)) {
                    System.err.println(local + " != " + global);
                }
            }
        }
    }
}
