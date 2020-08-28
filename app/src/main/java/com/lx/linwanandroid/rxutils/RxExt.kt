package com.lx.linwanandroid.rxutils

import com.lx.base.IModel
import com.lx.base.IView
import com.lx.linwanandroid.R
import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.bean.BaseBean
import com.lx.linwanandroid.net.exception.ApiStatus
import com.lx.linwanandroid.net.RetryWithDelay
import com.lx.linwanandroid.net.exception.ExceptionHandle
import com.lx.linwanandroid.utils.NetWorkUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @title：RxExt
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/22
 */
fun <T: BaseBean>Observable<T>.mysubscribe(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit) {
        this.compose(RxSchedulerHelper.io_main())
            .retryWhen(RetryWithDelay())
            .subscribe(object : Observer<T>{
                override fun onComplete() {
                    view?.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    if (isShowLoading) view?.showLoading()
                    model?.addDisposable(d)
                    if (!NetWorkUtil.isNetworkAvailable(App.instance)) {
                        view?.showDefaultMsg(App.instance.resources.getString(R.string.no_network))
                        onComplete()
                    }

                }

                override fun onNext(t: T) {
                    when {
                        t.errorCode == ApiStatus.SUCCESS -> onSuccess.invoke(t)
                        t.errorCode == ApiStatus.TOKEN_INVALID -> {
                            //token过期，重新登录
                        }
                        else -> view?.showDefaultMsg(t.errorMsg)
                    }
                }

                override fun onError(e: Throwable) {
                    view?.hideLoading()
                    view?.showError(ExceptionHandle.handleException(e))
                }

            })
}

fun <T: BaseBean>Observable<T>.mysubscribe(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: ((T) -> Unit)? = null): Disposable {
    if (isShowLoading) view?.showLoading()
    return this.compose(RxSchedulerHelper.io_main())
        .retryWhen(RetryWithDelay())
        .subscribe({
            when {
                it.errorCode == ApiStatus.SUCCESS -> onSuccess.invoke(it)
                it.errorCode == ApiStatus.TOKEN_INVALID -> {
                    // Token 过期，重新登录
                }
                else -> {
                    if (onError != null) {
                        onError.invoke(it)
                    } else {
                        if (it.errorMsg.isNotEmpty())
                            view?.showDefaultMsg(it.errorMsg)
                    }
                }
            }
            view?.hideLoading()
        }, {
            view?.hideLoading()
            view?.showError(ExceptionHandle.handleException(it))
        })
}