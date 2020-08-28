package com.lx.linwanandroid.net.interceptor

import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.utils.NetWorkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @title：CacheInterceptor
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/08
 */
class CacheInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetWorkUtil.isNetworkAvailable(App.context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//没网的时候用缓存
                    .build()
        }
        val response = chain.proceed(request)
        if (NetWorkUtil.isNetworkAvailable(App.context)) {
            // 有网络时, 不缓存, 最大保存时长为0
            val maxAge = 0
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")  // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build()
        } else {
            // 无网络时，设置超时为1周  只对get有用,post没有缓冲
            val maxStale = 60 * 60 * 24 * 7
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        return response
    }
}