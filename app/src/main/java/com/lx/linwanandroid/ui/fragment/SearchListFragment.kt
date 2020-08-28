package com.lx.linwanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.HomeAdapter
import com.lx.linwanandroid.adapter.SearchListAdapter
import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.ProjectArticle
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ext.showSnackMsg
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.SearchListContract
import com.lx.linwanandroid.mvp.presenter.SearchListPresenter
import com.lx.linwanandroid.ui.activity.ContentActivity
import com.lx.linwanandroid.ui.activity.LoginActivity
import com.lx.linwanandroid.utils.NetWorkUtil
import com.lx.linwanandroid.widgets.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import kotlinx.android.synthetic.main.fragment_search_list.*

/**
 * @title：SearchListFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
class SearchListFragment: BaseMvpStatusViewFragment<SearchListContract.View, SearchListContract.Presenter>(),
    SearchListContract.View{

    private var mKey = ""

    companion object {
        fun getInstance(bundle: Bundle): SearchListFragment {
            val fragment = SearchListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): SearchListContract.Presenter = SearchListPresenter()

    override fun setContentView(): Int = R.layout.fragment_search_list

    /**
     * datas
     */
    private val datas = mutableListOf<ProjectArticle.DatasBean>()

    /**
     * Adapter
     */
    private val searchListAdapter: SearchListAdapter by lazy {
        SearchListAdapter(datas)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    /**
     * is Refresh
     */
    private var isRefresh = true

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            searchListAdapter.run {
                loadMoreModule.isEnableLoadMore = true
            }
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        searchListAdapter.run {
            if (isRefresh)
                loadMoreModule.isEnableLoadMore = true
            else
                loadMoreModule.loadMoreFail()
        }
    }

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        mKey = arguments?.getString(Constant.SEARCH_KEY, "") ?: ""

        swipeRefreshLayout.run {
            setOnRefreshListener {
                isRefresh = true
                searchListAdapter.loadMoreModule.isEnableLoadMore = false
                mPresenter?.queryBySearchKey(0, mKey)
            }
        }

        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = searchListAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        searchListAdapter.run {
            recyclerView = this@SearchListFragment.recyclerView
            setOnItemClickListener { adapter, view, position ->
                if (datas.size != 0) {
                    val data = datas[position]
                    Intent(activity, ContentActivity::class.java).run {
                        putExtra(Constant.CONTENT_URL_KEY, data.link)
                        putExtra(Constant.CONTENT_TITLE_KEY, data.title)
                        putExtra(Constant.CONTENT_ID_KEY, data.id)
                        startActivity(this)
                    }
                }
            }
            addChildClickViewIds(R.id.iv_like)
            setOnItemChildClickListener { adapter, view, position ->
                if (datas.size != 0) {
                    val data = datas[position]
                    when (view.id) {
                        R.id.iv_like -> {
                            if (isLogin) {
                                val collect = data.collect
                                data.collect = !collect
                                searchListAdapter.setData(position, data)
                                if (collect) {
                                    mPresenter?.cancelCollectArticle(data.id)
                                } else{
                                    mPresenter?.addCollectArticle(data.id)
                                }
                            }
                        }
                    }
                }
            }
        }

        floating_action_btn.setOnClickListener {
            scrollToTop()
        }
    }


    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.queryBySearchKey(0, mKey)
    }

    override fun showArticles(articles: ProjectArticle) {
        articles.datas?.let {
            searchListAdapter.run {
                if (isRefresh) {
                    setList(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < articles.size) {
                    loadMoreModule.loadMoreEnd(isRefresh)
                } else {
                    loadMoreModule.loadMoreComplete()
                }
            }
        }
        if (searchListAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun scrollToTop() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(getString(R.string.collect_success))
        }
    }

    override fun showCancelColletcSuccess(success: Boolean) {
        if (success) {
            showToast(getString(R.string.cancel_collect_success))
        }
    }
}