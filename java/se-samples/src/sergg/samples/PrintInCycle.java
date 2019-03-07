/**
 * @version $Revision:$
 */
public class PrintInCycle {
    public static void main(String[] args) throws InterruptedException {
        PrintInCycle o = new PrintInCycle();
        for(int i=0;;i++) {
            o.printAlive(i);
            Thread.sleep(1000);
        }
    }

    private void printAlive(int i) {
        System.err.println("I'm alive...");
    }
}
