package com.lx.linwanandroid.mvp.presenter

import com.lx.linwanandroid.mvp.contract.ContentContract
import com.lx.linwanandroid.mvp.model.ContentModel


/**
 * Created by chenxz on 2018/6/10.
 */
class ContentPresenter : CommonPresenter<ContentContract.Model, ContentContract.View>(), ContentContract.Presenter {
    override fun getModel(): ContentContract.Model = ContentModel()
}