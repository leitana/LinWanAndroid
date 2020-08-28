package com.lx.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

/**
 * @title：MultiStatusView
 * @projectName LinWanAndroid
 * @description: 多状态View
 * @author linxiao
 * @data Created in 2020/05/29
 * @JvmOverloads注解，Kotlin就会暴露多个重载方法。
 */
class MultiStatusView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attributeSet, defStyleAttr){

    private val DEFAULT_LAYOUT_PARAMS = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT)

    private val TAG = "MultipleStatusView"

    private val STATUS_CONTENT = 0
    private val STATUS_LOADING = 1
    private val STATUS_ERROR = 2
    private val STATUS_NO_NETWORK = 3
    private val STATUS_EMPTY = 4

    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mNoNetworkView: View? = null
    private var mContentView: View? = null

    private var mEmptyViewResId: Int = 0
    private var mErrorViewResId: Int = 0
    private var mLoadingViewResId: Int = 0
    private var mNoNetworkViewResId: Int = 0
    private var mContentViewResId: Int = 0
    private val NULL_RESOURCE_ID = -1

    //装载几个异常视图id的容器
    private val mOtherIds: MutableList<Int> = ArrayList()

    private var mInflater: LayoutInflater? = null

    private var mViewStatus: Int? = null

    private var mOnRetryClickListener: OnClickListener? = null


    init {
        val typeArry = context.obtainStyledAttributes(attributeSet, R.styleable.MultiStatusView, defStyleAttr, 0)
        mEmptyViewResId = typeArry.getResourceId(R.styleable.MultiStatusView_emptyView, R.layout.empty_view)
        mErrorViewResId = typeArry.getResourceId(R.styleable.MultiStatusView_errorView, R.layout.error_view)
        mLoadingViewResId = typeArry.getResourceId(R.styleable.MultiStatusView_loadingView, R.layout.loading_view)
        mNoNetworkViewResId = typeArry.getResourceId(R.styleable.MultiStatusView_noNetworkView, R.layout.no_network_view)
        mContentViewResId = typeArry.getResourceId(R.styleable.MultiStatusView_contentView, NULL_RESOURCE_ID)
        typeArry.recycle()
        mInflater = LayoutInflater.from(getContext())
    }

    //布局全部加载后调用
    override fun onFinishInflate() {
        super.onFinishInflate()
        showContent()
    }

    //视图被销毁时
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView)
        if (mOtherIds != null){
            mOtherIds.clear()
        }
        if (mOnRetryClickListener != null) {
            mOnRetryClickListener = null
        }
    }

    //获取当前状态
    open fun getViewStatus(): Int?{
        return mViewStatus
    }

    //重试点击事件
    open fun setOnRetryClickListener(onRetryClickListener: OnClickListener){
        mOnRetryClickListener = onRetryClickListener
    }


    open fun showEmpty(){
        showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    open fun showEmpty(layoutId: Int, layoutParams: ViewGroup.LayoutParams){
        showEmpty((if (null == mEmptyView) inflateView(layoutId) else mEmptyView), layoutParams)
    }

    /**
     * 显示空视图
     */
    open fun showEmpty(view: View?, layoutParams: ViewGroup.LayoutParams?){
        checkNull(view, "Empty view is null.")
        checkNull(layoutParams, "Layout params is null.")
        mViewStatus = STATUS_EMPTY
        if (mEmptyView == null){
            mEmptyView = view
            val emptyRetryView = mEmptyView?.findViewById<View>(R.id.empty_retry_view)
            if (mOnRetryClickListener != null && emptyRetryView != null){
                emptyRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mEmptyView!!.id)
            addView(mEmptyView, 0, layoutParams)
        }
        showViewById(mEmptyView!!.id)
    }

    open fun showError(){
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    open fun showError(layoutId: Int, layoutParams: ViewGroup.LayoutParams){
        showError((if (mErrorView == null) inflateView(layoutId) else mErrorView), layoutParams)
    }

    //显示错误视图
    open fun showError(view: View?, layoutParams: ViewGroup.LayoutParams?){
        checkNull(view, "Error view is null.")
        checkNull(layoutParams, "Layout params is null.")
        mViewStatus = STATUS_ERROR
        if (mErrorView == null) {
            mErrorView = view
            val errorRetryView = mErrorView?.findViewById<View>(R.id.error_retry_view)
            if (mOnRetryClickListener != null && errorRetryView != null){
                errorRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mErrorView!!.id)
            addView(mErrorView, 0, layoutParams)
        }
        showViewById(mErrorView!!.id)
    }

    open fun showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    open fun showLoading(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showLoading((if (mLoadingView == null) inflateView(layoutId) else mLoadingView), layoutParams)
    }

    //显示加载视图
    open fun showLoading(view: View?, layoutParams: ViewGroup.LayoutParams?){
        checkNull(view, "Loading view is null.")
        checkNull(layoutParams, "Layout params is null.")
        mViewStatus = STATUS_LOADING
        if (mLoadingView == null){
            mLoadingView = view
            mOtherIds.add(mLoadingView!!.id)
            addView(mLoadingView, 0, layoutParams)
        }
        showViewById(mLoadingView!!.id)
    }

    open fun showNoNetwork(){
        showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    open fun showNoNetwork(layoutId: Int, layoutParams: ViewGroup.LayoutParams?) {
        showNoNetwork((if (mNoNetworkView == null) inflateView(layoutId) else mNoNetworkView), layoutParams)
    }

    //显示无网络视图
    open fun showNoNetwork(view: View?, layoutParams: ViewGroup.LayoutParams?) {
        checkNull(view, "No network view is null.")
        checkNull(layoutParams, "Layout params is null.")
        mViewStatus = STATUS_NO_NETWORK
        if (mNoNetworkView == null) {
            mNoNetworkView = view
            val noNetworkRetryView = mNoNetworkView?.findViewById<View>(R.id.no_network_retry_view)
            if (mOnRetryClickListener != null && noNetworkRetryView != null) {
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mNoNetworkView!!.id)
            addView(mNoNetworkView, 0, layoutParams)
        }
        showViewById(mNoNetworkView!!.id)
    }

    //显示界面本身的View
    open fun showContent(){
        mViewStatus = STATUS_CONTENT
        if (mContentView == null && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater?.inflate(mContentViewResId, null)
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS)
        }
        showContentView()
    }

    //自定义布局文件
    open fun showContent(layoutId: Int, layoutParams: ViewGroup.LayoutParams?){
        showContent(inflateView(layoutId), layoutParams)
    }
    //自定义视图
    open fun showContent(view: View?, layoutParams: ViewGroup.LayoutParams?){
        checkNull(view, "Content view is null.")
        checkNull(layoutParams, "Layout params is null.")
        mViewStatus = STATUS_CONTENT
        clear(mContentView)
        mContentView = view
        addView(mContentView, 0, layoutParams)
        showViewById(mContentView!!.id)
    }

    private fun inflateView(layoutId: Int): View{
        return mInflater!!.inflate(layoutId, null)
    }

    private fun showViewById(viewId: Int){
        val childCount: Int = childCount
        for (i in 0 until childCount){
            var view = getChildAt(i)
            view.visibility = if (view.getId() == viewId) View.VISIBLE else View.GONE
        }
    }

    //显示界面本身的View
    private fun showContentView(){
        val childView: Int = childCount
        for (i in 0 until childCount) {
            var view = getChildAt(i)
            view.visibility =
                if (mOtherIds.contains(view.id)) View.GONE else View.VISIBLE
        }
    }

    private fun checkNull(any: Any?, hint: String) {
        if (any == null) {
            throw NullPointerException(hint)
        }
    }

    private fun clear(vararg views: View?){
        for (view in views){
            removeView(view)
        }
    }


}