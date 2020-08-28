package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.NavigationBean
import io.reactivex.Observable

/**
 * @title：NavigationContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/26
 */
interface NavigationContract {
    interface View: IView {
        fun showNavigationData(list: List<NavigationBean>)
    }

    interface Presenter: IPresenter<View> {
        fun requestNavigationData()
    }

    interface Model: IModel {
        fun requestNavigation(): Observable<BaseResponse<List<NavigationBean>>>
    }
}