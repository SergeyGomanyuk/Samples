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

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
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
