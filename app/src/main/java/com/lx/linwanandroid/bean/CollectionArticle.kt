package com.lx.linwanandroid.bean

/**
 * 收藏列表文章
 */
class CollectionArticle {
    /**
     * curPage : 1
     * datas : [{"author":"xiangcman","chapterId":314,"chapterName":"RV列表动效","courseId":13,"desc":"快速利用RecyclerView的LayoutManager搭建流式布局  ","envelopePic":"http://www.wanandroid.com/blogimgs/36badc79-fb1e-460e-8368-6898c16ba723.png","id":9977,"link":"http://www.wanandroid.com/blog/show/2112","niceDate":"2018-05-02","origin":"","originId":2829,"publishTime":1525237333000,"title":"快速利用RecyclerView的LayoutManager搭建流式布局  ","userId":1864,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 5
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
         * author : xiangcman
         * chapterId : 314
         * chapterName : RV列表动效
         * courseId : 13
         * desc : 快速利用RecyclerView的LayoutManager搭建流式布局
         * envelopePic : http://www.wanandroid.com/blogimgs/36badc79-fb1e-460e-8368-6898c16ba723.png
         * id : 9977
         * link : http://www.wanandroid.com/blog/show/2112
         * niceDate : 2018-05-02
         * origin :
         * originId : 2829
         * publishTime : 1525237333000
         * title : 快速利用RecyclerView的LayoutManager搭建流式布局
         * userId : 1864
         * visible : 0
         * zan : 0
         */
        var author: String? = null
        var chapterId = 0
        var chapterName: String? = null
        var courseId = 0
        var desc: String? = null
        var envelopePic: String? = null
        var id = 0
        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var originId = 0
        var publishTime: Long = 0
        var title: String? = null
        var userId = 0
        var visible = 0
        var zan = 0

    }
}