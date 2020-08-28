package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.Banner
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.HomeArticle
import com.lx.linwanandroid.mvp.contract.HomeContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：HomeModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
class HomeModel: CommonModel(), HomeContract.Model {
    override fun requestBanner(): Observable<BaseResponse<List<Banner>>> {
        return RetrofitHelper.service.getBanners()
    }

    override fun requestTopArticles(): Observable<BaseResponse<MutableList<HomeArticle.DatasBean>>> {
        return RetrofitHelper.service.getTopArticles()
    }

    override fun requestArticles(num: Int): Observable<BaseResponse<HomeArticle>> {
        return RetrofitHelper.service.getArticles(num)
    }
}