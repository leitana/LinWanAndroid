package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.CollectContract
import com.lx.linwanandroid.mvp.model.CollectModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：CollectPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/20
 */
class CollectPresenter: BasePresenter<CollectContract.View, CollectContract.Model>(), CollectContract.Presenter {
    override fun getModel(): CollectContract.Model = CollectModel()

    override fun getCollectList(page: Int) {
        mModel?.getCollectList(page)?.mysubscribe(mModel, mView) {
            mView?.setCollectList(it.data)
        }
    }

    override fun removeCollect(id: Int, originId: Int) {
        mModel?.removeCollect(id, originId)?.mysubscribe(mModel, mView) {
            mView?.showRemoveSuccess(true)
        }
    }
}