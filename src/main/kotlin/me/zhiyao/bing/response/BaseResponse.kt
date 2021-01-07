package me.zhiyao.bing.response

import me.zhiyao.bing.constant.ResponseCode

/**
 *
 * @author WangZhiYao
 * @date 2021/1/6
 */
sealed class BaseResponse(
    open val code: Int,
) {

    data class Success<T>(val data: T?) : BaseResponse(ResponseCode.SUCCESS)

    data class Error(override val code: Int) : BaseResponse(code)
}
