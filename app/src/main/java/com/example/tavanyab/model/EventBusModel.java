package com.example.tavanyab.model;

public class EventBusModel {
    private String currentPage;

    public EventBusModel(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
