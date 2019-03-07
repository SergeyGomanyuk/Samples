package sergg.samples;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version $Revision:$
 */
public class Main1 {

    private static long[] cpuLoads = new long[] { 5000L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L} ;
    private static long[] cpuTotals = new long[] { 5000L, 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L} ;
//    private static long[] cpuLoads = new long[] {
//        498L,
//    };
//    private static long[] cpuTotals = new long[] {
//            499L,
//    } ;

    public static void main(String[] args) {
//        long costInfoValue = 1501;
//        int costInfoExponent = 2;
//        costInfoValue = costInfoValue * (long)(Math.pow(10, costInfoExponent));
//        System.out.println("costInfoValue = " + costInfoValue);
//
//        Date timeAndTimezoneDateValue = new Date(Long.valueOf("1226402084000"));
//        capTimeAndTimezone = new SimpleDateFormat("yyyyMMddHHmmss").format(timeAndTimezoneDateValue);
//        System.out.println("epoch " + System.currentTimeMillis()/1000);
        long allcpuTotal = 0;
        long allcpuLoad = 0;

        long sumPercent = 0;

        for (int cpuId = 0; cpuId < cpuLoads.length; cpuId++) {
            long cpuLoad = cpuLoads[cpuId];
            long cpuTotal = cpuTotals[cpuId];
            long percent = (long) (((double) cpuLoad / (double) cpuTotal) * 100d);
            System.out.println("[ALL-" + cpuId + ": " + percent + "]");
            allcpuTotal += cpuTotal;
            allcpuLoad += cpuLoad;
            sumPercent += percent;
        }
        double allcpuLoad1 = allcpuLoad;
        double allcpuTotal1 = allcpuTotal;
        long percentAll = (long) ((allcpuLoad1 / allcpuTotal1) * 100d);

        long percentAllNew = sumPercent / cpuLoads.length;


        System.out.println("[allcpuLoad1:"+allcpuLoad1+", allcpuTotal1:"+allcpuTotal1+", ALL:" + percentAll + ", ALL_NEW:" + percentAllNew + "]");

//        for(long used=0; used<16040; used++) {
//            for(long total=15960; total<16040L; total++) {
//                long ku = (long) (((double)used / (double)total) * 100d);
//                if(ku == 100) {
//                    System.out.println("used:" + used + ", total:" + total);
//                }
//            }
//        }
    }
}
