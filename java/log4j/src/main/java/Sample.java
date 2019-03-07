import java.util.Calendar;
import java.util.Date;

/**
 * @version $Revision:$
 */
public class Sample {

    private final static String PERFORMANCE_SESSION =
            "    <void method=\"add\">\n" +
                    "     <object class=\"com.jnetx.slee.management.smp.performance.PerformanceSessionDescriptor\">\n" +
                    "      <string>jNETx_3IR_NGIN_Counters</string>\n" +
                    "      <int>60</int>\n" +
                    "      <int>0</int>\n" +
                    "      <object class=\"com.jnetx.slee.management.smp.monitoring.SimpleSessionSchedule\">\n" +
                    "       <object class=\"java.util.Date\">\n" +
                    "        <long>1343204229251</long>\n" + // active starting from time
                    "       </object>\n" +
                    "       <object class=\"java.util.Date\">\n" +
                    "        <long>" + (Long.MAX_VALUE - 1) + "</long>\n" + // and till the end of time
                    "       </object>\n" +
                    "      </object>\n" +
                    "      <object class=\"com.jnetx.slee.management.smp.performance.ResetRule\" id=\"ResetRule0\" method=\"valueOf\">\n" +
                    "       <string>NONE</string>\n" +
                    "      </object>\n" +
                    "      <object class=\"com.jnetx.slee.management.smp.performance.EmptyPerformanceAction\"/>\n" +
                    "      <object class=\"java.util.ArrayList\">\n" +
                    "        {0}\n" +
                    "      </object>\n" +
                    "      <string>jNETx</string>\n" +
                    "      <string>jNETx</string>\n" +
                    "      <void property=\"enabled\">\n" +
                    "       <boolean>true</boolean>\n" +
                    "      </void>\n" +
                    "     </object>\n" +
                    "    </void>";


    public static void main(String[] args)
    {
        // get a calendar instance, which defaults to "now"
        Calendar calendar = Calendar.getInstance();

        // get a date to represent "today"
        Date today = calendar.getTime();
        System.out.println("today:    " + today);

        // add one day to the date/calendar
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);



        // now get "tomorrow"
        Date tomorrow = calendar.getTime();

        // print out tomorrow's date
        System.out.println("tomorrow: " + tomorrow);
    }

//    public static void main(String[] args) {
//
//        System.out.println(PERFORMANCE_SESSION);
//
//        System.out.println("null instanceof Object:" + (null instanceof Object));
//
//        String output = "\\033[K\\033[34m\\033[1mactive\\033[0m";
//        //output = output.replace("\\033[0m", "");
//        output = output.replaceAll("\\[K|\\[.{1,4}m", "");
//        //output = output.replaceAll("\\033[K\\033[34m\\033[1mactive\\033[0m", "");
//        System.out.println(output);
//
//
//    }
}
