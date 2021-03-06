package com.example.thirdapp.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DOOR_KEY.
*/
public class DoorKeyDao extends AbstractDao<DoorKey, String> {

    public static final String TABLENAME = "DOOR_KEY";

    /**
     * Properties of entity DoorKey.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Sdk_id = new Property(0, String.class, "sdk_id", true, "SDK_ID");
        public final static Property App_key = new Property(1, String.class, "app_key", false, "APP_KEY");
        public final static Property User_id = new Property(2, String.class, "user_id", false, "USER_ID");
        public final static Property Agtcode = new Property(3, String.class, "agtcode", false, "AGTCODE");
        public final static Property AlgorithmVer = new Property(4, String.class, "algorithmVer", false, "ALGORITHM_VER");
        public final static Property Lock_name = new Property(5, String.class, "lock_name", false, "LOCK_NAME");
        public final static Property Community = new Property(6, String.class, "community", false, "COMMUNITY");
        public final static Property Lock_id = new Property(7, String.class, "lock_id", false, "LOCK_ID");
        public final static Property State = new Property(8, String.class, "state", false, "STATE");
        public final static Property Reg_time = new Property(9, String.class, "reg_time", false, "REG_TIME");
        public final static Property Sdk_type_id = new Property(10, String.class, "sdk_type_id", false, "SDK_TYPE_ID");
        public final static Property Sdk_number = new Property(11, String.class, "sdk_number", false, "SDK_NUMBER");
        public final static Property Sdk_fid = new Property(12, String.class, "sdk_fid", false, "SDK_FID");
        public final static Property Sdk_type_name = new Property(13, String.class, "sdk_type_name", false, "SDK_TYPE_NAME");
        public final static Property History_id = new Property(14, String.class, "history_id", false, "HISTORY_ID");
        public final static Property Pid = new Property(15, String.class, "pid", false, "PID");
        public final static Property Validity = new Property(16, String.class, "validity", false, "VALIDITY");
        public final static Property Ancno = new Property(17, String.class, "ancno", false, "ANCNO");
        public final static Property Auname = new Property(18, String.class, "auname", false, "AUNAME");
        public final static Property Auno = new Property(19, String.class, "auno", false, "AUNO");
        public final static Property Autel = new Property(20, String.class, "autel", false, "AUTEL");
        public final static Property Aucommon = new Property(21, String.class, "aucommon", false, "AUCOMMON");
        public final static Property Time = new Property(22, String.class, "time", false, "TIME");
    };

    private DaoSession daoSession;


    public DoorKeyDao(DaoConfig config) {
        super(config);
    }
    
    public DoorKeyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DOOR_KEY' (" + //
                "'SDK_ID' TEXT ," + // 0: sdk_id
                "'APP_KEY' TEXT," + // 1: app_key
                "'USER_ID' TEXT," + // 2: user_id
                "'AGTCODE' TEXT," + // 3: agtcode
                "'ALGORITHM_VER' TEXT," + // 4: algorithmVer
                "'LOCK_NAME' TEXT," + // 5: lock_name
                "'COMMUNITY' TEXT," + // 6: community
                "'LOCK_ID' TEXT," + // 7: lock_id
                "'STATE' TEXT," + // 8: state
                "'REG_TIME' TEXT," + // 9: reg_time
                "'SDK_TYPE_ID' TEXT," + // 10: sdk_type_id
                "'SDK_NUMBER' TEXT," + // 11: sdk_number
                "'SDK_FID' TEXT," + // 12: sdk_fid
                "'SDK_TYPE_NAME' TEXT," + // 13: sdk_type_name
                "'HISTORY_ID' TEXT," + // 14: history_id
                "'PID' TEXT," + // 15: pid
                "'VALIDITY' TEXT," + // 16: validity
                "'ANCNO' TEXT," + // 17: ancno
                "'AUNAME' TEXT," + // 18: auname
                "'AUNO' TEXT," + // 19: auno
                "'AUTEL' TEXT," + // 20: autel
                "'AUCOMMON' TEXT," + // 21: aucommon
                "'TIME' TEXT);"); // 22: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DOOR_KEY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DoorKey entity) {
        stmt.clearBindings();
//        stmt.bindString(1, entity.getSdk_id());
 
        String app_key = entity.getApp_key();
        if (app_key != null) {
            stmt.bindString(2, app_key);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(3, user_id);
        }
 
        String agtcode = entity.getAgtcode();
        if (agtcode != null) {
            stmt.bindString(4, agtcode);
        }
 
        String algorithmVer = entity.getAlgorithmVer();
        if (algorithmVer != null) {
            stmt.bindString(5, algorithmVer);
        }
 
        String lock_name = entity.getLock_name();
        if (lock_name != null) {
            stmt.bindString(6, lock_name);
        }
 
        String community = entity.getCommunity();
        if (community != null) {
            stmt.bindString(7, community);
        }
 
        String lock_id = entity.getLock_id();
        if (lock_id != null) {
            stmt.bindString(8, lock_id);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(9, state);
        }
 
        String reg_time = entity.getReg_time();
        if (reg_time != null) {
            stmt.bindString(10, reg_time);
        }
 
        String sdk_type_id = entity.getSdk_type_id();
        if (sdk_type_id != null) {
            stmt.bindString(11, sdk_type_id);
        }
 
        String sdk_number = entity.getSdk_number();
        if (sdk_number != null) {
            stmt.bindString(12, sdk_number);
        }
 
        String sdk_fid = entity.getSdk_fid();
        if (sdk_fid != null) {
            stmt.bindString(13, sdk_fid);
        }
 
        String sdk_type_name = entity.getSdk_type_name();
        if (sdk_type_name != null) {
            stmt.bindString(14, sdk_type_name);
        }
 
        String history_id = entity.getHistory_id();
        if (history_id != null) {
            stmt.bindString(15, history_id);
        }
 
        String pid = entity.getPid();
        if (pid != null) {
            stmt.bindString(16, pid);
        }
 
        String validity = entity.getValidity();
        if (validity != null) {
            stmt.bindString(17, validity);
        }
 
        String ancno = entity.getAncno();
        if (ancno != null) {
            stmt.bindString(18, ancno);
        }
 
        String auname = entity.getAuname();
        if (auname != null) {
            stmt.bindString(19, auname);
        }
 
        String auno = entity.getAuno();
        if (auno != null) {
            stmt.bindString(20, auno);
        }
 
        String autel = entity.getAutel();
        if (autel != null) {
            stmt.bindString(21, autel);
        }
 
        String aucommon = entity.getAucommon();
        if (aucommon != null) {
            stmt.bindString(22, aucommon);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(23, time);
        }
    }

    @Override
    protected void attachEntity(DoorKey entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DoorKey readEntity(Cursor cursor, int offset) {
        DoorKey entity = new DoorKey( //
            cursor.getString(offset + 0), // sdk_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // app_key
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // user_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // agtcode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // algorithmVer
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // lock_name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // community
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // lock_id
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // state
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // reg_time
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // sdk_type_id
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // sdk_number
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // sdk_fid
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // sdk_type_name
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // history_id
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // pid
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // validity
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // ancno
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // auname
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // auno
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // autel
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // aucommon
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22) // time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DoorKey entity, int offset) {
        entity.setSdk_id(cursor.getString(offset + 0));
        entity.setApp_key(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUser_id(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAgtcode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAlgorithmVer(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLock_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCommunity(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLock_id(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setState(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setReg_time(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSdk_type_id(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSdk_number(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSdk_fid(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setSdk_type_name(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setHistory_id(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setPid(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setValidity(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setAncno(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setAuname(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setAuno(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setAutel(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setAucommon(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setTime(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(DoorKey entity, long rowId) {
        return entity.getSdk_id();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(DoorKey entity) {
        if(entity != null) {
            return entity.getSdk_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
