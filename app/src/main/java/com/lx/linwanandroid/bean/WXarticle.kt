package com.lx.linwanandroid.bean

/**
 * 公众号文章
 */
class WXarticle {
    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7503,"link":"https://mp.weixin.qq.com/s/m1MhhUYi71OO9eZCU3xEwA","niceDate":"2018-11-12","origin":"","projectLink":"","publishTime":1541952000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Toast 不显示了？","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 20
     * over : false
     * pageCount : 33
     * size : 20
     * total : 641
     */
    var curPage = 0
    var offset = 0
    var isOver = false
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * apkLink :
         * author : 鸿洋
         * chapterId : 408
         * chapterName : 鸿洋
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 7503
         * link : https://mp.weixin.qq.com/s/m1MhhUYi71OO9eZCU3xEwA
         * niceDate : 2018-11-12
         * origin :
         * projectLink :
         * publishTime : 1541952000000
         * superChapterId : 408
         * superChapterName : 公众号
         * tags : [{"name":"公众号","url":"/wxarticle/list/408/1"}]
         * title : Toast 不显示了？
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */
        var apkLink: String? = null
        var author: String? = null
        var chapterId = 0
        var chapterName: String? = null
        var isCollect = false
        var courseId = 0
        var desc: String? = null
        var envelopePic: String? = null
        var isFresh = false
        var id = 0
        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var projectLink: String? = null
        var publishTime: Long = 0
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
             * name : 公众号
             * url : /wxarticle/list/408/1
             */
            var name: String? = null
            var url: String? = null

        }
    }
}