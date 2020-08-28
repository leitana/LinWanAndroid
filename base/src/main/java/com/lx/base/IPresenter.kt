package com.lx.base

/**
 * @title：IBasePresenter
 * @projectName RouteModule
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/14
 */
interface IPresenter<in V : IView> {
    fun attachView(mView: V)

    fun detachView()
}