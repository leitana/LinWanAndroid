package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.mvp.contract.ProjectTreeContract
import com.lx.linwanandroid.mvp.model.ProjectTreeModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：ProjectTreePresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectTreePresenter: BasePresenter<ProjectTreeContract.View, ProjectTreeContract.Model>(),
    ProjectTreeContract.Presenter{
    override fun getModel(): ProjectTreeContract.Model = ProjectTreeModel()

    override fun requestProjectTree() {
        mModel?.requestProjectTree()?.mysubscribe(mModel, mView){
            mView?.showProjectTree(it.data)
        }
    }
}