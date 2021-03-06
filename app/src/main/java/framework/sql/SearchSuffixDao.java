package framework.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SEARCH_SUFFIX".
*/
public class SearchSuffixDao extends AbstractDao<SearchSuffix, Integer> {

    public static final String TABLENAME = "SEARCH_SUFFIX";

    /**
     * Properties of entity SearchSuffix.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "ID");
        public final static Property Suffix = new Property(1, String.class, "suffix", false, "SUFFIX");
    }


    public SearchSuffixDao(DaoConfig config) {
        super(config);
    }
    
    public SearchSuffixDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SEARCH_SUFFIX\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SUFFIX\" TEXT);"); // 1: suffix
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SEARCH_SUFFIX\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SearchSuffix entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String suffix = entity.getSuffix();
        if (suffix != null) {
            stmt.bindString(2, suffix);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SearchSuffix entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String suffix = entity.getSuffix();
        if (suffix != null) {
            stmt.bindString(2, suffix);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    @Override
    public SearchSuffix readEntity(Cursor cursor, int offset) {
        SearchSuffix entity = new SearchSuffix( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // suffix
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SearchSuffix entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setSuffix(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(SearchSuffix entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public Integer getKey(SearchSuffix entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SearchSuffix entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
