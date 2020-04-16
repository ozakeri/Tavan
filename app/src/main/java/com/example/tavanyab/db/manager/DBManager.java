package com.example.tavanyab.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.ChildDao;
import com.example.tavanyab.db.DaoMaster;
import com.example.tavanyab.db.DaoSession;
import com.example.tavanyab.db.Result;
import com.example.tavanyab.db.ResultDao;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DBManager implements IDBManager, AsyncOperationListener {
    private final String DATABASE_NAME = "database.db";
    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DBManager.class.getCanonicalName();
    /**
     * Instance of DatabaseManager
     */
    private static DBManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link android.content.Context}.
     */
    public DBManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, DATABASE_NAME, null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }

    /**
     * @param context The Android {@link android.content.Context}.
     * @return this.instance
     */
    public static DBManager getInstance(Context context) {

        if (instance == null) {
            instance = new DBManager(context);
        }

        return instance;
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized void createDatabase() {
        try {
            openWritableDb();
            mHelper.onCreate(database);              // creates the tables
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            Log.d(TAG, "Old db version: " + database.getVersion());

            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(Child.class);    // clear all elements from a table
//            context.deleteDatabase(DATABASE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    @Override
    public Child getChildById(Long childId) {
        Child child = null;
        try {
            openReadableDb();
            ChildDao childDao = daoSession.getChildDao();
            child = childDao.loadByRowId(childId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return child;
    }

    @Override
    public List<Child> getChildList() {
        List<Child> children = null;
        try {
            openReadableDb();
            ChildDao childDao = daoSession.getChildDao();
            children = childDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    @Override
    public List<Child> getChildListByKeyword(String keyword, Integer maxResultSize) {
        List<Child> children = null;
        try {
            openReadableDb();
            ChildDao childDao = daoSession.getChildDao();
            QueryBuilder<Child> queryBuilder = childDao.queryBuilder();

            queryBuilder.whereOr(ChildDao.Properties.First_name.like("%" + keyword + "%"), ChildDao.Properties.Last_name.like("%" + keyword + "%"));

            if (maxResultSize != null) {
                queryBuilder = queryBuilder.limit(maxResultSize);
            }
            children = queryBuilder.list();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    @Override
    public Child insertChild(Child child) {
        try {
            if (child != null) {
                openWritableDb();
                ChildDao stationDao = daoSession.getChildDao();
                stationDao.insert(child);
                Log.d(TAG, "Inserted child: " + child.getId() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return child;
    }

    @Override
    public Child saveOrUpdateChild(Child child) {
        try {
            openWritableDb();
            ChildDao childDao = daoSession.getChildDao();
            childDao.insertOrReplace(child);
            Log.d(TAG, "Inserted or Replace child: " + child.getId() + " to the schema.");
            daoSession.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return child;
    }

    @Override
    public synchronized void deleteChild(Child child) {
        try {
            if (child != null) {
                openWritableDb();
                daoSession.delete(child);
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //**********************************************************************************************
    @Override
    public Result getResultByChildId(Long resultId) {
        Result result = null;
        try {
            openReadableDb();
            ResultDao resultDao = daoSession.getResultDao();
            result = resultDao.loadByRowId(resultId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Result> getResultListByChildId(Long childId) {
        List<Result> results = null;
        try {
            openReadableDb();
            ResultDao resultDao = daoSession.getResultDao();
            QueryBuilder<Result> queryBuilder = resultDao.queryBuilder();
            queryBuilder.where(ResultDao.Properties.Child_id.eq(childId));
            results = queryBuilder.list();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public Result insertResult(Result result) {
        try {
            if (result != null) {
                openWritableDb();
                ResultDao resultDao = daoSession.getResultDao();
                resultDao.insert(result);
                Log.d(TAG, "Inserted result: " + result.getId() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result saveOrUpdateResult(Result result) {
        try {
            openWritableDb();
            ResultDao resultDao = daoSession.getResultDao();
            resultDao.insertOrReplace(result);
            Log.d(TAG, "Inserted or Replace result: " + result.getId() + " to the schema.");
            daoSession.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getResultByChildIdAndLetter(Long childId, String letter) {
        Result result = null;
        try {
            openReadableDb();
            ResultDao resultDao = daoSession.getResultDao();
            QueryBuilder<Result> queryBuilder = resultDao.queryBuilder();
            queryBuilder.where(ResultDao.Properties.Child_id.eq(childId), ResultDao.Properties.Letter_name.eq(letter));
            queryBuilder.limit(1);
            List<Result> resultList = queryBuilder.list();
            if (!resultList.isEmpty()) {
                result = resultList.get(0);
            }

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
