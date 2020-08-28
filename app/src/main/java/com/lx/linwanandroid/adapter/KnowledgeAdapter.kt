package com.lx.linwanandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.KnowledgeSysArticle
import com.lx.linwanandroid.utils.ImageLoader

/**
 * @title：KnowledgeAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/24
 */
class KnowledgeAdapter(datas: MutableList<KnowledgeSysArticle.DatasBean>): BaseQuickAdapter<KnowledgeSysArticle.DatasBean, BaseViewHolder>
    (R.layout.item_knowledge_list, datas), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: KnowledgeSysArticle.DatasBean) {
        item ?: return
        holder ?: return
        val authorStr = if (item.author?.isNotEmpty()!!) item.author else item.shareUser
        holder.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, authorStr)
            .setText(R.id.tv_article_date, item.niceDate)
            .setImageResource(R.id.iv_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )
        val chapterName = when {
            item.chapterName?.isNotEmpty()?.let { item.superChapterName?.isNotEmpty()?.and(it) }!! ->
                "${item.superChapterName} / ${item.chapterName}"
            item.superChapterName?.isNotEmpty()!! -> item.superChapterName
            item.chapterName?.isNotEmpty()!! -> item.chapterName
            else -> ""
        }
        holder.setText(R.id.tv_article_chapterName, chapterName)

        if (!TextUtils.isEmpty(item.envelopePic)) {
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