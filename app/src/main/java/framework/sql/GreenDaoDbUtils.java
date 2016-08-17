package framework.sql;

import java.util.List;

import develop.y.zhzl.SearchSuffix;
import develop.y.zhzl.SearchSuffixDao;

/**
 * by y on 2016/8/17.
 */
public class GreenDaoDbUtils {

    public static SearchSuffixDao getSuffixDb() {
        return GreenDaoUtils.getInstance().getSearchSuffixDao();
    }

    public static List<SearchSuffix> getSuffixAll() {
        return getSuffixDb().loadAll();
    }

    public static boolean isEmpty(String key) {
        return getSuffixDb().queryBuilder().where(SearchSuffixDao.Properties.Suffix.eq(key)).unique() == null;
    }

    public static void clear(int key) {
        getSuffixDb().deleteByKey(key);
    }

    public static void clear() {
        getSuffixDb().deleteAll();
    }

}
