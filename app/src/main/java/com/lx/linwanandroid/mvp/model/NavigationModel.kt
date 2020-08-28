package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.NavigationBean
import com.lx.linwanandroid.mvp.contract.NavigationContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：NavigationModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/26
 */
class NavigationModel: BaseModel(), NavigationContract.Model {
    override fun requestNavigation(): Observable<BaseResponse<List<NavigationBean>>> {
        return RetrofitHelper.service.getNavigation()
    }
}