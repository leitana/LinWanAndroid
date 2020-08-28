package com.lx.linwanandroid.ui.fragment

import android.content.res.ColorStateList
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.ProjectTreeAdapter
import com.lx.linwanandroid.base.BaseMvpStatusViewFragment
import com.lx.linwanandroid.bean.ProjectTreeBean
import com.lx.linwanandroid.mvp.contract.ProjectTreeContract
import com.lx.linwanandroid.mvp.presenter.ProjectTreePresenter
import com.lx.linwanandroid.utils.SettingUtil
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * @title：ProjectTreeFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectTreeFragment: BaseMvpStatusViewFragment<ProjectTreeContract.View, ProjectTreeContract.Presenter>(),
    ProjectTreeContract.View{
    companion object{
        fun getInstance(): ProjectTreeFragment {
            return ProjectTreeFragment()
        }
    }
    override fun createPresenter(): ProjectTreeContract.Presenter = ProjectTreePresenter()

    override fun setContentView(): Int = R.layout.fragment_project

    private var projectTree = mutableListOf<ProjectTreeBean>()

    private val viewPagerAdapter: ProjectTreeAdapter by lazy {
        ProjectTreeAdapter(projectTree, childFragmentManager)
    }

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }
        tabLayout.run {
            backgroundTintList= ColorStateList.valueOf(SettingUtil.getColor())
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        }
    }

    override fun lazyLoad() {
        mPresenter?.requestProjectTree()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun showError(msg: String) {
        super.showError(msg)
        mLayoutStatusView?.showError()
    }

    override fun scrollToTop() {
        if (viewPagerAdapter.count == 0) {
            return
        }
        val fragment: ProjectListFragment = viewPagerAdapter.getItem(viewPager.currentItem) as ProjectListFragment
        fragment.scrollToTop()
    }

    override fun showProjectTree(list: List<ProjectTreeBean>) {
        list.let {
            projectTree.addAll(list)
            viewPager.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = projectTree.size
            }
        }
        if (list.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }
}