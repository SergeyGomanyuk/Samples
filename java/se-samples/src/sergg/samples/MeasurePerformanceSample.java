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

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class MeasurePerformanceSample {
    private static final Logger log = Logger.getLogger("MeasurePerformanceSample");
    private final String name;
    private final long reportTimeout;

    private long timestamp;

    private long min;
    private long max;
    private long avg;
    private long count;

    MeasurePerformanceSample(String name, long reportTimeout) {
        this.name = name;
        this.reportTimeout = reportTimeout;
        reset();
    }

    public void reset() {
        timestamp = System.nanoTime();
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
        avg = 0;
        count = 0;
    }

    public void count(long val) {
        if(val < min) {
            min = val;
        }
        if(val > max) {
            max = val;
        }
        avg = (avg*(count++) + val)/count;

        if(reportTimeout !=-1 && System.nanoTime() - timestamp > reportTimeout) {
            log.info(toString());
            reset();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MeasurePerformanceSample measurePerformanceSample = new MeasurePerformanceSample("MeasurePerformanceSample", -1);
        for(int i=0; i<10; i++) {
            long now = System.nanoTime();
            Thread.sleep(500);
            measurePerformanceSample.count(System.nanoTime() - now);
        }
        log.info(measurePerformanceSample.toString());
    }

    @Override
    public String toString() {
        return "MeasurePerformanceSample{" +
                "name='" + name + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", avg=" + avg +
                ", count=" + count +
                '}';
    }
}
