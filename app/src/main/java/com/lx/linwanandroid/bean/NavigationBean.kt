package com.lx.linwanandroid.bean

/**
 * @title：NavigationBean
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/26
 */
class NavigationBean {
    var articles: List<Article>? = null
    var cid: Int = 0
    var name: String? = null

    class Article {
        /**
         * apkLink :
         * audit : 1
         * author :
         * canEdit : false
         * chapterId : 502
         * chapterName : 自助
         * collect : false
         * courseId : 13
         * desc :
         * descMd :
         * envelopePic :
         * fresh : true
         * id : 14077
         * link : https://www.jianshu.com/p/272074d518af
         * niceDate : 8小时前
         * niceShareDate : 8小时前
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1593395637000
         * realSuperChapterId : 493
         * selfVisible : 0
         * shareDate : 1593395637000
         * shareUser : gaoandroid
         * superChapterId : 494
         * superChapterName : 广场Tab
         * tags : []
         * title : Kotlin 快速入门  橘之缘之空
         * type : 0
         * userId : 26628
         * visible : 1
         * zan : 0
         */
        var apkLink: String? = null
        var audit = 0
        var author: String? = null
        var isCanEdit = false
        var chapterId = 0
        var chapterName: String? = null
        var collect = false
        var courseId = 0
        var desc: String? = null
        var descMd: String? = null
        var envelopePic: String? = null
        var fresh = false
        var id = 0
        var link: String? = null
        var niceDate: String? = null
        var niceShareDate: String? = null
        var origin: String? = null
        var prefix: String? = null
        var projectLink: String? = null
        var publishTime: Long = 0
        var realSuperChapterId = 0
        var selfVisible = 0
        var shareDate: Long = 0
        var shareUser: String? = null
        var superChapterId = 0
        var superChapterName: String? = null
        var title: String? = null
        var type = 0
        var userId = 0
        var visible = 0
        var zan = 0
        var tags: MutableList<HomeArticle.Tag>? = null
        var top: String? = null
    }
}