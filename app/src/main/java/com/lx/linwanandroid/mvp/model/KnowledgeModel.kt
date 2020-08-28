package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.KnowledgeSysArticle
import com.lx.linwanandroid.mvp.contract.KnowledgeContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：KnowledgeModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/24
 */
class KnowledgeModel: CommonModel(), KnowledgeContract.Model {
    override fun requestKnowledgeList(
        page: Int,
        cid: Int
    ): Observable<BaseResponse<KnowledgeSysArticle>> {
        return RetrofitHelper.service.getKnowledge(page, cid)
    }
}