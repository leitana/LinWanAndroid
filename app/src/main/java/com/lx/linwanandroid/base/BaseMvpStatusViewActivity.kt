package com.lx.linwanandroid.base

import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.ext.showToast

/**
 * @title：BaseMvpStatusViewActivity
 * @projectName LinWanAndroid
 * @description: 融合了多状态View的mvp基类
 * @author linxiao
 * @data Created in 2020/06/08
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpStatusViewActivity<in V: IView, P: IPresenter<V>>: BaseStatusViewActivity(), IView {
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        if (mPresenter == null) {
            mPresenter = createPresenter()
        }
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
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