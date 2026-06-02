package com.mealgo.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory) {

        // 預設快取設定
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()

                // 不快取 null 值
                .disableCachingNullValues()

                // 使用 JSON 格式序列化快取資料
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(
                                        new GenericJackson2JsonRedisSerializer()));

        // 各快取名稱的獨立設定
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // 店家列表快取 30 分鐘
        cacheConfigurations.put(
                "shops",
                defaultConfig.entryTtl(Duration.ofMinutes(30)));

        // 單一店家快取 30 分鐘
        cacheConfigurations.put(
                "shop",
                defaultConfig.entryTtl(Duration.ofMinutes(30)));

        // 商品列表快取 15 分鐘
        cacheConfigurations.put(
                "products",
                defaultConfig.entryTtl(Duration.ofMinutes(15)));

        // 單一商品快取 15 分鐘
        cacheConfigurations.put(
                "product",
                defaultConfig.entryTtl(Duration.ofMinutes(15)));

        return RedisCacheManager.builder(redisConnectionFactory)

                // 其他未指定的快取預設保存 10 分鐘
                .cacheDefaults(
                        defaultConfig.entryTtl(Duration.ofMinutes(10)))

                // 套用自訂快取設定
                .withInitialCacheConfigurations(cacheConfigurations)

                .build();
    }
}