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
package sergg.samples.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
@Aspect
public class SecuredMethodAspect {
    @Pointcut("@annotation(secured)")
    public void callAt(Secured secured) {
    }

    @Around("callAt(secured)")
    public Object around(ProceedingJoinPoint pjp, Secured secured) throws Throwable {
        System.out.println("SecuredMethodAspect: " + pjp + " " + secured);
        return secured.value() ? null : pjp.proceed();
    }
}
