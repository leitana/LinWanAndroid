package com.lx.linwanandroid.adapter

import android.content.Context
import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.ProjectArticle
import com.lx.linwanandroid.utils.ImageLoader

/**
 * Created by chenxz on 2018/5/20.
 */
class ProjectAdapter(datas: MutableList<ProjectArticle.DatasBean>)
    : BaseQuickAdapter<ProjectArticle.DatasBean, BaseViewHolder>(R.layout.item_project_list, datas),LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: ProjectArticle.DatasBean) {
        holder ?: return
        item ?: return
        val authorStr = if (item.author?.isNotEmpty()!!) item.author else item.shareUser
        holder.setText(R.id.item_project_list_title_tv, Html.fromHtml(item.title))
            .setText(R.id.item_project_list_content_tv, Html.fromHtml(item.desc))
            .setText(R.id.item_project_list_time_tv, item.niceDate)
            .setText(R.id.item_project_list_author_tv, authorStr)
            .setImageResource(R.id.item_project_list_like_iv,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )
        context.let {
            ImageLoader.load(it, item.envelopePic, holder.getView(R.id.item_project_list_iv))
        }
    }

}