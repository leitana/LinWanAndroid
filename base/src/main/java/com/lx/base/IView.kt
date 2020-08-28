package com.lx.base

/**
 * @title：IBaseView
 * @projectName RouteModule
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/14
 */
interface IView {

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示信息（默认样式）
     */
    fun showDefaultMsg(msg: String)

    /**
     * 显示错误信息
     */
    fun showError(msg: String)

    /**
     * 显示信息
     */
    fun showMsg(msg: String)
}