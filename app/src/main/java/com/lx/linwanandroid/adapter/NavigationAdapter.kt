package com.lx.linwanandroid.adapter

import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid.R
import com.lx.linwanandroid.bean.NavigationBean
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ui.activity.ContentActivity
import com.lx.linwanandroid.utils.CommonUtil
import com.lx.linwanandroid.utils.DisplayManager
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.zhy.view.flowlayout.TagFlowLayout.dip2px

/**
 * @title：NavigationAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/26
 */
class NavigationAdapter(datas: MutableList<NavigationBean>): BaseQuickAdapter<NavigationBean, BaseViewHolder>
    (R.layout.item_navigation_list, datas) {
    override fun convert(holder: BaseViewHolder, item: NavigationBean) {
        item ?: return
        holder.setText(R.id.item_navigation_tv, item.name)
        val flowLayout: TagFlowLayout = holder.getView(R.id.item_navigation_flow_layout)
        val articles: List<NavigationBean.Article> = item.articles!!
        flowLayout.run {
            adapter = object : TagAdapter<NavigationBean.Article>(articles){
                override fun getView(
                    parent: FlowLayout?,
                    position: Int,
                    article: NavigationBean.Article?
                ): View {
                    val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv,
                        flowLayout, false) as TextView
                    val padding: Int = DisplayManager.dip2px(10F)
                    tv.setPadding(padding, padding, padding, padding)
                    tv.text = article?.title
                    tv.setTextColor(CommonUtil.randomColor())

                    setOnTagClickListener { view, position, _ ->
                        val options: ActivityOptions = ActivityOptions.makeScaleUpAnimation(view,
                            view.width / 2,
                            view.height / 2,
                            0,
                            0)
                        var data: NavigationBean.Article = articles[position]
                        Intent(context, ContentActivity::class.java).run {
                            putExtra(Constant.CONTENT_URL_KEY, data.link)
                            putExtra(Constant.CONTENT_TITLE_KEY, data.title)
                            putExtra(Constant.CONTENT_ID_KEY, data.id)
                            context.startActivity(this, options.toBundle())
                        }
                        true
                    }
                    return tv
                }

            }
        }
    }

}