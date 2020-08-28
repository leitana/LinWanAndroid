package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.KnowledgeSystem
import com.lx.linwanandroid.mvp.contract.KnowledgeTreeContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：KnowledgeTreeModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/21
 */
class KnowledgeTreeModel: BaseModel(), KnowledgeTreeContract.Model {
    override fun requestKnowledgeTree(): Observable<BaseResponse<MutableList<KnowledgeSystem>>> {
        return RetrofitHelper.service.getKnowledgeTree()
    }
}