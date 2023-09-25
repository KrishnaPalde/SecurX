package com.example.securx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class LoadingActivity extends AppCompatActivity {
    ScanInfo sf;
    File apkFile;
    private static final long LOADING_DELAY = 3000; // 3 seconds
    String appName;
    String packageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        Intent i = getIntent();

        if(i != null){
            apkFile = new File(i.getStringExtra("apkFilePath"));
            appName = i.getStringExtra("apkName");
            packageName = i.getStringExtra("apkPackageName");

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                VirusTotalResponse res = HTTPHelper.sendAPKToCheck(apkFile);
                sf = new ScanInfo(res.getData().getId(), res.getData().getLinks().getSelf());
                VirusTotalReport report = HTTPHelper.getScanReports(sf);
                if((report.getData().getAttributes().getStats().getMalicious() == 0) && (report.getData().getAttributes().getStats().getSuspicious() == 0)){
                    Log.d("APP TESTING", String.valueOf(report.getData().getAttributes().getStats().getUndetected()));
                    startNextActivity(false, appName, packageName, report.getData().getAttributes().getStats().getUndetected());
                } else {
                    startNextActivity(true, appName, packageName, report.getData().getAttributes().getStats().getUndetected());
                }

            }
        }, LOADING_DELAY);
    }

    private void startNextActivity(boolean isMalicious, String apkName, String packageName, int totalNumberOfAntivirusUndetected) {
        Intent intent = new Intent(this, MaliciousAppActivity.class);
        intent.putExtra("appName", apkName); // Replace with the actual app name
        intent.putExtra("packageName", packageName); // Replace with the actual package name
        intent.putExtra("isMalicious", isMalicious); // Replace with the actual malicious status
        intent.putExtra("undetectedBy", totalNumberOfAntivirusUndetected);
        startActivity(intent);

        finish(); // Close this loading activity
    }

}

