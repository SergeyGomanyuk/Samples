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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class Main1 {
    public static void main(String[] args) {
//        long costInfoValue = 1501;
//        int costInfoExponent = 2;
//        costInfoValue = costInfoValue * (long)(Math.pow(10, costInfoExponent));
//        System.out.println("costInfoValue = " + costInfoValue);
//
//        Date timeAndTimezoneDateValue = new Date(Long.valueOf("1226402084000"));
//        capTimeAndTimezone = new SimpleDateFormat("yyyyMMddHHmmss").format(timeAndTimezoneDateValue);
        System.out.println("epoch " + System.currentTimeMillis()/1000);
    }
}
