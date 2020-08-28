package com.lx.linwanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.ProjectAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.ProjectArticle
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.ProjectListContract
import com.lx.linwanandroid.mvp.presenter.ProjectListPresenter
import com.lx.linwanandroid.ui.activity.ContentActivity
import com.lx.linwanandroid.widgets.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * @title：ProjectListFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectListFragment: BaseMvpStatusViewFragment<ProjectListContract.View, ProjectListContract.Presenter>(),
    ProjectListContract.View{

    companion object {
        fun getInstance(cid: Int): ProjectListFragment {
            val fragment = ProjectListFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createPresenter(): ProjectListContract.Presenter = ProjectListPresenter()

    override fun setContentView(): Int = R.layout.fragment_refresh_layout

    private var cid: Int = -1

    private val datas = mutableListOf<ProjectArticle.DatasBean>()

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val recyclerViewItemDecoration by lazy {
        activity?.let { SpaceItemDecoration(it) }
    }

    private val projectAdapter: ProjectAdapter by lazy {
        ProjectAdapter(datas)
    }

    private var isRefresh = true

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        cid = arguments!!.getInt(Constant.CONTENT_CID_KEY)

        swipeRefreshLayout.run {
            setOnRefreshListener{
                isRefresh = true
                projectAdapter.loadMoreModule.isEnableLoadMore = false
                mPresenter?.requestProjectList(1, cid)
            }
        }

        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = projectAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        projectAdapter.run {
            recyclerView = this@ProjectListFragment.recyclerView
            loadMoreModule.setOnLoadMoreListener {
                isRefresh = false
                swipeRefreshLayout.isRefreshing = false
                val page = projectAdapter.data.size / 15 + 1
                mPresenter?.requestProjectList(page, cid)
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
            addChildClickViewIds(R.id.item_project_list_like_iv)
            setOnItemChildClickListener { adapter, view, position ->
                val data = datas[position]
                when(view.id) {
                    R.id.item_project_list_like_iv -> {
                        if (isLogin) {
                            val collect = data.collect
                            data.collect = !collect
                            projectAdapter.setData(position, data)
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

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestProjectList(1, cid)
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

    override fun setProjectList(articles: ProjectArticle) {
        articles.datas?.let {
            projectAdapter.run {
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
        if (projectAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
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