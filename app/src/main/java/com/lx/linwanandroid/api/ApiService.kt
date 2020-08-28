package com.lx.linwanandroid.api

import com.lx.linwanandroid.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @title：ApiService
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/17
 */
interface ApiService {
    /**
     * 登录
     * https://www.wanandroid.com/user/login
     * [username] 用户名
     * [password] 密码
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String)
            : Observable<BaseResponse<Login>>

    /**
     * 注册
     * https://www.wanandroid.com/user/register
     * [username]
     * [password]
     * [repassword]
     */
    @POST("user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String)
            : Observable<BaseResponse<Login>>

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout() : Observable<BaseResponse<Any>>

    /**
     * 获取积分
     * https://www.wanandroid.com/lg/coin/userinfo/json
     */
    @GET("lg/coin/userinfo/json")
    fun getUserInfo(): Observable<BaseResponse<UserInfoBody>>

    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    fun getBanners(): Observable<BaseResponse<List<Banner>>>

    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    fun getTopArticles(): Observable<BaseResponse<MutableList<HomeArticle.DatasBean>>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * [pageNum]
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Observable<BaseResponse<HomeArticle>>



    /*收藏*/

    /**
     * 获取收藏列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     * [page]
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Observable<BaseResponse<CollectionArticle>>

    /**
     * 收藏站内文章
     * https://www.wanandroid.com/lg/collect/1165/json
     * [id]
     */
    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<BaseResponse<Any>>

    /**
     * 收藏站外文章
     * http://www.wanandroid.com/lg/collect/add/json
     * [title] 标题
     * [author] 作者
     * [link] 链接
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun addCoolectOutsideArticle(@Field("title") title: String,
                                 @Field("author") author: String,
                                 @Field("link") link: String): Observable<BaseResponse<Any>>

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * [id]
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Observable<BaseResponse<Any>>

    /**
     * 收藏列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect/2805/json
     *
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(@Path("id") id: Int,
                             @Field("originId") originId: Int = -1): Observable<BaseResponse<Any>>

    /**
     * 知识体系
     * https://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Observable<BaseResponse<MutableList<KnowledgeSystem>>>


    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     */
    @GET("article/list/{page}/json")
    fun getKnowledge(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<KnowledgeSysArticle>>

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    fun getWXChapters(): Observable<BaseResponse<MutableList<WXchapters>>>

    /**
     * 导航数据
     * https://www.wanandroid.com/navi/json
     */
    @GET("navi/json")
    fun getNavigation(): Observable<BaseResponse<List<NavigationBean>>>

    /**
     * https://www.wanandroid.com/project/tree/json
     * 项目分类
     */
    @GET("project/tree/json")
    fun getProjectTree(): Observable<BaseResponse<List<ProjectTreeBean>>>

    /**
     * https://www.wanandroid.com/project/list/1/json?cid=294
     * 项目列表数据
     */
    @GET("project/list/{page}/json")
    fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<ProjectArticle>>

    /**
     * https://www.wanandroid.com/hotkey/json
     * 搜索热词
     */
    @GET("hotkey/json")
    fun getSearchHotBean(): Observable<BaseResponse<MutableList<SearchHotBean>>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun queryBySearchKey(@Path("page") page: Int,
                         @Field("k") key: String): Observable<BaseResponse<ProjectArticle>>
}