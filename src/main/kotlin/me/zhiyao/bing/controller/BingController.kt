package me.zhiyao.bing.controller

import me.zhiyao.bing.constant.Constants
import me.zhiyao.bing.dao.service.BingImageService
import me.zhiyao.bing.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 *
 * @author WangZhiYao
 * @date 2021/1/6
 */
@RestController
@RequestMapping("/v1")
class BingController(private val bingImageService: BingImageService) {

    @GetMapping("/random")
    suspend fun random(): BaseResponse {
        return BaseResponse.Success(bingImageService.random())
    }

    @GetMapping("/getByDate")
    suspend fun getByDate(date: String?): BaseResponse {
        if (date.isNullOrBlank()) {
            return BaseResponse.Error(Constants.CODE_DATE_IS_NULL_OR_BLANK)
        }

        val localDate = try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
        } catch (ex: DateTimeParseException) {
            return BaseResponse.Error(Constants.CODE_DATE_FORMAT_INVALID)
        }

        val bingImage = bingImageService.getByDate(localDate.year, localDate.monthValue, localDate.dayOfMonth)

        return if (bingImage != null) {
            BaseResponse.Success(bingImage)
        } else {
            BaseResponse.Error(Constants.CODE_DATE_OUT_OF_RANGE)
        }
    }
}