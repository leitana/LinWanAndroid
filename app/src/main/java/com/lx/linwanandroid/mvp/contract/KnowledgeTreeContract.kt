package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.KnowledgeSystem
import io.reactivex.Observable

/**
 * @title：KnowledgeTreeContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/21
 */
interface KnowledgeTreeContract {
    interface View: IView{
        fun scrollToTop()

        fun showKnowledgeTree(list: MutableList<KnowledgeSystem>)
    }

    interface Presenter: IPresenter<View>{
        fun requestKnowledgeTree()
    }

    interface Model: IModel{
        fun requestKnowledgeTree(): Observable<BaseResponse<MutableList<KnowledgeSystem>>>
    }
}