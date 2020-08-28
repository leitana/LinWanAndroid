package com.lx.linwanandroid.net

import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * @title：RetryWithDelay
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/22
 */
class RetryWithDelay : Function<Observable<out Throwable>, Observable<*>>{

    private var maxRetryCount = 3 // 可重试次数
    private var retryDelayMillis: Long = 3000 // 重试等待时间

    private var retryCount = 0

    constructor() {}

    constructor(retryDelayMillis: Long) {
        this.retryDelayMillis = retryDelayMillis
    }

    constructor(maxRetryCount: Int, retryDelayMillis: Long) {
        this.maxRetryCount = maxRetryCount
        this.retryDelayMillis  = retryDelayMillis
    }

    override fun apply(observable: Observable<out Throwable>): Observable<*> {
        return observable
            .flatMap {
                if (++retryCount <= maxRetryCount) {
                    Observable.timer(
                        retryDelayMillis,
                        TimeUnit.SECONDS
                    )
                }
                // Max retries hit. Just pass the error along.
                else Observable.error<Any>(it)
            }
    }

}