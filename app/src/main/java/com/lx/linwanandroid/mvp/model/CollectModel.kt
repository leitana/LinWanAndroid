package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.CollectionArticle
import com.lx.linwanandroid.mvp.contract.CollectContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：CollectModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/20
 */
class CollectModel: BaseModel(),CollectContract.Model {
    override fun getCollectList(page: Int): Observable<BaseResponse<CollectionArticle>> {
        return RetrofitHelper.service.getCollectList(page)
    }

    override fun removeCollect(id: Int, originId: Int): Observable<BaseResponse<Any>> {
        return RetrofitHelper.service.removeCollectArticle(id, originId)
    }
}