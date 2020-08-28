package com.lx.linwanandroid.base

import android.view.View
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.ext.showToast

/**
 * @title：BaseMvpStatusViewFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/08
 */
@Suppress("UNCHECKED_CAST")

abstract class BaseMvpStatusViewFragment<in V: IView, P: IPresenter<V>>: BaseStatusViewFragment(), IView {
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        if (mPresenter == null) {
            mPresenter = createPresenter()
        }
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showError(msg: String) {
        showToast(msg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }
}