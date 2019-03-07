package sergg.samples;

/**
 * @version $Revision:$
 */
public class MainHappenBefore {

    boolean  i = false;

    public static void main(String[] args) throws InterruptedException {
        new MainHappenBefore().start();
    }


    private void start() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("i = " + i);
                }
            }
        }).start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("set i = true");
                i = true;
            }
        }).start();
    }
}
