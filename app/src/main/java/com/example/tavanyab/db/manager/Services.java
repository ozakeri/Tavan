package com.example.tavanyab.db.manager;

import com.example.tavanyab.db.Assessment;
import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.Result;

import java.util.List;

public class Services {
    private IDBManager databaseManager;

    public Services() {
    }

    public Services(IDBManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public IDBManager getDatabaseManager() {
        return databaseManager;
    }

    public Child getChildById(Long childId) {
        return databaseManager.getChildById(childId);
    }

    public List<Child> getChildList() {
        return databaseManager.getChildList();
    }

    public List<Child> getChildListByKeyword(String keyword, Integer maxResultSize) {
        return databaseManager.getChildListByKeyword(keyword, maxResultSize);
    }

    public Child insertChild(Child child) {
        return databaseManager.insertChild(child);
    }

    public Child saveOrUpdateChild(Child child) {
        return databaseManager.saveOrUpdateChild(child);
    }

    void deleteChild(Child child) {
        databaseManager.deleteChild(child);
    }

    //*****************************************************************
    public Result getResultByChildId(Long resultId) {
        return databaseManager.getResultByChildId(resultId);
    }

    public List<Result> getResultListByChildId(Long childId) {
        return databaseManager.getResultListByChildId(childId);
    }

    public Result insertResult(Result result) {
        return databaseManager.insertResult(result);
    }

    public Result saveOrUpdateResult(Result result) {
        return databaseManager.saveOrUpdateResult(result);
    }

    public Result getResultByChildIdAndLetter(Long childId, String letter) {
        return databaseManager.getResultByChildIdAndLetter(childId, letter);
    }

    public Result getResultByLetter(String letter) {
        return databaseManager.getResultByLetter(letter);
    }

    public void bulkInsertOrUpdateAssessments(List<Assessment> assessmentList) {
        databaseManager.bulkInsertOrUpdateAssessments(assessmentList);
    }

    public Assessment getAssessmentByKeyword(String letter) {
        return databaseManager.getAssessmentByKeyword(letter);
    }

    public List<Assessment> getAllAssessment() {
        return databaseManager.getAllAssessment();
    }

    public List<Assessment> getAssessmentListById(Long assessmentId) {
        return databaseManager.getAssessmentListById(assessmentId);
    }

    public List<Assessment> getAssessmentListByLetterName(String letterName) {
        return databaseManager.getAssessmentListByLetterName(letterName);
    }
}
