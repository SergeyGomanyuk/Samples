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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class DateFormatSamples {
    private static final SimpleDateFormat SEND_REPORT_DATE_PATTERN = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws ParseException {
//        printNextTimeToSchedule("*:*:09");
//        printNextTimeToSchedule("*:*:69");
//        printNextTimeToSchedule("*:13:45");
//        printNextTimeToSchedule("18:13:45");
//        printNextTimeToSchedule("28:13:45");
//        printNextTimeToSchedule("128:13:45");

        scheduledTime("01:01:30");
        scheduledTime("20:01:30");
        scheduledTime("23:01:30");

    }

    private static void scheduledTime(String reportSendingStart) throws ParseException {
        Calendar now = Calendar.getInstance();
        Calendar req = Calendar.getInstance();
        req.setTime(SEND_REPORT_DATE_PATTERN.parse(reportSendingStart));

        now.set(Calendar.SECOND, req.get(Calendar.SECOND));
        now.set(Calendar.MINUTE, req.get(Calendar.MINUTE));
        now.set(Calendar.HOUR_OF_DAY, req.get(Calendar.HOUR_OF_DAY));

        if(new Date().after(now.getTime())) {
            now.add(Calendar.DAY_OF_MONTH, 1);
        }

        System.out.println("now:" + now.getTime());
    }

    public static String REG = "(\\*|\\d{2}):(\\*|\\d{2}):(\\*|\\d{2})";

    private static void printNextTimeToSchedule(String s) {
        Pattern p = Pattern.compile(REG);
        Matcher m = p.matcher(s);
        if(m.matches()) {
            String hh = m.group(1);
            int hours = -1;
            if(!"*".equals(hh)) {
                hours = Integer.parseInt(hh);
            }
            String mm = m.group(2);
            int minutes = -1;
            if(!"*".equals(mm)) {
                minutes = Integer.parseInt(mm);
            }
            String ss = m.group(3);
            int seconds = -1;
            if(!"*".equals(ss)) {
                seconds = Integer.parseInt(ss);
            }
            System.out.println("hh:" + hours + ", mm:" + minutes + ", ss:" + seconds);
        } else {
            System.out.println(s + " doesn't match " + REG);
            return;
        }
    }
}
