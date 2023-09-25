package com.example.securx;


public class AppInfo {
    private String appName;
    private String lastUsedStatus;
    private String apkPath;
    private String packageName;

    public AppInfo(String appName, String lastUsedStatus, String apkPath, String packageName) {
        this.appName = appName;
        this.lastUsedStatus = lastUsedStatus;
        this.apkPath = apkPath;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public String getApkPath() {
        return apkPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getLastUsedStatus() {
        return lastUsedStatus;
    }
}

