package me.zhiyao.bing.crawler

import com.fasterxml.jackson.databind.ObjectMapper
import me.zhiyao.bing.crawler.model.HPImageArchive
import me.zhiyao.bing.crawler.model.Image
import me.zhiyao.bing.dao.model.BingImage
import me.zhiyao.bing.ext.await
import me.zhiyao.bing.ext.logger
import me.zhiyao.bing.repository.BingImageRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
@Service
class BingCrawler(
    private val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper,
    private val bingImageRepository: BingImageRepository
) {

    private val logger = logger()

    companion object {
        private const val BASE_URL_BING = "https://cn.bing.com"
        private val bingDailyImageRequest = Request.Builder()
            .get()
            .url("$BASE_URL_BING/HPImageArchive.aspx?format=js&idx=0&n=1")
            .build()
    }

    suspend fun getHPImageArchive(): Boolean {
        val response = try {
            okHttpClient.newCall(bingDailyImageRequest).await()
        } catch (ex: IOException) {
            logger.error("get bing image failed", ex)
            return false
        }

        if (!response.isSuccessful) {
            logger.error("get bing image failed: HTTP ${response.code()} ${response.message()}")
            return false
        }

        val hpImageArchive = convertResponse(response) ?: return false
        val image = validateImage(hpImageArchive.images) ?: return false

        val date = LocalDate.parse(image.endDate, DateTimeFormatter.BASIC_ISO_DATE)
        val imageName = image.url!!.split("&")[0].replace("/th?id=OHR.", "")

        return bingImageRepository.putBingImage(
            BingImage(
                null,
                date.year,
                date.monthValue,
                date.dayOfMonth,
                image.copyright,
                image.title.takeIf { !it.isNullOrBlank() },
                image.desc.takeIf { !it.isNullOrBlank() },
                null,
                imageName
            )
        )
    }

    private fun convertResponse(response: Response): HPImageArchive? {
        val responseStr = try {
            response.body()?.string()
        } catch (ex: IOException) {
            logger.error("get bing image failed, get response string error", ex)
            return null
        }

        if (responseStr.isNullOrBlank()) {
            logger.error("get bing image failed, response string is null or blank")
            return null
        }

        val hpImageArchive = try {
            objectMapper.readValue(responseStr, HPImageArchive::class.java)
        } catch (ex: IOException) {
            logger.error("get bing image failed, parse response to json error", ex)
            return null
        }

        if (hpImageArchive == null || hpImageArchive.images.isNullOrEmpty()) {
            logger.error("get bing image failed, response is null")
            return null
        }

        return hpImageArchive
    }

    private fun validateImage(images: List<Image>?): Image? {
        if (images.isNullOrEmpty()) {
            logger.error("images is null or empty")
            return null
        }

        val image = images[0]

        if (image.endDate.isNullOrBlank()) {
            logger.error("image end date is null or blank")
            return null
        }

        if (image.url.isNullOrBlank()) {
            logger.error("image url is null or blank")
            return null
        }

        return image
    }
}
