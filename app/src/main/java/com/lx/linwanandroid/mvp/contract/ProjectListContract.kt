package com.lx.linwanandroid.mvp.contract

import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.ProjectArticle
import io.reactivex.Observable

/**
 * @title：ProjectListContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
interface ProjectListContract {
    interface View : CommonContract.View {

        fun scrollToTop()

        fun setProjectList(articles: ProjectArticle)

    }

    interface Presenter : CommonContract.Presenter<View> {

        fun requestProjectList(page: Int, cid: Int)

    }

    interface Model : CommonContract.Model {

        fun requestProjectList(page: Int, cid: Int): Observable<BaseResponse<ProjectArticle>>

    }
}