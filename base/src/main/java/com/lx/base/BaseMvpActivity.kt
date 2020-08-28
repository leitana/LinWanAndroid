package com.lx.base

/**
 * @title：BaseMVPActivity
 * @projectName RouteModule
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/15
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView{
    protected var mPresenter : P? = null

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
        this.mPresenter = null
    }

}