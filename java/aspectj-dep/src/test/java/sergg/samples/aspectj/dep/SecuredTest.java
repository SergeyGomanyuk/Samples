package sergg.samples.aspectj.dep;

import org.junit.Test;
import sergg.samples.aspectj.dep.SecuredMethod;

/**
 * @version $Revision:$
 */
public class SecuredTest {
    @Test
    public void testMethod() throws Exception {
        SecuredMethod service = new SecuredMethod();
        service.unlockedMethod();
        service.lockedMethod();
    }
}
