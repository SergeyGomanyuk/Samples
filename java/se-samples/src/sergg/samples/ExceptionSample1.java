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
package sergg.samples;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
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
