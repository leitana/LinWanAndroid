package com.lx.linwanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.CollectAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.CollectionArticle
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.event.RefreshHomeEvent
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.CollectContract
import com.lx.linwanandroid.mvp.presenter.CollectPresenter
import com.lx.linwanandroid.ui.activity.ContentActivity
import com.lx.linwanandroid.widgets.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * @title：CollectFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/20
 */
class CollectFragment: BaseMvpStatusViewFragment<CollectContract.View, CollectContract.Presenter>(), CollectContract.View {

    companion object {
        fun getInstance(bundle: Bundle): CollectFragment{
            val fragment = CollectFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val datas = mutableListOf<CollectionArticle.DatasBean>()

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    private val collectAdapter: CollectAdapter by lazy {
        CollectAdapter(datas)
    }

    private var isRefresh = true

    override fun createPresenter(): CollectContract.Presenter = CollectPresenter()

    override fun setContentView(): Int = R.layout.fragment_collect


    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view

        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = collectAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        collectAdapter.run {
            recyclerView = this@CollectFragment.recyclerView
            loadMoreModule.setOnLoadMoreListener(onRequestLoadMoreListener)
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
                val data = datas[position]
                when(view.id) {
                    R.id.iv_like -> {
                        collectAdapter.remove(position)
                        mPresenter?.removeCollect(data.id, data.originId)
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
        mPresenter?.getCollectList(0)
    }

    //刷新
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener{
        isRefresh = true
        collectAdapter.loadMoreModule.isEnableLoadMore = false
        mPresenter?.getCollectList(0)
    }
    //加载更多
    private val onRequestLoadMoreListener = OnLoadMoreListener{
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = collectAdapter.data.size / 20
        mPresenter?.getCollectList(page)
    }

    override fun setCollectList(collects: CollectionArticle) {
        collects.datas?.let {
            collectAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < collects.size) {
                    loadMoreModule.loadMoreEnd(isRefresh)
                } else {
                    loadMoreModule.loadMoreComplete()
                }
            }
        }
        if (collectAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun showRemoveSuccess(success: Boolean) {
        if (success) {
            showToast(getString(R.string.cancel_collect_success))
            EventBus.getDefault().post(RefreshHomeEvent(true))
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

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            collectAdapter.run {
                loadMoreModule.isEnableLoadMore = true

            }
        }
    }

    override fun showError(msg: String) {
        mLayoutStatusView?.showError()
        collectAdapter.run {
            if (isRefresh)
                loadMoreModule.isEnableLoadMore = true
            else
                loadMoreModule.loadMoreFail()
        }
    }
}