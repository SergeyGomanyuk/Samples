package sergg.samples;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version $Revision:$
 */
public class Main1 {

    private static final byte[] ATT_LOGON_APP_PASSWORD_PROD_BYTES = new byte[]{56, 86, 51, 114, 105, 84, 89, 51};
    private static final byte[] ATT_LOGON_APP_PASSWORD_FUNC_BYTES = new byte[]{67, 84, 88, 77, 71, 82, 49, 50, 51};
    private static final byte[] ATT_LOGON_ROLES_MAPPING_PROD_BYTES = new byte[]{67, 77, 71, 69, 78, 71, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 67, 77, 71, 85, 83, 69, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 73, 78, 70, 82, 65, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 65, 82, 76, 73, 78, 70, 82, 65, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 65, 82, 76, 85, 83, 69, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 83, 80, 84, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 65, 82, 76, 83, 80, 84, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 65, 82, 76, 73, 78, 70, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 65, 82, 76, 85, 83, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 73, 78, 70, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 67, 77, 71, 85, 83, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121};
    private static final byte[] ATT_LOGON_ROLES_MAPPING_FUNC_BYTES = new byte[]{67, 84, 88, 69, 78, 71, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 67, 84, 88, 85, 83, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 73, 78, 70, 82, 65, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 67, 77, 71, 85, 83, 69, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 65, 82, 76, 73, 78, 70, 82, 65, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 65, 82, 76, 85, 83, 69, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 84, 69, 83, 84, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 65, 82, 76, 84, 69, 83, 84, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 65, 82, 76, 73, 78, 70, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 65, 82, 76, 84, 83, 84, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 65, 82, 76, 85, 83, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 73, 78, 70, 61, 97, 100, 109, 105, 110, 105, 115, 116, 114, 97, 116, 111, 114, 59, 67, 77, 71, 84, 83, 84, 61, 114, 101, 97, 100, 111, 110, 108, 121, 59, 67, 77, 71, 85, 83, 82, 61, 114, 101, 97, 100, 111, 110, 108, 121};
    private static final byte[] GRAFANA_ADMIN_PASSWORD_BYTES = new byte[]{57, 85, 52, 113, 106, 88, 80, 50};


    private static long[] cpuLoads = new long[] { 5000L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L} ;
    private static long[] cpuTotals = new long[] { 5000L, 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L} ;
//    private static long[] cpuLoads = new long[] {
//        498L,
//    };
//    private static long[] cpuTotals = new long[] {
//            499L,
//    } ;

    public static void main(String[] args) {

        System.out.println(System.nanoTime());
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

        String passwordProd = new String(ATT_LOGON_APP_PASSWORD_PROD_BYTES);
        String passwordFunc = new String(ATT_LOGON_APP_PASSWORD_FUNC_BYTES);
        String mappingProd = new String(ATT_LOGON_ROLES_MAPPING_PROD_BYTES);
        String mappingFunc = new String(ATT_LOGON_ROLES_MAPPING_FUNC_BYTES);

        String passwordGrafana = new String(GRAFANA_ADMIN_PASSWORD_BYTES);

        System.out.println(passwordProd);
        System.out.println(passwordFunc);
        System.out.println(mappingProd);
        System.out.println(mappingFunc);

        System.out.println(passwordGrafana);

        Date d = new Date(1574629622718L);
        System.out.println("date is " + d);
    }
}
