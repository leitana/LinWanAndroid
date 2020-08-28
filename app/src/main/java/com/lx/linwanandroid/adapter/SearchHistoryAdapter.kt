package com.lx.linwanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.SearchHistoryBean

class SearchHistoryAdapter(datas: MutableList<SearchHistoryBean>)
    : BaseQuickAdapter<SearchHistoryBean, BaseViewHolder>(R.layout.item_search_history, datas) {
    override fun convert(holder: BaseViewHolder, item: SearchHistoryBean) {
        holder ?: return
        item ?: return
        holder.setText(R.id.tv_search_key, item.key)
    }

}