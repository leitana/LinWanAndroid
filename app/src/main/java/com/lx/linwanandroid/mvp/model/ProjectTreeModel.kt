package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.ProjectTreeBean
import com.lx.linwanandroid.mvp.contract.ProjectTreeContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：ProjectTreeModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectTreeModel: BaseModel(), ProjectTreeContract.Model {
    override fun requestProjectTree(): Observable<BaseResponse<List<ProjectTreeBean>>> {
        return RetrofitHelper.service.getProjectTree()
    }
}