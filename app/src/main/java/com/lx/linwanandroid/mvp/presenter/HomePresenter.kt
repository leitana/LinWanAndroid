package com.lx.linwanandroid.mvp.presenter

import com.lx.linwanandroid.bean.BaseResponse
import com.lx.linwanandroid.bean.HomeArticle
import com.lx.linwanandroid.mvp.contract.HomeContract
import com.lx.linwanandroid.mvp.model.HomeModel
import com.lx.linwanandroid.rxutils.mysubscribe
import com.lx.linwanandroid.utils.SettingUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * @title：HomePresenter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
class HomePresenter: CommonPresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {
    override fun getModel(): HomeContract.Model = HomeModel()

    override fun requestBanner() {
        mModel?.requestBanner()?.mysubscribe(mModel,mView,false){
            mView?.setBanner(it.data)
        }
    }

    override fun requestArticles(num: Int) {
        mModel?.requestArticles(num)?.mysubscribe(mModel, mView, num == 0) {
            mView?.setArticles(it.data)
        }
    }

    override fun requestHomeData() {
        requestBanner()

        val observable = if (SettingUtil.getIsShowTopArticle()) {
            mModel?.requestArticles(0)
        } else {
            Observable.zip(mModel?.requestTopArticles(), mModel?.requestArticles(0),
                BiFunction<BaseResponse<MutableList<HomeArticle.DatasBean>>, BaseResponse<HomeArticle>,
                        BaseResponse<HomeArticle>> {t1, t2 ->
                    t1.data.forEach {
                        // 置顶数据中没有标识，手动添加一个标识
                        it.top = "1"
                    }
                    t2.data.datas?.addAll(0, t1.data)
                    t2
                })
        }

        observable?.mysubscribe(mModel, mView, false) {
            mView?.setArticles(it.data)
        }
    }

}