package com.lx.linwanandroid.mvp.model

import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.ProjectArticle
import com.lx.linwanandroid.mvp.contract.SearchListContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：SearchListModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
class SearchListModel: CommonModel(), SearchListContract.Model {
    override fun queryBySearchKey(
        page: Int,
        key: String
    ): Observable<BaseResponse<ProjectArticle>> {
        return RetrofitHelper.service.queryBySearchKey(page, key)
    }
}