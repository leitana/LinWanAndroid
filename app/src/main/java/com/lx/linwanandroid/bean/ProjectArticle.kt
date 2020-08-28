package com.lx.linwanandroid.bean

/**
 * 项目列表
 */
class ProjectArticle {
    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"littledavid-tech","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"这个算是对Android学习总结，MVP架构+好多轮子","envelopePic":"http://wanandroid.com/blogimgs/9be242c9-e53e-4a54-9f49-d69b04b463b9.png","fresh":false,"id":7641,"link":"http://www.wanandroid.com/blog/show/2449","niceDate":"2天前","origin":"","projectLink":"https://github.com/littledavid-tech/WanAndroidApp","publishTime":1544499146000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"我的涂鸦之作WanAndroid第三方客户端","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 7
     * size : 15
     * total : 102
     */
    var curPage = 0
    var offset = 0
    var over = false
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * apkLink :
         * author : littledavid-tech
         * chapterId : 294
         * chapterName : 完整项目
         * collect : false
         * courseId : 13
         * desc : 这个算是对Android学习总结，MVP架构+好多轮子
         * envelopePic : http://wanandroid.com/blogimgs/9be242c9-e53e-4a54-9f49-d69b04b463b9.png
         * fresh : false
         * id : 7641
         * link : http://www.wanandroid.com/blog/show/2449
         * niceDate : 2天前
         * origin :
         * projectLink : https://github.com/littledavid-tech/WanAndroidApp
         * publishTime : 1544499146000
         * superChapterId : 294
         * superChapterName : 开源项目主Tab
         * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
         * title : 我的涂鸦之作WanAndroid第三方客户端
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */
        var apkLink: String? = null
        var audit: Int = 0
        var author: String? = null
        var canEdit = false
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
        var origin: String? = null
        var prefix: String? = null
        var projectLink: String? = null
        var publishTime: Long = 0
        var realSuperChapterId: Long = 0
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
        var tags: List<TagsBean>? =
            null

        class TagsBean {
            /**
             * name : 项目
             * url : /project/list/1?cid=294
             */
            var name: String? = null
            var url: String? = null

        }
    }
}