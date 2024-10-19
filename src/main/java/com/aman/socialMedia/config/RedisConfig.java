//package com.aman.socialMedia.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//
//        //connection factory
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        //key serializer
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//
//        //hashkey serializer
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//
//
//        //value serializer
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        return redisTemplate;
//
//    }
//}
