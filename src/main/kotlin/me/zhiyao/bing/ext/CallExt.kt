package me.zhiyao.bing.ext

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *
 * @author WangZhiYao
 * @date 2020/12/25
 */
suspend fun Call.await(): Response = suspendCoroutine { continuation ->
    enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            continuation.resume(response)
        }

        override fun onFailure(call: Call, ex: IOException) {
            continuation.resumeWithException(ex)
        }
    })
}