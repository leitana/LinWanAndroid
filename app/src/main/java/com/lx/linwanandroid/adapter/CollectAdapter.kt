package com.lx.linwanandroid.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.CollectionArticle
import com.lx.linwanandroid.utils.ImageLoader

/**
 * @title：CollectAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/20
 */
class CollectAdapter(datas: MutableList<CollectionArticle.DatasBean>)
    :BaseQuickAdapter<CollectionArticle.DatasBean, BaseViewHolder>(R.layout.item_collect_list, datas)
    , LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CollectionArticle.DatasBean) {
        holder ?: return
        item ?: return
        holder.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, item.author)
            .setText(R.id.tv_article_date, item.niceDate)
            .setImageResource(R.id.iv_like, R.drawable.ic_like)

        holder.setText(R.id.tv_article_chapterName, item.chapterName)
        if (item.envelopePic?.isNotEmpty()!!) {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.VISIBLE
            context?.let {
                ImageLoader.load(it, item.envelopePic, holder.getView(R.id.iv_article_thumbnail))
            }
        } else {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.GONE
        }
    }
}