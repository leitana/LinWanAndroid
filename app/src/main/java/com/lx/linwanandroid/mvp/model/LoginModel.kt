package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.Login
import com.lx.linwanandroid.mvp.contract.LoginContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：LoginModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/28
 */
class LoginModel: BaseModel(), LoginContract.Model {
    override fun loginWanAndroid(
        username: String,
        password: String
    ): Observable<BaseResponse<Login>> {
        return RetrofitHelper.service.login(username, password)
    }
}