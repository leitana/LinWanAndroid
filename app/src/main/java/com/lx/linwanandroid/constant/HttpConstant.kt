package com.lx.linwanandroid.constant

import com.lx.linwanandroid.utils.Preference

/**
 * @title：HttpConstant
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/16
 */
object HttpConstant {
    const val DEFAULT_TIMEOUT: Long = 15

    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"

    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"
    const val COIN_WEBSITE = "lg/coin"

    /**
     * 整合cookie为唯一字符串
     */
    fun encodeCookie(cookies: List<String>): String{
        val sb = StringBuilder()
        val set = HashSet<String>()

        cookies.map {
            cookies -> cookies.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }.forEach {
            it.filterNot { set.contains(it) }.forEach{ set.add(it) }
        }
        //Java写法
//        for (cookie in cookies) {
//            val arr = cookie.split(";".toRegex()).toTypedArray()
//            for (s in arr) {
//                if (set.contains(s)) continue
//                set.add(s)
//            }
//        }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    /**
     * 保存cookie到本地
     */
    fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by Preference(url, cookies)
        spUrl = cookies

        domain ?: return
        var spDomain: String by Preference(domain, cookies)
        spDomain = cookies
    }
}