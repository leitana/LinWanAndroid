package com.lx.linwanandroid.mvp.presenter

import com.lx.base.BasePresenter
import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.bean.SearchHistoryBean
import com.lx.linwanandroid.mvp.contract.SearchContract
import com.lx.linwanandroid.mvp.model.SearchModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：SearchPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
class SearchPresenter: BasePresenter<SearchContract.View, SearchContract.Model>(),
    SearchContract.Presenter{
    override fun getModel(): SearchContract.Model = SearchModel()

    override fun queryHistory() {
        val historyBeans = App.getDataBase().dao().searchHistoryList
        if (historyBeans != null) {
            mView?.showHistoryData(historyBeans)
        }
    }

    override fun saveSearchKey(key: String) {
        var searchHistoryBean = SearchHistoryBean()
        searchHistoryBean.key = key
        App.getDataBase().dao().insertSearchHistory(searchHistoryBean)
    }

    override fun deleteById(id: Long) {
        App.getDataBase().dao().deleteBySheetId(id)
    }

    override fun clearAllHistory() {
        App.getDataBase().dao().deleteAllHistory()
    }

    override fun requestHotSearchData() {
        mModel?.getHotSearchData()?.mysubscribe(mModel, mView){
            mView?.showHotSearchData(it.data)
        }
    }
}