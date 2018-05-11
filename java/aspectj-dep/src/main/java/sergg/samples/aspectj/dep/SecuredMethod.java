/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package sergg.samples.aspectj.dep;

import sergg.samples.aspectj.Secured;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
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
