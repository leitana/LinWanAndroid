package com.lx.linwanandroid.ui.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.SearchHistoryAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewActivity
import com.lx.linwanandroid.bean.SearchHistoryBean
import com.lx.linwanandroid.bean.SearchHotBean
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.mvp.contract.SearchContract
import com.lx.linwanandroid.mvp.presenter.SearchPresenter
import com.lx.linwanandroid.utils.CommonUtil
import com.lx.linwanandroid.utils.DisplayManager
import com.lx.linwanandroid.widgets.RecyclerViewItemDecoration
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

/**
 * @title：SearchActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class SearchActivity: BaseMvpStatusViewActivity<SearchContract.View, SearchContract.Presenter>(),
    SearchContract.View{
    override fun createPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun getLayoutId(): Int = R.layout.activity_search

    private var mHotSearchDatas = mutableListOf<SearchHotBean>()

    private val datas = mutableListOf<SearchHistoryBean>()

    private val searchHistoryAdapter by lazy {
        SearchHistoryAdapter(datas)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val recyclerViewItemDecoration by lazy {
        RecyclerViewItemDecoration(this)
    }

    override fun initView() {
        super.initView()
        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { finish() }
        }

        hot_search_flow_layout.run {
            setOnTagClickListener { view, position, parent ->
                if (mHotSearchDatas.size > 0){
                    val hotSearchBean = mHotSearchDatas[position]
                    hotSearchBean.name?.let { goToSearchList(it) }
                    true
                }
                false
            }
        }

        rv_history_search.run {
            layoutManager = linearLayoutManager
            adapter = searchHistoryAdapter
            itemAnimator = DefaultItemAnimator()
        }

        searchHistoryAdapter.run {
            recyclerView = this@SearchActivity.rv_history_search
            setOnItemClickListener { adapter, view, position ->
                if (searchHistoryAdapter.data.size != 0) {
                    val item = searchHistoryAdapter.data[position]
                    goToSearchList(item.key)
                }
            }
            addChildClickViewIds(R.id.iv_clear)
            setOnItemChildClickListener { adapter, view, position ->
                if (searchHistoryAdapter.data.size != 0) {
                    val item = searchHistoryAdapter.data[position]
                    when (view.id) {
                        R.id.iv_clear -> {
                            mPresenter?.deleteById(item.id)
                            searchHistoryAdapter.removeAt(position)
                        }
                    }
                }
            }
        }

        search_history_clear_all_tv.run {
            datas.clear()
            searchHistoryAdapter.notifyDataSetChanged()
            mPresenter?.clearAllHistory()
        }
        mPresenter?.requestHotSearchData()

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.queryHistory()
    }

    override fun start() {
    }

    override fun initData() {
    }

    override fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>) {
        searchHistoryAdapter.setList(historyBeans)
    }

    override fun showHotSearchData(hotSearchBean: MutableList<SearchHotBean>) {
        this.mHotSearchDatas.addAll(hotSearchBean)
        hot_search_flow_layout.adapter = object : TagAdapter<SearchHotBean>(hotSearchBean) {
            override fun getView(parent: FlowLayout?, position: Int, hotSearchBean: SearchHotBean?): View {
                val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv,
                    hot_search_flow_layout, false) as TextView
                val padding: Int = DisplayManager.dip2px(10F)!!
                tv.setPadding(padding, padding, padding, padding)
                tv.text = hotSearchBean?.name
                tv.setTextColor(CommonUtil.randomColor())
                return tv
            }
        }
    }

    private fun goToSearchList(key: String) {
        mPresenter?.saveSearchKey(key)
        Intent(this, CommonActivity::class.java).run {
            putExtra(Constant.TYPE_KEY, Constant.Type.SEARCH_TYPE_KEY)
            putExtra(Constant.SEARCH_KEY, key)
            startActivity(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.isSubmitButtonEnabled = true
        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            goToSearchList(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }
}