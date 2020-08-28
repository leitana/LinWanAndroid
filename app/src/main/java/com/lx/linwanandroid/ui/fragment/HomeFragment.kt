package com.lx.linwanandroid.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.lx.base.BaseMvpFragment
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.HomeAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewActivity
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.Banner
import com.lx.linwanandroid.bean.HomeArticle
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.HomeContract
import com.lx.linwanandroid.mvp.presenter.HomePresenter
import com.lx.linwanandroid.ui.activity.ContentActivity
import com.lx.linwanandroid.utils.ImageLoader
import com.lx.linwanandroid.widgets.SpaceItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import kotlinx.android.synthetic.main.item_home_banner.*
import kotlinx.android.synthetic.main.item_home_banner.view.*

/**
 * @title：HomeFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
class HomeFragment: BaseMvpStatusViewFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private val datas = mutableListOf<HomeArticle.DatasBean>()

    private lateinit var bannerDatas: ArrayList<Banner>

    private var bannerView: View? = null

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            ImageLoader.load(context, feedImageUrl, imageView)
        }
    }

    private var isRefresh = true

    private val homeAdapter: HomeAdapter by lazy{
        HomeAdapter(activity, datas)
    }

    //recyclerview分割线
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()
    override fun setContentView(): Int = R.layout.fragment_refresh_layout

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view

        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)
        bannerView?.banner?.run {
            setDelegate(bannerDelegate)
        }

        homeAdapter.run {
//            recyclerView.adapter = this
            recyclerView = this@HomeFragment.recyclerView
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
                        if (isLogin) {
                            val collect = data.collect
                            data.collect = !collect
                            homeAdapter.setData(position, data)
                            if (collect) {
                                mPresenter?.cancelCollectArticle(data.id)
                            } else{
                                mPresenter?.addCollectArticle(data.id)
                            }
                        }
                    }
                }
            }
            addHeaderView(bannerView!!)
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestHomeData()
    }

    override fun scrollToUp() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun setBanner(banners: List<Banner>) {
        bannerDatas = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners)
            .subscribe { list ->
                list.imagePath?.let { bannerFeedList.add(it) }
                list.title?.let { bannerTitleList.add(it) }
            }
        bannerView?.banner?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }
    }

    override fun setArticles(article: HomeArticle) {
        article.datas?.let{
            homeAdapter.run {
                if (isRefresh) {
                    setList(it)
                } else{
                    addData(it)
                }
                val size = it.size
                if (size < article.size) {
                    // 所有数据加载完成，调用此方法
                    // 需要重置"加载完成"状态时，请调用 setNewData()
                    loadMoreModule.loadMoreEnd(isRefresh)
                } else {
                    // 当前这次数据加载完毕，调用此方法
                    loadMoreModule.loadMoreComplete()
                }
            }
        }
        if (homeAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            homeAdapter.run {
                loadMoreModule.isEnableLoadMore = true
            }
        }
    }

    override fun showError(msg: String) {
        super.showError(msg)
        mLayoutStatusView?.showError()
        homeAdapter.run {
            if (isRefresh) {
                // 打开或关闭加载更多功能（默认为true）
                loadMoreModule.isEnableLoadMore = true
            } else{
                // 当前这次数据加载错误，调用此方法
                loadMoreModule.loadMoreFail()
            }
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.collect_success))
        }
    }

    override fun showCancelColletcSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.cancel_collect_success))
        }
    }

    //刷新
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        homeAdapter.loadMoreModule.isEnableLoadMore = false
        mPresenter?.requestHomeData()
    }

    //加载更多
    private val onRequestLoadMoreListener = OnLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = homeAdapter.data.size / 20
        mPresenter?.requestArticles(page)
    }


    /**
     * banner
     */
    private val bannerDelegate = BGABanner.Delegate<ImageView, String>{ banner, imageView, model, position ->
        if (bannerDatas.size > 0) {
            val data = bannerDatas[position]
            Intent(activity, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, data.url)
                putExtra(Constant.CONTENT_TITLE_KEY, data.title)
                putExtra(Constant.CONTENT_ID_KEY, data.id)
                startActivity(this)
            }
        }
    }

}