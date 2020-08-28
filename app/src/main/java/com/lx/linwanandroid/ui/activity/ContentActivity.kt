package com.lx.linwanandroid.ui.activity

import android.net.http.SslError
import android.os.Build
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.*
import com.lx.linwanandroid.R
import com.lx.linwanandroid.base.BaseMvpStatusViewActivity
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ext.getAgentWeb
import com.lx.linwanandroid.mvp.contract.ContentContract
import com.lx.linwanandroid.mvp.presenter.ContentPresenter
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @title：ContentActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/07/10
 */
class ContentActivity: BaseMvpStatusViewActivity<ContentContract.View, ContentContract.Presenter>(),
    ContentContract.View{

    private var agentWeb: AgentWeb? = null
    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    private lateinit var shareTitle: String
    private lateinit var shareUrl: String
    private var shareId: Int = 0


    override fun createPresenter(): ContentContract.Presenter = ContentPresenter()

    override fun start() {
    }

    override fun getLayoutId(): Int = R.layout.activity_content

    override fun initView() {
        super.initView()
        toolbar.apply {
            title = ""//getString(R.string.loading)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setHomeButtonEnabled(true)//不知为何不起效果
            setNavigationOnClickListener { finish() }
            //StatusBarUtil2.setPaddingSmart(this@ContentActivity, toolbar)
        }
        tv_title.apply {
            text = getString(R.string.loading)
            visibility = View.VISIBLE
            postDelayed({
                tv_title.isSelected = true
            }, 2000)
        }

        intent.extras?.let {
            shareId = it.getInt(Constant.CONTENT_ID_KEY, -1)
            shareTitle = it.getString(Constant.CONTENT_TITLE_KEY, "")
            shareUrl = it.getString(Constant.CONTENT_URL_KEY, "")
        }

        initWebView()
    }

    override fun initData() {
    }

    override fun showCollectSuccess(success: Boolean) {
    }

    override fun showCancelColletcSuccess(success: Boolean) {
    }

    override fun onBackPressed() {
        agentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun initWebView(){
        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

//        agentWeb = AgentWeb.with(this)
//            .setAgentWebParent(cl_main, 1, layoutParams)//传入AgentWeb 的父控件
//            .useDefaultIndicator()// 使用默认进度条
//            .setWebView(mWebView)
//            .setWebChromeClient(webChromeClient)
//            .setWebViewClient(webViewClient)
//            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
//            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
//            .createAgentWeb()//
//            .ready()
//            .go(shareUrl)

        agentWeb = shareUrl.getAgentWeb(
            this,
            cl_main,
            layoutParams,
            mWebView,
            webChromeClient,
            webViewClient
        )

        agentWeb?.webCreator?.webView?.let {
            it.settings.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    /**
     * webViewClient
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            // super.onReceivedSslError(view, handler, error)
            handler?.proceed()
        }
    }

    /**
     * webChromeClient
     */
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            title.let {
                // toolbar.title = it
                tv_title.text = it
            }
        }
    }
}