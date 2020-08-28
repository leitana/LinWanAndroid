package com.lx.linwanandroid.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.KnowledgeSystem

/**
 * @title：KnowledgeTreeAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/21
 */
class KnowledgeTreeAdapter(datas: MutableList<KnowledgeSystem>):
    BaseQuickAdapter<KnowledgeSystem, BaseViewHolder>
        (R.layout.item_knowledge_tree_list, datas) {
    override fun convert(holder: BaseViewHolder, item: KnowledgeSystem) {
        holder.setText(R.id.title_first, item.name)
        item.children.let {
            holder.setText(R.id.title_second,
                it?.joinToString("   ", transform = { child ->
                    Html.fromHtml(child.name)
                })
            )
        }
    }
}