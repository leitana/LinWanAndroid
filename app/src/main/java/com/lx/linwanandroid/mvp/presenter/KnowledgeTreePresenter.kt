package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.KnowledgeTreeContract
import com.lx.linwanandroid.mvp.model.KnowledgeTreeModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：KnowledgeTreePresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/21
 */
class KnowledgeTreePresenter: BasePresenter<KnowledgeTreeContract.View, KnowledgeTreeContract.Model>(),
    KnowledgeTreeContract.Presenter{
    override fun getModel(): KnowledgeTreeContract.Model = KnowledgeTreeModel()

    override fun requestKnowledgeTree() {
        mModel?.requestKnowledgeTree()?.mysubscribe(mModel,mView){
            mView?.showKnowledgeTree(it.data)
        }
    }
}