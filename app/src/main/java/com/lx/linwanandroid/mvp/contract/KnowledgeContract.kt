package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.KnowledgeSysArticle
import io.reactivex.Observable

/**
 * @title：KnowledgeContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/24
 */
interface KnowledgeContract {
    interface View: CommonContract.View {
        fun showKnowledgeList(article: KnowledgeSysArticle)

        fun scrollToTop()
    }

    interface Presenter: CommonContract.Presenter<View>{
        fun requestKnowledgeList(page: Int, cid: Int)
    }

    interface Model: CommonContract.Model{
        fun requestKnowledgeList(page: Int, cid: Int): Observable<BaseResponse<KnowledgeSysArticle>>
    }
}