package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.mvp.contract.CommonContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * @title：CommonModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
open class CommonModel : CommonContract.Model, BaseModel(){
    override fun addCollectArticle(id: Int): Observable<BaseResponse<Any>> {
        return RetrofitHelper.service.addCollectArticle(id)
    }

    override fun cancelCollectArticle(id: Int): Observable<BaseResponse<Any>> {
        return RetrofitHelper.service.cancelCollectArticle(id)
    }

}