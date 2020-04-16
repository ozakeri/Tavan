package com.example.tavanyab.db.manager;

import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.Result;

import java.util.List;

public interface IDBManager {

    public void closeDbConnections();

    public void createDatabase();

    public void dropDatabase();

    public Child getChildById(Long childId);

    public List<Child> getChildList();

    public List<Child> getChildListByKeyword(String keyword, Integer maxResultSize);

    public Child insertChild(Child child);

    public Child saveOrUpdateChild(Child child);

    public void deleteChild(Child child);

    //************************************************
    public Result getResultByChildId(Long resultId);

    public List<Result> getResultListByChildId(Long childId);

    public Result insertResult(Result result);

    public Result saveOrUpdateResult(Result result);

    public Result getResultByChildIdAndLetter(Long childId, String letter);

}
