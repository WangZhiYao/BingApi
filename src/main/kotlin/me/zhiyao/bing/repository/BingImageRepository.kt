package me.zhiyao.bing.repository

import me.zhiyao.bing.constant.RedisKeyPrefix
import me.zhiyao.bing.dao.model.BingImage
import me.zhiyao.bing.dao.service.BingImageService
import me.zhiyao.bing.ext.logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.SerializationException
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

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

    fun putBingImage(bingImage: BingImage): Boolean = bingImageService.save(bingImage)

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
        var bingImage = bingImageRedisTemplate.opsForValue()
            .get("${RedisKeyPrefix.BING_IMAGE}_${localDate.format(DateTimeFormatter.BASIC_ISO_DATE)}")
        if (bingImage == null) {
            bingImage = bingImageService.getByDate(year, month, day)
        }
        return bingImage
    }

    fun flushCache() {
        val bingImageList = bingImageService.list()
        if (bingImageList.isNullOrEmpty()) {
            logger.error("data source is empty")
            return
        }

        val bingImageMap = bingImageList.stream()
            .collect(Collectors.toMap({ bingImage ->
                val localDate = LocalDate.of(bingImage.year, bingImage.month, bingImage.day)
                "${RedisKeyPrefix.BING_IMAGE}_${localDate.format(DateTimeFormatter.BASIC_ISO_DATE)}"
            }) { bingImage -> bingImage })

        bingImageRedisTemplate.connectionFactory?.connection?.flushDb()

        bingImageRedisTemplate.opsForValue().multiSet(bingImageMap)
    }
}