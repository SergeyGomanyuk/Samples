/**
 * Copyright (counter) Amdocs jNetX.
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class SyncNft {
    private static final int TOTAL_PRODUCERS = 8;

    public SyncNft(Counter globalCounter, long duration, boolean printStatistics) {
        this.globalCounter = globalCounter;
        this.duration = TimeUnit.SECONDS.toNanos(duration);
        this.printStatistics = printStatistics;
    }

    public static void main(String[] args) throws InterruptedException {
//        new SyncNft(new NonThreadSafeCounter(), 30, false).test();
//        new SyncNft(new NonThreadSafeCounter(), 10, true).test();
//
//        new SyncNft(new ReadWriteLockCounter(), 30, false).test();
//        new SyncNft(new ReadWriteLockCounter(), 10, true).test();
//
//
//        new SyncNft(new AtomicCounter(), 30, false).test();
//        new SyncNft(new AtomicCounter(), 10, true).test();
//
//        new SyncNft(new ReentrantExclusiveCounter(), 30, false).test();
//        new SyncNft(new ReentrantExclusiveCounter(), 10, true).test();
//
//        new SyncNft(new ManyWritesRareReadsCounter(), 30, false).test();
//        new SyncNft(new ManyWritesRareReadsCounter(), 10, true).test();

        new SyncNft(new ManyWritesRareReadsCounter(), 30, false).testManyWritesRareReadsLock();
    }

    private void testManyWritesRareReadsLock() throws InterruptedException {
        final ManyWritesOrRareReadsLock lock = new ManyWritesOrRareReadsLock();

        Future<?> writeLock1 = executorService.submit(() -> { lock.writeLock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("writeLock1: " + writeLock1.isDone());
        System.out.println();

        Future<?> writeLock2 = executorService.submit(() -> { lock.writeLock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("writeLock2: " + writeLock2.isDone());
        System.out.println();

        Future<?> readLock1 = executorService.submit(() -> { lock.readLock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("readLock1: " + readLock1.isDone());
        System.out.println();

        Future<?> writeLock3 = executorService.submit(() -> { lock.writeLock();  });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("writeLock3: " + writeLock3.isDone());
        System.out.println();

        Future<?> readLock2 = executorService.submit(() -> { lock.readLock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("readLock2: " + readLock2.isDone());
        System.out.println();

        Future<?> writeUnlock1 = executorService.submit(() -> { lock.writeUnlock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("writeUnlock1: " + writeUnlock1.isDone());
        System.out.println("readLock1: " + readLock1.isDone());
        System.out.println("readLock2: " + readLock2.isDone());
        System.out.println("writeLock3: " + writeLock3.isDone());
        System.out.println();

        Future<?> writeUnlock2 = executorService.submit(() -> { lock.writeUnlock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("writeUnlock2: " + writeUnlock2.isDone());
        System.out.println("readLock1: " + readLock1.isDone());
        System.out.println("readLock2: " + readLock2.isDone());
        System.out.println("writeLock3: " + writeLock3.isDone());
        System.out.println();

        Future<?> readUnlock1 = executorService.submit(() -> { lock.readUnlock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("readUnlock1: " + readUnlock1.isDone());
        System.out.println("writeLock3: " + writeLock3.isDone());
        System.out.println();

        Future<?> readUnlock2 = executorService.submit(() -> { lock.readUnlock(); });

        Thread.sleep(100);
        System.out.println(""+lock);
        System.out.println("readUnlock2: " + readUnlock2.isDone());
        System.out.println("writeLock3: " + writeLock3.isDone());
        System.out.println();

        Future<?> writeUnlock3 = executorService.submit(() -> { lock.writeUnlock(); });
        System.out.println(""+lock);
        System.out.println("writeUnlock3: " + writeUnlock3.isDone());

        executorService.shutdown();
    }

    private final Counter globalCounter;
    private final long duration;
    private final boolean printStatistics;
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    private void test() throws InterruptedException {
        if(printStatistics) {
            System.out.println("--- " + globalCounter.getClass().getSimpleName());
        } else {
            System.out.println("warming " + globalCounter.getClass().getSimpleName());
        }
        for(int i=0; i<TOTAL_PRODUCERS; i++) {
            executorService.submit(new Producer(i));
        }
        executorService.submit(new Consumer());
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        if(printStatistics) System.out.println("---");
        if(printStatistics) System.out.println("");
    }

    private class Producer implements Runnable {
        final int n;
        final Counter localCounter = new NonThreadSafeCounter();

        public Producer(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            try {
                long finishTimestamp = System.nanoTime() + duration;
                while (finishTimestamp > System.nanoTime()) {

                    long timestamp = System.nanoTime();

                    long stepTimestamp = timestamp + TimeUnit.MILLISECONDS.toNanos(5);
                    while(stepTimestamp > System.nanoTime());
                    globalCounter.sample(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timestamp));
                    localCounter.sample(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timestamp));
                }

                CounterValue counterValue = localCounter.get();
                if(printStatistics) System.out.println("producer:" + n +
                        ", count:" + counterValue.getCount() +
                        ", min:" + counterValue.getMin() +
                        ", max:" + counterValue.getMax() +
                        ", avg:" + counterValue.getAvg());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                long endTimestamp = System.nanoTime() + duration;

                while(endTimestamp > System.nanoTime()) {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                    CounterValue counterValue = globalCounter.get();
                    if(printStatistics) System.out.println("consumer:" +
                            "count:" + counterValue.getCount() +
                            ", min:" + counterValue.getMin() +
                            ", max:" + counterValue.getMax() +
                            ", avg:" + counterValue.getAvg());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface Counter {
        void sample(long value);
        CounterValue get();
    }

    public static class CounterValue {
        private final long value;
        private final long min;
        private final long max;
        private final long sum;
        private final long count;

        public CounterValue(long value, long min, long max, long sum, long count) {
            this.value = value;
            this.min = min;
            this.max = max;
            this.sum = sum;
            this.count = count;
        }

        public long getValue() {
            return value;
        }

        public long getMin() {
            return count==0?0:min;
        }

        public long getMax() {
            return count==0?0:max;
        }

        public long getAvg() {
            return count==0?0:(sum/count);
        }

        public long getCount() {
            return count;
        }
    }

    public static class NonThreadSafeCounter implements Counter {

        private long value = 0;
        private long min = Long.MAX_VALUE;
        private long max = Long.MIN_VALUE;
        private long sum = 0;
        private long count = 0;

        @Override
        public void sample(long value) {
            this.value = value;
            sum += value;
            count++;
            if (value < min) min = value;
            if (value > max) max = value;
        }

        @Override
        public CounterValue get() {
            return new CounterValue(value, min, max, sum, count);
        }
    }

    public static class ReadWriteLockCounter extends NonThreadSafeCounter {

        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = readWriteLock.readLock();
        private Lock writeLock = readWriteLock.writeLock();

        @Override
        public void sample(long value) {
            writeLock.lock();
            try {
                super.sample(value);
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public CounterValue get() {
            readLock.lock();
            try {
                return super.get();
            } finally {
                readLock.unlock();
            }
        }
    }

    public static class AtomicCounter implements Counter {

        private AtomicLong value = new AtomicLong();
        private AtomicLong min = new AtomicLong(Long.MAX_VALUE);
        private AtomicLong max = new AtomicLong(Long.MIN_VALUE);
        private AtomicLong sum = new AtomicLong();
        private AtomicLong count = new AtomicLong();

        private void updateMinimum(long v) {
            while(true) {
                long curMin = min.get();
                if(v < curMin) {
                    if(min.weakCompareAndSet(curMin, v)) break;
                } else {
                    break;
                }
            }
        }

        private void updateMaximum(long v) {
            while(true) {
                long curMax = max.get();
                if(v > curMax) {
                    if(max.weakCompareAndSet(curMax, v)) break;
                } else {
                    break;
                }
            }
        }

        @Override
        public void sample(long value) {
            this.value.set(value);
            sum.addAndGet(value);
            count.incrementAndGet();
            updateMaximum(value);
            updateMinimum(value);
        }

        @Override
        public CounterValue get() {
            long c = count.get();
            long s = sum.get();
            long v = value.get();
            long n = min.get();
            long x = max.get();
            if(v < n) n = v;
            if(v > x) x = v;
            return new CounterValue(v, n, x, s, c);
        }
    }

    public static class ReentrantExclusiveCounter extends AtomicCounter {

        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock reentrantLock = readWriteLock.readLock();
        private Lock exclusiveLock = readWriteLock.writeLock();

        @Override
        public void sample(long value) {
            reentrantLock.lock();
            try {
                super.sample(value);
            } finally {
                reentrantLock.unlock();
            }
        }

        @Override
        public CounterValue get() {
            exclusiveLock.lock();
            try {
                return super.get();
            } finally {
                exclusiveLock.unlock();
            }
        }
    }

    public static class ManyWritesRareReadsCounter extends AtomicCounter {

        private ManyWritesOrRareReadsLock readWriteLock = new ManyWritesOrRareReadsLock();

        @Override
        public void sample(long value) {
            readWriteLock.writeLock();;
            try {
                super.sample(value);
            } finally {
                readWriteLock.writeUnlock();
            }
        }

        @Override
        public CounterValue get() {
            readWriteLock.readLock();
            try {
                return super.get();
            } finally {
                readWriteLock.readUnlock();
            }
        }
    }

    public static class ManyWritesOrRareReadsLock {

        private static final long READ_COUNT_INCREMENT = 0x00010000;
        private static final long READ_COUNT_BITMASK = 0xFFFF0000;
        private static final long WRITE_COUNT_BITMASK = 0x0000FFFF;


        private AtomicLong readWriteCounts = new AtomicLong();

        public void writeLock() {
            long readCounts = readWriteCounts.getAndIncrement() & READ_COUNT_BITMASK;
            boolean writeCountIncremented = true;
            while(readCounts != 0) {
                if(writeCountIncremented) {
                    readWriteCounts.decrementAndGet();
                    writeCountIncremented = false;
                }
                readCounts = readWriteCounts.get() & READ_COUNT_BITMASK;
                if(readCounts == 0) {
                    readCounts = readWriteCounts.getAndIncrement() & READ_COUNT_BITMASK;
                    writeCountIncremented = true;
                }
            };
        }

        public void writeUnlock() {
            readWriteCounts.decrementAndGet();
        }

        public void readLock() {
            long writeCounts = readWriteCounts.getAndAdd(READ_COUNT_INCREMENT) & WRITE_COUNT_BITMASK;
            while(writeCounts != 0) {
                writeCounts = readWriteCounts.get() & WRITE_COUNT_BITMASK;
            }
        }

        public void readUnlock() {
            readWriteCounts.addAndGet(-READ_COUNT_INCREMENT);
        }

        @Override
        public String toString() {
            long rwc = readWriteCounts.get();

            return "ManyWritesOrRareReadsLock{" +
                    "readWriteCounts=" + rwc +
                    ", readCounts = " + ((rwc & READ_COUNT_BITMASK) >> 16) +
                    ", writeCounts = " + (rwc & WRITE_COUNT_BITMASK) +
                    '}';
        }
    }
}
