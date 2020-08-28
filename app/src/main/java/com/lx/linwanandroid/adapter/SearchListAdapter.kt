package com.lx.linwanandroid.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.HomeArticle
import com.lx.linwanandroid.bean.ProjectArticle
import com.lx.linwanandroid.utils.ImageLoader

/**
 * @title：HomeAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
class SearchListAdapter(datas: MutableList<ProjectArticle.DatasBean>)
    : BaseQuickAdapter<ProjectArticle.DatasBean, BaseViewHolder>(R.layout.item_home_list, datas),
    LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: ProjectArticle.DatasBean) {
        val authorStr = if (!item.author.isNullOrBlank()) item.author else item.shareUser
        helper.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, authorStr)
            .setText(R.id.tv_article_date, item.niceDate)
            .setImageResource(R.id.iv_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )
        val chapterName = when {
            !item.superChapterName.isNullOrBlank() and !item.chapterName.isNullOrBlank() ->
                "${item.superChapterName} / ${item.chapterName}"
            !item.superChapterName.isNullOrBlank() -> item.superChapterName
            !item.chapterName.isNullOrBlank() -> item.chapterName
            else -> ""
        }
        helper.setText(R.id.tv_article_chapterName, chapterName)
        if (!item.envelopePic.isNullOrBlank()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.VISIBLE
            context?.let {
                ImageLoader.load(it, item.envelopePic!!, helper.getView(R.id.iv_article_thumbnail))
            }
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.GONE
        }
        val tv_fresh = helper.getView<TextView>(R.id.tv_article_fresh)
        if (item.fresh) {
            tv_fresh.visibility = View.VISIBLE
        } else {
            tv_fresh.visibility = View.GONE
        }
        val tv_top = helper.getView<TextView>(R.id.tv_article_top)
        val tv_article_tag = helper.getView<TextView>(R.id.tv_article_tag)
        if (!item.tags.isNullOrEmpty()) {
            tv_article_tag.visibility = View.VISIBLE
            tv_article_tag.text = item.tags!![0].name
        } else {
            tv_article_tag.visibility = View.GONE
        }
    }
}