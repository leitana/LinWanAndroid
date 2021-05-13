package com.lx.linwanandroid.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.lx.linwanandroid.R
import com.lx.linwanandroid.app.App.Companion.context
import com.lx.linwanandroid.base.BaseMvpStatusViewActivity
import com.lx.linwanandroid.bean.UserInfoBody
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.event.ColorEvent
import com.lx.linwanandroid.event.LoginEvent
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.MainContract
import com.lx.linwanandroid.mvp.presenter.MainPresenter
import com.lx.linwanandroid.ui.fragment.*
import com.lx.linwanandroid.utils.DialogUtil
import com.lx.linwanandroid.utils.Preference
import com.lx.linwanandroid.utils.SettingUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseMvpStatusViewActivity<MainContract.View, MainContract.Presenter>(),MainContract.View {

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_KNOWLEDGE = 0x02
    private val FRAGMENT_NAVIGATION = 0x03
    private val FRAGMENT_PROJECT = 0x04
    private val FRAGMENT_WECHAT = 0x05

    private var mHomeFragment: HomeFragment? = null
    private var mKnowledgeTreeFragment: KnowledgeTreeFragment? = null
    private var mWeChatFragment: WeChatFragment? = null
    private var mNavigationFragment: NavigationFragment? = null
    private var mProjectTreeFragment: ProjectTreeFragment? = null

    private var mIndex = FRAGMENT_HOME

    private var username: String by Preference(Constant.USERNAME_KEY, "")

    private var nav_username: TextView? = null
    private var nav_user_id: TextView? = null
    private var nav_user_grade: TextView? = null
    private var nav_user_rank: TextView? = null
    private var nav_score: TextView? = null
    private var nav_rank: ImageView? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
    }

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun start() {
        // 获取用户个人信息
        mPresenter?.getUserInfo()
    }

    override fun initView() {
        super.initView()
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        EventBus.getDefault().register(this)
        initDrawerLayout()
        initNavView()
        showFragment(mIndex)
        floatingActionButton.run {
            setOnClickListener {
                when (mIndex) {
                    FRAGMENT_HOME -> {
                        mHomeFragment?.scrollToUp()
                    }
                    FRAGMENT_KNOWLEDGE -> {
                        mKnowledgeTreeFragment?.scrollToTop()
                    }
                    FRAGMENT_NAVIGATION -> {
                        mNavigationFragment?.scrollToTop()
                    }
                    FRAGMENT_PROJECT -> {
                        mProjectTreeFragment?.scrollToTop()
                    }
                    FRAGMENT_WECHAT -> {
                        mWeChatFragment?.scrollToTop()
                    }
                }
            }
        }
    }

     override fun showLogoutSuccess(success: Boolean) {
        if (success) {
            Preference.clearPreference()
            showToast(resources.getString(R.string.logout_success))
            username = nav_username?.text.toString().trim()
            isLogin = false
            EventBus.getDefault().post(LoginEvent(false))
        }
    }

    override fun showUserInfo(user: UserInfoBody) {
        nav_user_grade?.text = (user.coinCount / 100 + 1).toString()
        nav_user_id?.text = user.userId.toString()
        nav_user_rank?.text = user.rank.toString()
        nav_score?.text = user.coinCount.toString()
    }

    private fun initDrawerLayout(){
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar
                , R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun initColor() {
        super.initColor()
        refreshColor(ColorEvent(true))
    }

    private fun initNavView() {
        bottomNavigation.itemIconTintList = createColorStateList(this@MainActivity, mThemeColor)
        bottomNavigation.itemTextColor = createColorStateList(this@MainActivity, mThemeColor)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_knowledge_system -> {
                    showFragment(FRAGMENT_KNOWLEDGE)
                    true
                }
                R.id.action_wechat -> {
                    showFragment(FRAGMENT_WECHAT)
                    true
                }
                R.id.action_navigation -> {
                    showFragment(FRAGMENT_NAVIGATION)
                    true
                }
                R.id.action_project -> {
                    showFragment(FRAGMENT_PROJECT)
                    true
                }
                else -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
            }
        }

        nav_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            nav_username = getHeaderView(0).findViewById(R.id.tvUsername)
            nav_user_id = getHeaderView(0).findViewById(R.id.tv_user_id)
            nav_user_grade = getHeaderView(0).findViewById(R.id.tv_user_grade)
            nav_user_rank = getHeaderView(0).findViewById(R.id.tv_user_rank)
            nav_rank = getHeaderView(0).findViewById(R.id.ivRank)
            nav_score = MenuItemCompat.getActionView(nav_view.menu.findItem(R.id.nav_score)) as TextView
            nav_score?.gravity = Gravity.CENTER_VERTICAL
            menu.findItem(R.id.nav_logout).isVisible = isLogin
        }

        nav_username?.run {
            text = if (!isLogin) getString(R.string.go_login) else username
            setOnClickListener {
                if (!isLogin) {
                    Intent(this@MainActivity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                }
            }
        }

    }

    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when(index) {
            FRAGMENT_HOME -> {
                toolbar.title = getString(R.string.app_name)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_KNOWLEDGE -> {
                toolbar.title = getString(R.string.knowledge_system)
                if (mKnowledgeTreeFragment == null) {
                    mKnowledgeTreeFragment = KnowledgeTreeFragment.getInstance()
                    transaction.add(R.id.container, mKnowledgeTreeFragment!!, "knowledge")
                } else{
                    transaction.show(mKnowledgeTreeFragment!!)
                }
            }
            FRAGMENT_WECHAT -> {

                toolbar.title = getString(R.string.app_name)
                if (mWeChatFragment == null) {
                    mWeChatFragment = WeChatFragment.getInstance()
                    transaction.add(R.id.container, mWeChatFragment!!, "home")
                } else {
                    transaction.show(mWeChatFragment!!)
                }
            }
            FRAGMENT_NAVIGATION -> {
                toolbar.title = getString(R.string.app_name)
                if (mNavigationFragment == null) {
                    mNavigationFragment = NavigationFragment.getInstance()
                    transaction.add(R.id.container, mNavigationFragment!!, "home")
                } else {
                    transaction.show(mNavigationFragment!!)
                }
            }
            FRAGMENT_PROJECT -> {
                toolbar.title = getString(R.string.app_name)
                if (mProjectTreeFragment == null) {
                    mProjectTreeFragment = ProjectTreeFragment.getInstance()
                    transaction.add(R.id.container, mProjectTreeFragment!!, "home")
                } else {
                    transaction.show(mProjectTreeFragment!!)
                }
            }
        }

        transaction.commit()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mKnowledgeTreeFragment?.let { transaction.hide(it) }
        mWeChatFragment?.let { transaction.hide(it) }
        mNavigationFragment?.let { transaction.hide(it) }
        mProjectTreeFragment?.let { transaction.hide(it) }
    }

    private val onDrawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                //积分
                R.id.nav_score -> {

                }

                //收藏
                R.id.nav_collect-> {

                    if (isLogin) {
                        Intent(this@MainActivity, CommonActivity::class.java).run {
                            putExtra(Constant.TYPE_KEY, Constant.Type.COLLECT_TYPE_KEY)
                            startActivity(this)
                        }
                    } else {
                        showToast(resources.getString(R.string.login_tint))
                        Intent(this@MainActivity, LoginActivity::class.java).run {
                            startActivity(this)
                        }
                    }
                }

                //TO.DO
                R.id.nav_todo-> {

                }
                //夜间模式
                R.id.nav_night_mode-> {
                    if (SettingUtil.isNightMode()) {
                        SettingUtil.setIsNightMode(false)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        SettingUtil.setIsNightMode(true)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    //解决闪屏问题
                    window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
                    recreate()
                }
                //系统设置
                R.id.nav_setting -> {
                    Intent(this@MainActivity, SettingActivity::class.java).run {
                        startActivity(this)
                    }
                }
                //关于我
                R.id.nav_about_us -> {

                }
                //退出登录
                R.id.nav_logout -> {
                    DialogUtil.getConfirmDialog(this@MainActivity, resources.getString(R.string.confirm_logout),
                        DialogInterface.OnClickListener{ dialog, which ->
                            mPresenter?.logout()
                        }).show()
                }
            }
            true
        }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(event: LoginEvent) {
        if (event.isLogin) {
            nav_username?.text = username
            nav_view.menu.findItem(R.id.nav_logout).isVisible = true
            mHomeFragment?.lazyLoad()
            mPresenter?.getUserInfo()
        } else {
            nav_username?.text = resources.getString(R.string.go_login)
            nav_view.menu.findItem(R.id.nav_logout).isVisible = false
            mHomeFragment?.lazyLoad()
            // 重置用户信息
            nav_user_id?.text = getString(R.string.nav_line_4)
            nav_user_grade?.text = getString(R.string.nav_line_2)
            nav_user_rank?.text = getString(R.string.nav_line_2)
            nav_score?.text = ""
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent){
        if (event.isRefresh) {
            nav_view.getHeaderView(0).setBackgroundColor(mThemeColor)
            floatingActionButton.backgroundTintList = ColorStateList.valueOf(mThemeColor)
            bottomNavigation.itemIconTintList = createColorStateList(this@MainActivity, mThemeColor)
            bottomNavigation.itemTextColor = createColorStateList(this@MainActivity, mThemeColor)
        }
    }

    /**
     * 生成ColorStateList
     */
    private fun createColorStateList(context: Context, color: Int): ColorStateList? {
        val colors = intArrayOf(
//            ContextCompat.getColor(context, color),
            color,
            resources.getColor(R.color.item_desc)
        )
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_checked)
        states[1] = intArrayOf(-android.R.attr.state_checked)
        return ColorStateList(states, colors)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                Intent(this, SearchActivity::class.java).run {
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
