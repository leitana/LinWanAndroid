package com.lx.linwanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.KnowledgeAdapter
import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.KnowledgeSysArticle
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ext.showSnackMsg
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.KnowledgeContract
import com.lx.linwanandroid.mvp.presenter.KnowledgePresenter
import com.lx.linwanandroid.ui.activity.ContentActivity
import com.lx.linwanandroid.ui.activity.LoginActivity
import com.lx.linwanandroid.utils.NetWorkUtil
import com.lx.linwanandroid.widgets.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * @title：KnowledgeFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/24
 */
class KnowledgeFragment: BaseMvpStatusViewFragment<KnowledgeContract.View, KnowledgeContract.Presenter>(),
    KnowledgeContract.View{

    companion object {
        fun getInstance(cid: Int): KnowledgeFragment {
            val fragment = KnowledgeFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createPresenter(): KnowledgeContract.Presenter = KnowledgePresenter()

    override fun setContentView(): Int = R.layout.fragment_refresh_layout

    private var cid: Int = 0

    private val datas = mutableListOf<KnowledgeSysArticle.DatasBean>()

    private val knowledgeAdapter: KnowledgeAdapter by lazy{
        KnowledgeAdapter(datas)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }


    private var isRefresh = true

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0
        swipeRefreshLayout.run {
            setOnRefreshListener {
                isRefresh = true
                knowledgeAdapter.loadMoreModule.isEnableLoadMore = false
                mPresenter?.requestKnowledgeList(0, cid)
            }
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = knowledgeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        knowledgeAdapter.run {
            loadMoreModule.setOnLoadMoreListener {
                isRefresh = false
                swipeRefreshLayout.isRefreshing = false
                val page = knowledgeAdapter.data.size / 20
                mPresenter?.requestKnowledgeList(page, cid)
            }

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
                                if (!NetWorkUtil.isNetworkAvailable(App.context)) {
                                    showSnackMsg(resources.getString(R.string.no_network))
                                    return@setOnItemChildClickListener
                                }
                                val collect = data.collect
                                data.collect = !collect
                                knowledgeAdapter.setData(position, data)
                                if (collect) {
                                    mPresenter?.cancelCollectArticle(data.id)
                                } else {
                                    mPresenter?.addCollectArticle(data.id)
                                }
                            } else {
                                Intent(activity, LoginActivity::class.java).run {
                                    startActivity(this)
                                }
                                showToast(resources.getString(R.string.login_tint))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestKnowledgeList(0, cid)
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            knowledgeAdapter.run {
                loadMoreModule.isEnableLoadMore = true
            }
        }
    }

    override fun showKnowledgeList(article: KnowledgeSysArticle) {
        article.datas?.let {
            knowledgeAdapter.run {
                if (isRefresh) {
                    setList(article.datas as MutableList<KnowledgeSysArticle.DatasBean>?)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < article.size) {
                    loadMoreModule.loadMoreEnd(isRefresh)
                } else {
                    loadMoreModule.loadMoreComplete()
                }
            }
        }
        if (knowledgeAdapter.data.isEmpty()) {
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