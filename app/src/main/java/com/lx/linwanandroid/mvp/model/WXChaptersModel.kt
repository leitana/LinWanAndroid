package com.lx.linwanandroid.mvp.model

import com.lx.base.BaseModel
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.WXchapters
import com.lx.linwanandroid.mvp.contract.WeChatContract
import com.lx.linwanandroid.net.RetrofitHelper
import io.reactivex.Observable

/**
 * @title：WXChaptersModel
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/25
 */
class WXChaptersModel: BaseModel(), WeChatContract.Model {
    override fun requestWXChapters(): Observable<BaseResponse<MutableList<WXchapters>>> {
        return RetrofitHelper.service.getWXChapters()
    }
}