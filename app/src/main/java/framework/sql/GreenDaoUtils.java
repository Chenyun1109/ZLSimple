package framework.sql;


import android.database.sqlite.SQLiteDatabase;

import framework.utils.UIUtils;

public class GreenDaoUtils {

    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static DaoMaster daoMaster;
    private static final String SQL_NAME = "zlsuffix";

    private static class SessionHolder {
        public static final DaoSession daoSession = getDaoMaster().newSession();
    }

    public static DaoSession getInstance() {
        return SessionHolder.daoSession;
    }

    private static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getSQLiteDatabase());
        }
        return daoMaster;
    }

    private static SQLiteDatabase getSQLiteDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = getDevOpenHelper().getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    private static DaoMaster.DevOpenHelper getDevOpenHelper() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(UIUtils.getContext(), SQL_NAME, null);
        }
        return devOpenHelper;
    }

}
