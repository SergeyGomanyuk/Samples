package sergg.samples.aspectj.dep;

import sergg.samples.aspectj.Secured;

/**
 * @version $Revision:$
 */
public class SecuredMethod {

    @Secured()
    public void lockedMethod() {
        System.out.println("SecuredMethod.lockedMethod");
    }

    @Secured(false)
    public void unlockedMethod() {
        System.out.println("SecuredMethod.unlockedMethod");
    }
}
