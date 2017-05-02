package framework.sql.sqlutil

import framework.sql.SearchSuffix
import framework.sql.SearchSuffixDao

/**
 * by y on 2016/11/22
 */

object GreenDaoDbUtils {
    val suffixDb: SearchSuffixDao
        get() = GreenDaoUtils.instance.searchSuffixDao

    val suffixAll: List<SearchSuffix>
        get() = suffixDb.loadAll()

    fun isEmpty(key: String): Boolean {
        return suffixDb.queryBuilder().where(SearchSuffixDao.Properties.Suffix.eq(key)).unique() == null
    }

    fun clear(key: Int) {
        suffixDb.deleteByKey(key)
    }

    fun clear() {
        suffixDb.deleteAll()
    }

}
