package com.lx.linwanandroid.ui.activity

import com.lx.linwanandroid.R
import com.lx.linwanandroid.base.BaseStatusViewActivity
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ui.fragment.CollectFragment
import com.lx.linwanandroid.ui.fragment.SearchListFragment
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @title：CommonActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/20
 */
class CommonActivity: BaseStatusViewActivity() {

    override fun getLayoutId(): Int = R.layout.activity_common

    override fun start() {
    }

    override fun initView() {
        val extras = intent.extras
        val type = extras?.getString(Constant.TYPE_KEY, "")
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { finish() }
        }
        val fragment = when(type){
            Constant.Type.COLLECT_TYPE_KEY -> {
                toolbar.title = getString(R.string.collect)
                CollectFragment.getInstance(extras)
            }
            Constant.Type.SEARCH_TYPE_KEY -> {
                toolbar.title = extras.getString(Constant.SEARCH_KEY, "")
                SearchListFragment.getInstance(extras)
            }
            else -> {
                null
            }
        }
        fragment ?: return
        supportFragmentManager.beginTransaction()
            .replace(R.id.common_frame_layout, fragment, Constant.Type.COLLECT_TYPE_KEY)
            .commit()
    }

    override fun initData() {
    }
}