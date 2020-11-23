package com.jsgygujun.code.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GuJun
 * @date 2020/11/23
 */
public class ExpCache {

    public static void main(String[] args) throws Exception {
        缓存测试案例();
    }

    private static Optional<String> getValue(String key) {
        System.out.println("耗时接口取数据！key: " + key);
        return Optional.of("value" + ThreadLocalRandom.current().nextInt(0, 10));
    }

    private static void 缓存测试案例() throws Exception {
        LoadingCache<String, Optional<String>> kvCache = CacheBuilder
                .newBuilder()
                .maximumSize(3) // 最大缓存数目
                .recordStats() // 统计缓存数据
                .expireAfterAccess(Duration.ofSeconds(5)) // 缓存清理策略
                .build(new CacheLoader<String, Optional<String>>() {
                    @Override
                    public Optional<String> load(String key) throws Exception {
                        return getValue(key); // 若未命中缓存则调用实际接口取数据, load方法不能返回null,否则在get时抛出异常。
                    }
                });
        for (int i = 0; i < 10; ++i) {
            System.out.println(kvCache.get("key"+ThreadLocalRandom.current().nextInt(0, 5)));
            Thread.sleep(1000);
        }
        System.out.println(kvCache.stats()); // 打印缓存的统计信息
    }
}
