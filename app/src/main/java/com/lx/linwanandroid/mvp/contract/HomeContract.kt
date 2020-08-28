package com.lx.linwanandroid.mvp.contract

import com.lx.base.IView
import com.lx.linwanandroid.bean.Banner
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.HomeArticle
import io.reactivex.Observable

/**
 * @title：HomeContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
interface HomeContract {

    interface View: CommonContract.View {
        fun scrollToUp()

        fun setBanner(banners: List<Banner>)

        fun setArticles(article: HomeArticle)
    }

    interface Presenter: CommonContract.Presenter<View> {
        fun requestBanner()

        fun requestHomeData()

        fun requestArticles(num: Int)
    }

    interface Model: CommonContract.Model{
        fun requestBanner(): Observable<BaseResponse<List<Banner>>>

        fun requestTopArticles(): Observable<BaseResponse<MutableList<HomeArticle.DatasBean>>>

        fun requestArticles(num: Int): Observable<BaseResponse<HomeArticle>>
    }
}