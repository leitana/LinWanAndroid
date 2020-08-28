package com.lx.linwanandroid.mvp.contract

import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.ProjectArticle
import io.reactivex.Observable

interface SearchListContract {

    interface View : CommonContract.View {

        fun showArticles(articles: ProjectArticle)

        fun scrollToTop()

    }

    interface Presenter : CommonContract.Presenter<View> {

        fun queryBySearchKey(page: Int, key: String)

    }

    interface Model : CommonContract.Model {

        fun queryBySearchKey(page: Int, key: String): Observable<BaseResponse<ProjectArticle>>

    }

}