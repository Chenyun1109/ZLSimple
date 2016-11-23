package framework.sql;

/**
 * by y on 2016/11/22
 */

public class SearchSuffixDb {
    public static void insert(String suffix) {
        if (GreenDaoDbUtils.isEmpty(suffix)) {
            GreenDaoDbUtils.getSuffixDb().insert(new SearchSuffix(null, suffix));
        }
    }
}
