package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.Login
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * @title：LoginContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/28
 */
interface LoginContract {

    interface View: IView {
        fun loginSuccess(data: Login)

        fun loginFail()
    }

    interface Presenter: IPresenter<View> {
        fun loginWanAndroid(username: String, password: String)
    }

    interface Model: IModel {
        fun loginWanAndroid(username: String, password: String): Observable<BaseResponse<Login>>
    }
}