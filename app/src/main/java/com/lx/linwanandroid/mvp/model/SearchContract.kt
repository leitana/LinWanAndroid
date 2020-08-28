package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.SearchHotBean
import com.lx.linwanandroid.mvp.contract.SearchContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：SearchContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
class SearchModel: BaseModel(), SearchContract.Model {
    override fun getHotSearchData(): Observable<BaseResponse<MutableList<SearchHotBean>>> {
        return RetrofitHelper.service.getSearchHotBean()
    }
}