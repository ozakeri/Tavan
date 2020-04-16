package com.example.tavanyab.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.tavanyab.model.Child;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelpeOld extends OrmLiteSqliteOpenHelper {

    // Fields

    public static final String DB_NAME = "tavanyab.db";
    private static final int DB_VERSION = 1;
    private RuntimeExceptionDao<Child, String> childRuntimeDao = null;

    // Public methods

    public DBHelpeOld(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
        try {

            // Create Table with given table name with columnName
            TableUtils.createTable(cs, Child.class);

        } catch (SQLException | java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {

    }

    public RuntimeExceptionDao<Child, String> getChildRuntimeDao() {
        if (childRuntimeDao == null) {
            childRuntimeDao = getRuntimeExceptionDao(Child.class);
        }
        return childRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        childRuntimeDao = null;
    }
}
