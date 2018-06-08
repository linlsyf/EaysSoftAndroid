package com.core.db.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.core.db.greenDao.entity.Video;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VIDEO".
*/
public class VideoDao extends AbstractDao<Video, String> {

    public static final String TABLENAME = "VIDEO";

    /**
     * Properties of entity Video.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Data = new Property(2, String.class, "data", false, "DATA");
        public final static Property ThumbPath = new Property(3, String.class, "thumbPath", false, "THUMB_PATH");
        public final static Property Duration = new Property(4, long.class, "duration", false, "DURATION");
        public final static Property Size = new Property(5, long.class, "size", false, "SIZE");
        public final static Property DurationString = new Property(6, String.class, "durationString", false, "DURATION_STRING");
    };


    public VideoDao(DaoConfig config) {
        super(config);
    }
    
    public VideoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VIDEO\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"DATA\" TEXT," + // 2: data
                "\"THUMB_PATH\" TEXT," + // 3: thumbPath
                "\"DURATION\" INTEGER NOT NULL ," + // 4: duration
                "\"SIZE\" INTEGER NOT NULL ," + // 5: size
                "\"DURATION_STRING\" TEXT);"); // 6: durationString
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VIDEO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Video entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(3, data);
        }
 
        String thumbPath = entity.getThumbPath();
        if (thumbPath != null) {
            stmt.bindString(4, thumbPath);
        }
        stmt.bindLong(5, entity.getDuration());
        stmt.bindLong(6, entity.getSize());
 
        String durationString = entity.getDurationString();
        if (durationString != null) {
            stmt.bindString(7, durationString);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Video entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(3, data);
        }
 
        String thumbPath = entity.getThumbPath();
        if (thumbPath != null) {
            stmt.bindString(4, thumbPath);
        }
        stmt.bindLong(5, entity.getDuration());
        stmt.bindLong(6, entity.getSize());
 
        String durationString = entity.getDurationString();
        if (durationString != null) {
            stmt.bindString(7, durationString);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Video readEntity(Cursor cursor, int offset) {
        Video entity = new Video( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // data
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // thumbPath
            cursor.getLong(offset + 4), // duration
            cursor.getLong(offset + 5), // size
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // durationString
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Video entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setData(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setThumbPath(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDuration(cursor.getLong(offset + 4));
        entity.setSize(cursor.getLong(offset + 5));
        entity.setDurationString(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Video entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Video entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
