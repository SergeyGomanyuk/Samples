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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        final long startTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").parse("2017_12_30_20_30_35").getTime();
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
//        c.setTime(new Date());
        c.setTimeInMillis(startTime);
        System.out.println("YEAR " + c.get(Calendar.YEAR));
        System.out.println("MONTH " + (c.get(Calendar.MONTH) + 1));
        System.out.println("DAY_OF_MONTH " + c.get(Calendar.DAY_OF_MONTH));
        System.out.println("HOUR_OF_DAY " + c.get(Calendar.HOUR_OF_DAY));
        System.out.println("MINUTE " + c.get(Calendar.MINUTE));
        System.out.println("SECOND " + c.get(Calendar.SECOND));
        System.out.println("WEEKDAY " + c.get(Calendar.DAY_OF_WEEK));
    }
}
