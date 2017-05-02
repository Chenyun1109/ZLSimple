package framework.sql.sqlutil

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase

import framework.sql.DaoMaster
import framework.sql.DaoSession
import framework.utils.UIUtils

/**
 * by y on 2016/11/22
 */

object GreenDaoUtils {
    private val SQL_NAME = "zlsuffix"
    @SuppressLint("StaticFieldLeak")
    private var devOpenHelper: DaoMaster.DevOpenHelper? = null
    private var sqLiteDatabase: SQLiteDatabase? = null
    private var daoMaster: DaoMaster? = null

    val instance: DaoSession
        get() = SessionHolder.daoSession

    private fun getDaoMaster(): DaoMaster {
        if (daoMaster == null) {
            daoMaster = DaoMaster(getSQLiteDatabase())
        }
        return daoMaster as DaoMaster
    }

    private fun getSQLiteDatabase(): SQLiteDatabase? {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = getDevOpenHelper().writableDatabase
        }
        return sqLiteDatabase
    }

    private fun getDevOpenHelper(): DaoMaster.DevOpenHelper {
        if (devOpenHelper == null) {
            devOpenHelper = DaoMaster.DevOpenHelper(UIUtils.context, SQL_NAME, null)
        }
        return devOpenHelper as DaoMaster.DevOpenHelper
    }

    private object SessionHolder {
        internal val daoSession = getDaoMaster().newSession()
    }

}
