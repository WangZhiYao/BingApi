package me.zhiyao.bing.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import me.zhiyao.bing.dao.model.BingImage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 *
 * @author WangZhiYao
 * @date 2021/1/7
 */
@Configuration
class RedisConfiguration(private val objectMapper: ObjectMapper) {

    @Bean
    fun bingImageRedisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, BingImage> {

        val redisTemplate = RedisTemplate<String, BingImage>()
        redisTemplate.setConnectionFactory(redisConnectionFactory)

        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(BingImage::class.java)
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper)

        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = jackson2JsonRedisSerializer
        redisTemplate.afterPropertiesSet()

        return redisTemplate
    }
}