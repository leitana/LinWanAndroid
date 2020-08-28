package com.lx.linwanandroid.ui.activity

import android.content.res.ColorStateList
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.lx.linwanandroid.R
import com.lx.linwanandroid.adapter.KnowledgePagerAdapter
import com.lx.linwanandroid.base.BaseStatusViewActivity
import com.lx.linwanandroid.bean.KnowledgeSysArticle
import com.lx.linwanandroid.bean.KnowledgeSystem
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ui.fragment.KnowledgeFragment
import kotlinx.android.synthetic.main.activity_knowledge.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @title：KnowledgeActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/25
 */
class KnowledgeActivity: BaseStatusViewActivity() {

    override fun getLayoutId(): Int = R.layout.activity_knowledge

    private val knowledges = mutableListOf<KnowledgeSystem.ChildrenBean>()

    private lateinit var toolbarTitle: String

    private val viewPagerAdapter: KnowledgePagerAdapter by lazy {
        KnowledgePagerAdapter(knowledges, supportFragmentManager)
    }

    override fun start() {
    }

    override fun initData() {
        intent.extras?.let {
            toolbarTitle = it.getString(Constant.CONTENT_TITLE_KEY) ?: ""
            it.getSerializable(Constant.CONTENT_DATA_KEY)?.let {
                val data = it as KnowledgeSystem
                data.children?.let { children ->
                    knowledges.addAll(children)
                }
            }
        }
    }

    override fun initView() {
        toolbar.run {
            title = toolbarTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { finish() }
        }
        viewPager.run {
            adapter = viewPagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            offscreenPageLimit = knowledges.size
        }
        tabLayout.run {
            backgroundTintList= ColorStateList.valueOf(mThemeColor)
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
//            addOnTabSelectedListener(onTabSelectedListener)
        }
        floating_action_btn.run {
            backgroundTintList = ColorStateList.valueOf(mThemeColor)
            setOnClickListener(onFABClickListener)
        }
    }

    private val onFABClickListener = View.OnClickListener {
        if (viewPagerAdapter.count == 0) {
            return@OnClickListener
        }
        val fragment: KnowledgeFragment = viewPagerAdapter.getItem(viewPager.currentItem) as KnowledgeFragment
        fragment.scrollToTop()
    }

}