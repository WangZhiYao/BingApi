package me.zhiyao.bing.ext

import me.zhiyao.bing.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 *
 * @author WangZhiYao
 * @date 2021/1/7
 */
fun BaseResponse.toRestResponse(status: HttpStatus): ResponseEntity<BaseResponse> {
    return ResponseEntity(this, status)
}