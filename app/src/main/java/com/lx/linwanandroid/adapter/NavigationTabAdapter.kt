package com.lx.linwanandroid.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.module.LoadMoreModule
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.NavigationBean
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * Created by chenxz on 2018/5/13.
 */
class NavigationTabAdapter(context: Context?, list: List<NavigationBean>) : TabAdapter, LoadMoreModule {

    private var context: Context = context!!
    private var list = mutableListOf<NavigationBean>()

    init {
        this.list = list as MutableList<NavigationBean>
    }

    override fun getIcon(position: Int): ITabView.TabIcon? = null


    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
                .setContent(list[position].name)
                .setTextColor(
                    ContextCompat.getColor(context, R.color.colorAccent),
                        ContextCompat.getColor(context, R.color.Grey500))
                .build()
    }

    override fun getCount(): Int = list.size

}