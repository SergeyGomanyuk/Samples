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

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class MemoryUtilization {
    public static void main(String[] args) throws InterruptedException {
        final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        System.out.println("OperatingSystemMXBean");
        System.out.println("=====================");

        System.out.println("name: " + operatingSystemMXBean.getName());
        System.out.println("version: " + operatingSystemMXBean.getVersion());
        System.out.println("arch:" + operatingSystemMXBean.getArch());
        System.out.println("availableProcessors: " + operatingSystemMXBean.getAvailableProcessors());

        while(true) {
            System.out.println();
            System.out.println("=====================");

            System.out.println("totalPhysicalMemorySize: " + operatingSystemMXBean.getTotalPhysicalMemorySize());
            System.out.println("freePhysicalMemorySize: " + operatingSystemMXBean.getFreePhysicalMemorySize());
            System.out.println("committedVirtualMemorySize: " + operatingSystemMXBean.getCommittedVirtualMemorySize());
            System.out.println("-------------------");
            System.out.println("totalSwapSpaceSize: " + operatingSystemMXBean.getTotalSwapSpaceSize());
            System.out.println("freeSwapSpaceSize: " + operatingSystemMXBean.getFreeSwapSpaceSize());
            System.out.println("-------------------");
            System.out.println("processCpuLoad: " + operatingSystemMXBean.getProcessCpuLoad());
            System.out.println("processCpuTime: " + operatingSystemMXBean.getProcessCpuTime());
            System.out.println("systemCpuLoad: " + operatingSystemMXBean.getSystemCpuLoad());
            System.out.println("systemLoadAverage: " + operatingSystemMXBean.getSystemLoadAverage());

            Thread.sleep(5000);
        }
    }
}
