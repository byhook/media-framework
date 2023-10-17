package com.handy.logger

import java.util.concurrent.ConcurrentHashMap

/**
 * @author: handy
 * @date: 2022-04-28
 * @description:
 */
class LimitLogger(private val logTag: String, private val logDuration: Int = 5000) {

    /**
     * 限频日志
     */
    private val logTimestampMap: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

    /**
     * 日志限制频率
     * @param key
     * @param message
     */
    private fun logLimit(
        key: String,
        message: String,
        duration: Int? = null,
        onLogInvoke: ((message: String) -> Unit)) {
        val nowTimestamp = System.currentTimeMillis()
        var startTimestamp = logTimestampMap[key]
        startTimestamp = startTimestamp ?: 0
        val stepDuration = nowTimestamp - startTimestamp
        val tempDuration = duration ?: logDuration
        if (stepDuration >= tempDuration) {
            logTimestampMap[key] = nowTimestamp
            onLogInvoke.invoke(message)
        }
    }

    /**
     * 日志限制频率
     * @param key
     * @param message
     */
    open fun i(key: String, message: String, duration: Int? = null) {
        logLimit(key, message, duration) {
            Logger.i(logTag, message)
        }
    }

    /**
     * 日志限制频率
     * @param key
     * @param message
     */
    open fun d(key: String, message: String, duration: Int? = null) {
        logLimit(key, message, duration) {
            Logger.d(logTag, message)
        }
    }

    /**
     * 日志限制频率
     * @param key
     * @param message
     */
    open fun e(key: String, message: String, duration: Int? = null) {
        logLimit(key, message, duration) {
            Logger.e(logTag, message)
        }
    }

}