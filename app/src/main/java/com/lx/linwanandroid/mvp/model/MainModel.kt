package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.UserInfoBody
import com.lx.linwanandroid.mvp.contract.MainContract
import com.lx.linwanandroid.net.RetrofitHelper
import com.lx.linwanandroid.rxutils.mysubscribe
import io.reactivex.Observable

/**
 * @title：MainModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/19
 */
class MainModel: BaseModel(),MainContract.Model {
    override fun logout(): Observable<BaseResponse<Any>> {
        return RetrofitHelper.service.logout()
    }

    override fun getUserInfo(): Observable<BaseResponse<UserInfoBody>> {
        return RetrofitHelper.service.getUserInfo()
    }
}