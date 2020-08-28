package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.WXchapters
import io.reactivex.Observable

/**
 * @title：WeChartContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/25
 */
interface WeChatContract {
    interface View: IView{
        fun scrollToTop()

        fun showWXChapters(chapters: MutableList<WXchapters>)
    }

    interface Presenter: IPresenter<View>{
        fun requestWXChapters()
    }

    interface Model: IModel{
        fun requestWXChapters(): Observable<BaseResponse<MutableList<WXchapters>>>
    }
}