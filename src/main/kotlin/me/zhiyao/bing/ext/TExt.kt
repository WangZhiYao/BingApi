package me.zhiyao.bing.ext

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}