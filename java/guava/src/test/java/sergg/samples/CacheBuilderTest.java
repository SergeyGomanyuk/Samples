package sergg.samples;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * https://www.baeldung.com/guava-cache
 *
 * @version $Revision:$
 */
public class CacheBuilderTest {
    @Test
    public void whenEntryLiveTimeExpire_thenEviction()
            throws InterruptedException {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MILLISECONDS)
                .build(loader);

        cache.getUnchecked("hello");
        assertEquals(1, cache.size());
        Thread.sleep(300);
        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }

    @Test
    public void whenCacheMiss_thenValueIsComputed() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder().build(loader);

        assertEquals(0, cache.size());
        assertEquals("HELLO", cache.getUnchecked("hello"));
        assertEquals(1, cache.size());
    }

    @Test
    public void cacheAsMap() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String s) throws Exception {
                                return "<null>";
                            }
                });

        System.out.println("cache.get('key')=" + cache.getUnchecked("key"));
        cache.put("key", "value");
        System.out.println("cache.get('key')=" + cache.getUnchecked("key"));
        Thread.sleep(1500);
        System.out.println("cache.get('key')=" + cache.getUnchecked("key"));
        cache.put("key", "value");
        System.out.println("cache.get('key')=" + cache.getUnchecked("key"));
        cache.invalidate("key");
        System.out.println("cache.get('key')=" + cache.getUnchecked("key"));
        System.out.println();

        ConcurrentMap<String, String> stringStringConcurrentMap = cache.asMap();
        System.out.println("cache.asMap().get('key')=" + stringStringConcurrentMap.get("key"));
        stringStringConcurrentMap.put("key", "value");
        System.out.println("cache.asMap().get('key')=" + stringStringConcurrentMap.get("key"));
        Thread.sleep(1500);
        System.out.println("cache.asMap().get('key')=" + stringStringConcurrentMap.get("key"));
        stringStringConcurrentMap.remove("key");
        System.out.println("cache.asMap().get('key')=" + stringStringConcurrentMap.get("key"));
    }
}
