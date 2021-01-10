package me.zhiyao.bing.repository

import me.zhiyao.bing.constant.ImageHost
import me.zhiyao.bing.constant.RedisKey
import me.zhiyao.bing.dao.model.BingImage
import me.zhiyao.bing.dao.service.BingImageService
import me.zhiyao.bing.ext.logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.SerializationException
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors
import javax.annotation.PostConstruct

/**
 *
 * @author WangZhiYao
 * @date 2021/1/7
 */
@Component
class BingImageRepository(
    private val bingImageService: BingImageService,
    private val bingImageRedisTemplate: RedisTemplate<String, BingImage>
) {

    private val logger = logger()

    @PostConstruct
    fun init() {
        initCache()
    }

    fun initCache() {
        bingImageRedisTemplate.connectionFactory?.connection?.flushDb()

        val bingImageList = bingImageService.list()
        val bingImageMap = bingImageList.stream()
            .collect(Collectors.toMap({ bingImage ->
                val localDate = LocalDate.of(bingImage.year, bingImage.month, bingImage.day)
                "${RedisKey.PREFIX_BING_IMAGE}_${localDate.format(DateTimeFormatter.BASIC_ISO_DATE)}"
            }) { bingImage ->
                bingImage.image = ImageHost.BASE_URL + bingImage.image
                bingImage
            })

        bingImageRedisTemplate.opsForValue().multiSet(bingImageMap)
    }

    fun putBingImage(bingImage: BingImage): Boolean {
        val success = bingImageService.save(bingImage)

        val localDate = LocalDate.of(bingImage.year, bingImage.month, bingImage.day)
        val cacheKey = "${RedisKey.PREFIX_BING_IMAGE}_${localDate.format(DateTimeFormatter.BASIC_ISO_DATE)}"
        bingImage.image = ImageHost.BASE_URL + bingImage.image
        bingImageRedisTemplate.opsForValue().set(cacheKey, bingImage)

        return success
    }

    fun getRandom(): BingImage? {
        val key = try {
            bingImageRedisTemplate.randomKey()
        } catch (ex: SerializationException) {
            logger.error("redis is empty so can not get random key")
            null
        }

        return key?.let { bingImageRedisTemplate.opsForValue().get(it) }
    }

    fun getByDate(year: Int, month: Int, day: Int): BingImage? {
        val localDate = LocalDate.of(year, month, day)
        val cacheKey = "${RedisKey.PREFIX_BING_IMAGE}_${localDate.format(DateTimeFormatter.BASIC_ISO_DATE)}"
        return if (bingImageRedisTemplate.hasKey(cacheKey)) {
            bingImageRedisTemplate.opsForValue().get(cacheKey)
        } else {
            bingImageService.getByDate(year, month, day)
        }
    }
}