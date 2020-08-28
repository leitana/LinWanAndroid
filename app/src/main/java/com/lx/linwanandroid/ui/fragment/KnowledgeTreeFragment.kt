package com.lx.linwanandroid.ui.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.KnowledgeTreeAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.KnowledgeSystem
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.mvp.contract.KnowledgeTreeContract
import com.lx.linwanandroid.mvp.presenter.KnowledgeTreePresenter
import com.lx.linwanandroid.ui.activity.KnowledgeActivity
import com.lx.linwanandroid.widgets.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * @title：KnowledgeTreeFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/07/10
 */
class KnowledgeTreeFragment: BaseMvpStatusViewFragment<KnowledgeTreeContract.View, KnowledgeTreeContract.Presenter>(), KnowledgeTreeContract.View {
    companion object {
        fun getInstance(): KnowledgeTreeFragment = KnowledgeTreeFragment()
    }

    override fun createPresenter(): KnowledgeTreeContract.Presenter = KnowledgeTreePresenter()

    override fun setContentView(): Int = R.layout.fragment_refresh_layout

    private val datas = mutableListOf<KnowledgeSystem>()

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    private val knowledgeTreeAdapter: KnowledgeTreeAdapter by lazy {
        KnowledgeTreeAdapter(datas)
    }


    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = knowledgeTreeAdapter
            itemAnimator = DefaultItemAnimator()
        }
        knowledgeTreeAdapter.run {
            recyclerView = this@KnowledgeTreeFragment.recyclerView
            setOnItemClickListener { adapter, view, position ->
                if (datas.size != 0) {
                    val data = datas[position]
                    Intent(activity, KnowledgeActivity::class.java).run {
                        putExtra(Constant.CONTENT_TITLE_KEY, data.name)
                        putExtra(Constant.CONTENT_DATA_KEY, data)
                        startActivity(this)
                    }
                }
            }
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestKnowledgeTree()
    }

    override fun hideLoading() {
        super.hideLoading()
        swipeRefreshLayout.isRefreshing = false
//        knowledgeTreeAdapter.run {
//            loadMoreModule.loadMoreComplete()
//        }
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

    override fun showKnowledgeTree(list: MutableList<KnowledgeSystem>) {
        list.let {
            knowledgeTreeAdapter.run {
                setList(list)
            }
            if (knowledgeTreeAdapter.data.isEmpty()) {
                mLayoutStatusView?.showEmpty()
            } else {
                mLayoutStatusView?.showContent()
            }
        }
    }

    /**
     * 刷新
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        mPresenter?.requestKnowledgeTree()
    }
}