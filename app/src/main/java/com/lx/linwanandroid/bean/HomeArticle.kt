package com.lx.linwanandroid.bean

/**
 * 首页文章
 */
class HomeArticle {
    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"tinycoder","chapterId":191,"chapterName":"数据采集与埋点",
     * "collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":7656,
     * "link":"https://juejin.im/post/5c0e4117518825369c566f07","niceDate":"15小时前","origin":"",
     * "projectLink":"","publishTime":1544631512000,"superChapterId":79,"superChapterName":"热门专题",
     * "tags":[],"title":"无埋点统计SDK实践","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 290
     * size : 20
     * total : 5798
     */
    var curPage = 0
    var offset = 0
    var isOver = false
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: MutableList<DatasBean>? =
        null

    class DatasBean {

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
        var tags: MutableList<Tag>? = null
        var top: String? = null
    }

    class Tag{
        val name: String? = null
        val url: String? = null
    }
}