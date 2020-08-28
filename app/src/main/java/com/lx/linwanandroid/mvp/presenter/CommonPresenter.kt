package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.CommonContract
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：CommonPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
abstract class CommonPresenter<M: CommonContract.Model, V: CommonContract.View>
    : BasePresenter<V, M>(), CommonContract.Presenter<V>{

    override fun addCollectArticle(id: Int) {
        mModel?.addCollectArticle(id)?.mysubscribe(mModel, mView) {
            mView?.showCollectSuccess(true)
        }
    }

    override fun cancelCollectArticle(id: Int) {
        mModel?.cancelCollectArticle(id)?.mysubscribe(mModel, mView) {
            mView?.showCancelColletcSuccess(true)
        }
    }
}