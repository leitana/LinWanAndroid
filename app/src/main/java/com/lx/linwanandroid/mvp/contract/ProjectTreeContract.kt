package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.ProjectTreeBean
import io.reactivex.Observable

/**
 * @title：ProjectTreeContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
interface ProjectTreeContract {
    interface View : IView {
        fun scrollToTop()

        fun showProjectTree(list: List<ProjectTreeBean>)
    }

    interface Presenter: IPresenter<View> {
        fun requestProjectTree()
    }

    interface Model: IModel{
        fun requestProjectTree(): Observable<BaseResponse<List<ProjectTreeBean>>>
    }
}