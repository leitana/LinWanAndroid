package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.UserInfoBody
import com.tencent.bugly.crashreport.biz.UserInfoBean
import io.reactivex.Observable
import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess

/**
 * @title：MainContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/19
 */
interface MainContract {
    interface View: IView {
        fun showLogoutSuccess(success: Boolean)
        fun showUserInfo(user: UserInfoBody)
    }

    interface Presenter: IPresenter<View> {
        fun logout()
        fun getUserInfo()
    }

    interface Model: IModel {
        fun logout(): Observable<BaseResponse<Any>>
        fun getUserInfo(): Observable<BaseResponse<UserInfoBody>>
    }
}