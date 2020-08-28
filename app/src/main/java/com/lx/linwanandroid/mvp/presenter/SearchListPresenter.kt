package com.lx.linwanandroid.mvp.presenter

import com.lx.linwanandroid.mvp.contract.SearchListContract
import com.lx.linwanandroid.mvp.model.SearchListModel
import com.lx.linwanandroid.rxutils.mysubscribe

/**
 * @title：SearchListPresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
class SearchListPresenter: CommonPresenter<SearchListContract.Model, SearchListContract.View>(),
    SearchListContract.Presenter{
    override fun getModel(): SearchListContract.Model = SearchListModel()

    override fun queryBySearchKey(page: Int, key: String) {
        mModel?.queryBySearchKey(page, key)?.mysubscribe(mModel, mView){
            mView?.showArticles(it.data)
        }
    }
}