package framework.data;


import develop.y.zhzl.R;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class Constant {

    public static final int RECYCLERVIEW_LISTVIEW = 1;
    public static final int RECYCLERVIEW_GRIDVIEW = 5;

    public static final String BITMAP_CACHE_KEY = "key";
    public static final String ANNAL_TAG = "annal";
    public static final String THEME_TAG = "theme";

    public static final int LIMIT = 20;
    public static final int OFFSET = 0;

    public static final String ZHIHU = "zhihu";
    public static final String MOVIE = "movie";
    public static final String MUSIC = "music";
    public static final String DEVELOP = "develop";
    public static final String BOOK = "book";
    public static final String INTERNET = "internet";

    public static final boolean DAY = true;
    public static final boolean NIGHT = false;


    public static final int DAY_STYLES = R.style.AppTheme_Day;
    public static final int NIGHT_STYLES = R.style.AppTheme_Night;

    public static String[] getTabName(String type) {
        switch (type) {
            case Constant.ZHIHU:
                return UIUtils.getStringArray(R.array.zhihu);
            case Constant.MOVIE:
                return UIUtils.getStringArray(R.array.movie);
            case Constant.MUSIC:
                return UIUtils.getStringArray(R.array.music);
            case Constant.DEVELOP:
                return UIUtils.getStringArray(R.array.develop);
            case Constant.BOOK:
                return UIUtils.getStringArray(R.array.book);
            case Constant.INTERNET:
                return UIUtils.getStringArray(R.array.internet);
        }
        return new String[0];
    }


    public static String getSuffix(int position, String type) {
        switch (type) {
            case Constant.ZHIHU:
                return UIUtils.getStringArray(R.array.zhihu_suffix)[position];
            case Constant.MOVIE:
                return UIUtils.getStringArray(R.array.movie_suffix)[position];
            case Constant.MUSIC:
                return UIUtils.getStringArray(R.array.music_suffix)[position];
            case Constant.DEVELOP:
                return UIUtils.getStringArray(R.array.develop_suffix)[position];
            case Constant.BOOK:
                return UIUtils.getStringArray(R.array.book_suffix)[position];
            case Constant.INTERNET:
                return UIUtils.getStringArray(R.array.internet_suffix)[position];
            default:
                return "";
        }
    }
}
