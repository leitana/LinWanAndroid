package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.LoginContract
import com.lx.linwanandroid.mvp.model.LoginModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：LoginPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/28
 */
class LoginPresenter: BasePresenter<LoginContract.View, LoginContract.Model>(), LoginContract.Presenter {
    override fun getModel(): LoginContract.Model = LoginModel()

    override fun loginWanAndroid(username: String, password: String) {
        mModel?.loginWanAndroid(username, password)?.mysubscribe(mModel, mView) {
            mView?.loginSuccess(data = it.data)
        }
    }
}