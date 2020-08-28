package com.lx.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @title：IBaseModel
 * @projectName RouteModule
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/14
 */
interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()
}