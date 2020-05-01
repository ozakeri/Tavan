package com.example.tavanyab.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tavanyab.db.Assessment;
import com.example.tavanyab.db.AssessmentDao;
import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.ChildDao;
import com.example.tavanyab.db.DaoMaster;
import com.example.tavanyab.db.DaoSession;
import com.example.tavanyab.db.Result;
import com.example.tavanyab.db.ResultDao;
import com.example.tavanyab.utiles.FileUtils;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    public synchronized void createDatabaseIfChange() {
        try {
            openWritableDb();
            List<Assessment> assessmentList = getAllAssessment();
            if (assessmentList.isEmpty()) {
                initSeedData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void initSeedData() {

        try {
            InputStream myInput = null;
            myInput = context.getAssets().open("Assessment.csv");
            List<String[]> strArrayList = FileUtils.readCSVFile(myInput);
            List<Assessment> assessmentList = new ArrayList<Assessment>();
            System.out.println("strArrayList===" + strArrayList.size());

            for (String[] strArray : strArrayList) {
                Assessment assessment = new Assessment();
                assessment.setId(Long.valueOf(strArray[1]));
                assessment.setLetter_name(strArray[2]);
                assessment.setFirst_name(strArray[3]);
                assessment.setMiddle_name(strArray[4]);
                assessment.setLast_name(strArray[5]);
                assessment.setFirst_icon(strArray[6]);
                assessment.setMiddle_icon(strArray[7]);
                assessment.setLast_icon(strArray[8]);
                assessmentList.add(assessment);
            }

            myInput.close();
            System.out.println("assessmentList===" + assessmentList.size());
            bulkInsertOrUpdateAssessments(assessmentList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            Log.d(TAG, "saveOrUpdateResult: " + result.getId() + " to the schema.");
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


    public Result getResultByLetter(String letter) {
        Result result = null;
        try {
            openReadableDb();
            ResultDao resultDao = daoSession.getResultDao();
            QueryBuilder<Result> queryBuilder = resultDao.queryBuilder();
            queryBuilder.where(ResultDao.Properties.Letter_name.eq(letter));
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

    @Override
    public synchronized void bulkInsertOrUpdateAssessments(List<Assessment> lines) {
        try {
            if (lines != null && lines.size() > 0) {
                openWritableDb();
                asyncSession.insertOrReplaceInTx(Assessment.class, lines);
                assertWaitForCompletion1Sec();
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Assessment getAssessmentByKeyword(String letter) {
        Assessment assessment = null;
        try {
            openReadableDb();
            AssessmentDao assessmentDao = daoSession.getAssessmentDao();
            QueryBuilder<Assessment> queryBuilder = assessmentDao.queryBuilder();
            queryBuilder.where(AssessmentDao.Properties.Letter_name.eq(letter));
            queryBuilder.limit(1);
            List<Assessment> assessmentList = queryBuilder.list();
            if (!assessmentList.isEmpty()) {
                assessment = assessmentList.get(0);
            }

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assessment;
    }

    @Override
    public List<Assessment> getAllAssessment() {
        List<Assessment> assessments = null;
        try {
            openReadableDb();
            AssessmentDao assessmentDao = daoSession.getAssessmentDao();
            QueryBuilder<Assessment> queryBuilder = assessmentDao.queryBuilder();
            assessments = queryBuilder.list();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assessments;
    }

    @Override
    public List<Assessment> getAssessmentListById(Long assessmentId) {
        List<Assessment> assessments = null;
        try {
            openReadableDb();
            AssessmentDao assessmentDao = daoSession.getAssessmentDao();
            QueryBuilder<Assessment> queryBuilder = assessmentDao.queryBuilder();
            queryBuilder.where(AssessmentDao.Properties.Assessment_id.eq(assessmentId));
            assessments = queryBuilder.list();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assessments;
    }


    public List<Assessment> getAssessmentListByLetterName(String letterName) {
        List<Assessment> assessments = null;
        try {
            openReadableDb();
            AssessmentDao assessmentDao = daoSession.getAssessmentDao();
            QueryBuilder<Assessment> queryBuilder = assessmentDao.queryBuilder();
            queryBuilder.where(AssessmentDao.Properties.Letter_name.eq(letterName));
            assessments = queryBuilder.list();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assessments;
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

}
