package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.WeChatContract
import com.lx.linwanandroid.mvp.model.WXChaptersModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：WXChaptersPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/25
 */
class WXChaptersPresenter: BasePresenter<WeChatContract.View, WeChatContract.Model>(),
    WeChatContract.Presenter{
    override fun getModel(): WeChatContract.Model = WXChaptersModel()

    override fun requestWXChapters() {
        mModel?.requestWXChapters()?.mysubscribe(mModel, mView){
            mView?.showWXChapters(it.data)
        }
    }
}