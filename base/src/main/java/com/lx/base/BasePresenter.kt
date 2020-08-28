package com.lx.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @title：BasePresenter
 * @projectName Base
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/14
 */
abstract class BasePresenter<V : IView, M : IModel> : IPresenter<V>, LifecycleObserver {
    protected var mView : V? = null
    protected var mModel : M? = null

    protected abstract fun getModel() : M

    override fun attachView(v: V){
        mView = v
        mModel = getModel()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleOwner) {
                (mView as LifecycleOwner).lifecycle.addObserver(mModel as LifecycleObserver)
            }
        }
    }

    override fun detachView() {
        mModel?.onDetach()
        mView = null
        mModel = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        // detachView()
        owner.lifecycle.removeObserver(this)
    }

}