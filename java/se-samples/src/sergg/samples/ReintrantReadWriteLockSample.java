package sergg.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReintrantReadWriteLockSample {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Instant lastUpdated = Instant.MIN;
    private static String lastManagerDoc;
    private static final Logger log = LoggerFactory.getLogger(ReintrantReadWriteLockSample.class);

    public static void main(String[] args) {
        getManagerDoc1();
//        executorService.submit(() -> {
//            try {
//                lock.readLock().lock();
//                if (isOutdated()) {
//                    try {
//                        lock.writeLock().lock();
//                        System.out.println("!!!");
//                        // all stuff
//                    } finally {
//                        lock.writeLock().unlock();
//                    }
//                }
//            } finally {
//                lock.readLock().unlock();
//            }
//        });
//        executorService.shutdown();
    }

    private static boolean isOutdated() {
        return true;
    }

    private static Optional<String> getManagerDoc() {
        String managementDoc = null;
        lock.readLock().lock();
        try {
            if (isOutdated()) {
                try {
                    lock.readLock().unlock();
                    lock.writeLock().lock();
                    // might be updated from another thread
                    System.err.println("Reloading MangerDoc");
                    generateException();
                    lock.readLock().lock();
                } finally {
                    lock.writeLock().unlock();
                }
            } else {
                managementDoc = "ku";
            }
        } finally {
            lock.readLock().unlock();
        }
        return Optional.ofNullable(managementDoc);
    }

    private static void generateException() {
        throw new RuntimeException();
    }

    private static Optional<String> getManagerDoc1() {
        String managementDoc = null;
        lock.readLock().lock();
        try {
            if (isOutdated()) {
                try {
                    lock.readLock().unlock();
                    lock.writeLock().lock();
                    // might be updated from another thread
                    if (isOutdated()) {
                        log.info("Reloading MangerDoc");
                        generateException();
                        lastUpdated = Instant.now();
                        lastManagerDoc = managementDoc;
                    } else {
                        managementDoc = lastManagerDoc;
                    }
                    lock.readLock().lock();
                } catch (Throwable e) {
                    log.error("Bucket error ", e);
                    lock.readLock().lock();
                } finally {
                    lock.writeLock().unlock();
                }
            } else {
                managementDoc = lastManagerDoc;
            }
        } catch (Throwable e) {
            log.error("Bucket error ", e);
            lock.readLock().lock();
        } finally {
            lock.readLock().unlock();
        }
        return Optional.ofNullable(managementDoc);
    }

}
