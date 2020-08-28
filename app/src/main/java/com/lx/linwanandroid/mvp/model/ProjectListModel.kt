package com.lx.linwanandroid.mvp.model

import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.ProjectArticle
import com.lx.linwanandroid.mvp.contract.ProjectListContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：ProjectListModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectListModel: CommonModel(), ProjectListContract.Model {
    override fun requestProjectList(page: Int, cid: Int): Observable<BaseResponse<ProjectArticle>> {
        return RetrofitHelper.service.getProjectList(page, cid)
    }
}