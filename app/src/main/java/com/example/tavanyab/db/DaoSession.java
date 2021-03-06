package com.example.tavanyab.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.Result;
import com.example.tavanyab.db.Assessment;

import com.example.tavanyab.db.ChildDao;
import com.example.tavanyab.db.ResultDao;
import com.example.tavanyab.db.AssessmentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig childDaoConfig;
    private final DaoConfig resultDaoConfig;
    private final DaoConfig assessmentDaoConfig;

    private final ChildDao childDao;
    private final ResultDao resultDao;
    private final AssessmentDao assessmentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        childDaoConfig = daoConfigMap.get(ChildDao.class).clone();
        childDaoConfig.initIdentityScope(type);

        resultDaoConfig = daoConfigMap.get(ResultDao.class).clone();
        resultDaoConfig.initIdentityScope(type);

        assessmentDaoConfig = daoConfigMap.get(AssessmentDao.class).clone();
        assessmentDaoConfig.initIdentityScope(type);

        childDao = new ChildDao(childDaoConfig, this);
        resultDao = new ResultDao(resultDaoConfig, this);
        assessmentDao = new AssessmentDao(assessmentDaoConfig, this);

        registerDao(Child.class, childDao);
        registerDao(Result.class, resultDao);
        registerDao(Assessment.class, assessmentDao);
    }
    
    public void clear() {
        childDaoConfig.clearIdentityScope();
        resultDaoConfig.clearIdentityScope();
        assessmentDaoConfig.clearIdentityScope();
    }

    public ChildDao getChildDao() {
        return childDao;
    }

    public ResultDao getResultDao() {
        return resultDao;
    }

    public AssessmentDao getAssessmentDao() {
        return assessmentDao;
    }

}
