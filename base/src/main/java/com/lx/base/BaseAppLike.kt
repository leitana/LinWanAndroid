package com.lx.base

import android.content.Context

/**
 * @title：BaseAppLike
 * @projectName RouteModule
 * @description: 组件声明周期管理基类
 * @author linxiao
 * @data Created in 2020/05/15
 */
abstract class BaseAppLike {
    companion object{
        const val MAX_PRIORITY = 10;
        const val MIN_PRIORITY = 1;
        const val NOMAL_PRIORITY = 5;
    }

    /**
     * 返回组件优先级，优先级返回为1-10，默认优先级为5
     */
    fun getPriority() : Int{
        return NOMAL_PRIORITY
    }

    /**
     * 应用初始化
     */
    abstract fun onCreate(context: Context)

    abstract fun onTerminate()

}