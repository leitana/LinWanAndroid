package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.bean.KnowledgeSysArticle
import com.lx.linwanandroid.mvp.contract.KnowledgeContract
import com.lx.linwanandroid.mvp.model.KnowledgeModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：KnowledgePresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/24
 */
class KnowledgePresenter: CommonPresenter<KnowledgeContract.Model, KnowledgeContract.View>(),
    KnowledgeContract.Presenter{
    override fun getModel(): KnowledgeContract.Model = KnowledgeModel()

    override fun requestKnowledgeList(page: Int, cid: Int) {
        mModel?.requestKnowledgeList(page, cid)?.mysubscribe(mModel, mView){
            mView?.showKnowledgeList(it.data)
        }
    }
}