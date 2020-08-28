package com.lx.linwanandroid.mvp.contract

import com.lx.base.IModel
import com.lx.base.IPresenter
import com.lx.base.IView
import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.SearchHistoryBean
import com.lx.linwanandroid.bean.SearchHotBean
import io.reactivex.Observable

/**
 * @title：SearchContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
interface SearchContract {
    interface View: IView {
        fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>)

        fun showHotSearchData(hotSearchBean: MutableList<SearchHotBean>)
    }

    interface Presenter: IPresenter<View> {
        fun queryHistory()

        fun saveSearchKey(key: String)

        fun deleteById(id: Long)

        fun clearAllHistory()

        fun requestHotSearchData()
    }

    interface Model: IModel {
        fun getHotSearchData(): Observable<BaseResponse<MutableList<SearchHotBean>>>
    }

}