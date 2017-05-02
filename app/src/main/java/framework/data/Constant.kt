package framework.data


import develop.y.zhzl.R
import framework.utils.UIUtils

/**
 * by y on 2016/8/7.
 */
object Constant {

    val RECYCLERVIEW_LISTVIEW = 1
    val RECYCLERVIEW_GRIDVIEW = 5

    val BITMAP_CACHE_KEY = "key"
    val ANNAL_TAG = "annal"
    val THEME_TAG = "theme"

    val LIMIT = 20
    val OFFSET = 0

    val ZHIHU = "zhihu"
    val MOVIE = "movie"
    val MUSIC = "music"
    val DEVELOP = "develop"
    val BOOK = "book"
    val INTERNET = "internet"

    val DAY_STYLES = R.style.AppTheme_Day
    val NIGHT_STYLES = R.style.AppTheme_Night

    fun getTabName(type: String): Array<String> {
        when (type) {
            Constant.ZHIHU -> return UIUtils.getStringArray(R.array.zhihu)
            Constant.MOVIE -> return UIUtils.getStringArray(R.array.movie)
            Constant.MUSIC -> return UIUtils.getStringArray(R.array.music)
            Constant.DEVELOP -> return UIUtils.getStringArray(R.array.develop)
            Constant.BOOK -> return UIUtils.getStringArray(R.array.book)
            else -> return UIUtils.getStringArray(R.array.internet)
        }
    }


    fun getSuffix(position: Int, type: String): String {
        when (type) {
            Constant.ZHIHU -> return UIUtils.getStringArray(R.array.zhihu_suffix)[position]
            Constant.MOVIE -> return UIUtils.getStringArray(R.array.movie_suffix)[position]
            Constant.MUSIC -> return UIUtils.getStringArray(R.array.music_suffix)[position]
            Constant.DEVELOP -> return UIUtils.getStringArray(R.array.develop_suffix)[position]
            Constant.BOOK -> return UIUtils.getStringArray(R.array.book_suffix)[position]
            Constant.INTERNET -> return UIUtils.getStringArray(R.array.internet_suffix)[position]
            else -> return ""
        }
    }
}
