package sergg.samples;

/**
 * @version $Revision:$
 */
public class ExceptionSample1 {
    public static void main(String[] args) throws Exception{
        try {
            foo();
        } catch (Throwable e) {
            throw e;
//            if (e instanceof Exception) throw (Exception) e;
//            else if (e instanceof Error) throw (Error) e;
//            else throw new Exception("Unknown error starting container", e);
        }
    }

    private static void foo() {
        throw new Error();
    }
}
