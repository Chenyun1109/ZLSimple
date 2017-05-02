package framework.sql.sqlutil


import framework.sql.SearchSuffix

/**
 * by y on 2016/11/22
 */

object SearchSuffixDb {
    fun insert(suffix: String) {
        if (GreenDaoDbUtils.isEmpty(suffix)) {
            GreenDaoDbUtils.suffixDb.insert(SearchSuffix(null, suffix))
        }
    }
}
