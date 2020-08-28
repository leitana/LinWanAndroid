package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.NavigationContract
import com.lx.linwanandroid.mvp.model.NavigationModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：NavigaitonPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/26
 */
class NavigationPresenter: BasePresenter<NavigationContract.View, NavigationContract.Model>(),
    NavigationContract.Presenter{
    override fun getModel(): NavigationContract.Model = NavigationModel()

    override fun requestNavigationData() {
        mModel?.requestNavigation()?.mysubscribe(mModel, mView){
            mView?.showNavigationData(it.data)
        }
    }
}