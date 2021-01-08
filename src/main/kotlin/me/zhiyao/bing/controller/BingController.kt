package me.zhiyao.bing.controller

import me.zhiyao.bing.constant.ResponseCode
import me.zhiyao.bing.ext.toRestResponse
import me.zhiyao.bing.repository.BingImageRepository
import me.zhiyao.bing.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
@RequestMapping("/v1", produces = ["application/json;charset=utf-8"])
class BingController(private val bingImageRepository: BingImageRepository) {

    @GetMapping("/random")
    suspend fun random(): ResponseEntity<BaseResponse> {
        val bingImage = bingImageRepository.getRandom()
        return if (bingImage != null) {
            BaseResponse.Success(bingImage).toRestResponse(HttpStatus.OK)
        } else {
            BaseResponse.Error(ResponseCode.DATA_EMPTY).toRestResponse(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getByDate")
    suspend fun getByDate(date: String?): ResponseEntity<BaseResponse> {
        if (date.isNullOrBlank()) {
            return BaseResponse.Error(ResponseCode.DATE_IS_NULL_OR_BLANK).toRestResponse(HttpStatus.BAD_REQUEST)
        }

        val localDate = try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
        } catch (ex: DateTimeParseException) {
            return BaseResponse.Error(ResponseCode.DATE_FORMAT_INVALID).toRestResponse(HttpStatus.BAD_REQUEST)
        }

        val bingImage = bingImageRepository.getByDate(localDate.year, localDate.monthValue, localDate.dayOfMonth)

        return if (bingImage != null) {
            BaseResponse.Success(bingImage).toRestResponse(HttpStatus.OK)
        } else {
            BaseResponse.Error(ResponseCode.DATE_OUT_OF_RANGE).toRestResponse(HttpStatus.NOT_FOUND)
        }
    }
}