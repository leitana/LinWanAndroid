package com.lx.linwanandroid.mvp.presenter

import com.lx.linwanandroid.mvp.contract.ProjectListContract
import com.lx.linwanandroid.mvp.model.ProjectListModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：ProjectListPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectListPresenter: CommonPresenter<ProjectListContract.Model, ProjectListContract.View>(),
    ProjectListContract.Presenter{
    override fun getModel(): ProjectListContract.Model = ProjectListModel()

    override fun requestProjectList(page: Int, cid: Int) {
        mModel?.requestProjectList(page, cid)?.mysubscribe(mModel, mView){
            mView?.setProjectList(it.data)
        }
    }
}