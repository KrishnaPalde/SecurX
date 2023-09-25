package com.example.securx;


public class AppInfo {
    private String appName;
    private String lastUsedStatus;

    public AppInfo(String appName, String lastUsedStatus) {
        this.appName = appName;
        this.lastUsedStatus = lastUsedStatus;
    }

    public String getAppName() {
        return appName;
    }

    public String getLastUsedStatus() {
        return lastUsedStatus;
    }
}

