package com.lx.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.security.acl.Owner

/**
 * @title：BaseModel
 * @projectName RouteModule
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/15
 */
abstract class BaseModel : IModel, LifecycleObserver{
    private var mCompositeDisposable: CompositeDisposable? = null

    override fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(disposable) }
    }

    override fun onDetach() {
        unDispose()
    }

    private fun unDispose(){
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestory(owner: LifecycleOwner){
        owner.lifecycle.removeObserver(this)
    }
}