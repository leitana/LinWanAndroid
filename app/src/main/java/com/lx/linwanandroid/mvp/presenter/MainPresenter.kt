package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.MainContract
import com.lx.linwanandroid.mvp.model.MainModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：MainPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/19
 */
class MainPresenter: BasePresenter<MainContract.View, MainContract.Model>(), MainContract.Presenter {
    override fun getModel(): MainContract.Model = MainModel()

    override fun logout() {
        mModel?.logout()?.mysubscribe(mModel, mView){
            mView?.showLogoutSuccess(true)
        }
    }

    override fun getUserInfo() {
        mModel?.getUserInfo()?.mysubscribe(mModel, mView, false) {
            mView?.showUserInfo(it.data)
        }
    }
}