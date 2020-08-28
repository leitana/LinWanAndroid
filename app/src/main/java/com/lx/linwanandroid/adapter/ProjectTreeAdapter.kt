package com.lx.linwanandroid.adapter

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.lx.linwanandroid.bean.ProjectTreeBean
import com.lx.linwanandroid.ui.fragment.ProjectListFragment

/**
 * @title：ProjectTreeAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectTreeAdapter(val list: MutableList<ProjectTreeBean>, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(ProjectListFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}