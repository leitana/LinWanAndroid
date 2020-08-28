package com.lx.linwanandroid.ui.fragment

import android.content.res.ColorStateList
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.WeChatPageAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.WXchapters
import com.lx.linwanandroid.mvp.contract.WeChatContract
import com.lx.linwanandroid.mvp.presenter.WXChaptersPresenter
import com.lx.linwanandroid.utils.SettingUtil
import kotlinx.android.synthetic.main.fragment_wechat.*

/**
 * @title：WeChartFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/25
 */
class WeChatFragment: BaseMvpStatusViewFragment<WeChatContract.View, WeChatContract.Presenter>(),
    WeChatContract.View{

    companion object {
        fun getInstance(): WeChatFragment = WeChatFragment()
    }

    override fun createPresenter(): WeChatContract.Presenter = WXChaptersPresenter()

    override fun setContentView(): Int = R.layout.fragment_wechat

    private val datas = mutableListOf<WXchapters>()

    private val viewPagerAdapter: WeChatPageAdapter by lazy {
        WeChatPageAdapter(datas, childFragmentManager)
    }

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        tabLayout.run {
            backgroundTintList= ColorStateList.valueOf(SettingUtil.getColor())
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        }
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
    }


    override fun lazyLoad() {
        mPresenter?.requestWXChapters()
    }

    override fun scrollToTop() {
        if (viewPagerAdapter.count == 0) {
            return
        }
        val fragment: KnowledgeFragment = viewPagerAdapter.getItem(viewPager.currentItem) as KnowledgeFragment
        fragment.scrollToTop()
    }

    override fun showWXChapters(chapters: MutableList<WXchapters>) {
        chapters.let {
            datas.addAll(it)
            viewPager.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = datas.size
            }
        }
        if (chapters.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }
}