package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.CollectionArticle
import io.reactivex.Observable

/**
 * @title：CollectContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/20
 */
interface CollectContract {
    interface View: IView{
        fun setCollectList(collects: CollectionArticle)//显示收藏列表
        fun showRemoveSuccess(success: Boolean)//移除收藏
        fun scrollToTop()
    }

    interface Presenter: IPresenter<View> {
        fun getCollectList(page: Int)

        fun removeCollect(id: Int, originId: Int)
    }

    interface Model: IModel {
        fun getCollectList(page: Int): Observable<BaseResponse<CollectionArticle>>

        fun removeCollect(id: Int, originId: Int): Observable<BaseResponse<Any>>
    }
}