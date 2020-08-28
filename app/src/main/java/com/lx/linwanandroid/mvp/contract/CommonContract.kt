package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import io.reactivex.Observable

/**
 * @title：CommonContract
 * @projectName LinWanAndroid
 * @description: 收藏contract
 * @author linxiao
 * @data Created in 2020/06/29
 */
interface CommonContract {

    interface View: IView {
        //收藏
        fun showCollectSuccess(success: Boolean)
        //取消收藏
        fun showCancelColletcSuccess(success: Boolean)
    }

    interface Presenter<V: View>: IPresenter<V> {
        fun addCollectArticle(id: Int)

        fun cancelCollectArticle(id: Int)
    }

    interface Model: IModel {
        fun addCollectArticle(id: Int): Observable<BaseResponse<Any>>

        fun cancelCollectArticle(id: Int): Observable<BaseResponse<Any>>
    }
}