package com.example.securx;


import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppListAdapter adapter;
    List<AppInfo> appList = new ArrayList<>();
//    private View loadingSpinner;
    ScanInfo sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//        startActivity(intent);
//
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AppListAdapter(new AppListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here, for example, log the position
                Log.d("App Testing", "Position : " + position);
                Log.d("App Testing", "Name : " + appList.get(position).getAppName());
                Log.d("App Testing", "apkPath : " + appList.get(position).getApkPath());

//                File apkFile = new File(appList.get(position).getApkPath());

                Intent i = new Intent(getApplicationContext(), LoadingActivity.class);

                i.putExtra("apkName", appList.get(position).getAppName());
                i.putExtra("apkPackageName", appList.get(position).getPackageName());
                i.putExtra("apkFilePath", appList.get(position).getApkPath());

                startActivity(i);
//                VirusTotalResponse res = HTTPHelper.sendAPKToCheck(apkFile);
//                assert res != null;
//                sf = new ScanInfo(res.getData().getId(), res.getData().getLinks().getSelf());

            }
        });
        recyclerView.setAdapter(adapter);

        loadInstalledApps();
    }


    private void loadInstalledApps() {
        PackageManager packageManager = getPackageManager();
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();

        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

//        List<AppInfo> appList = new ArrayList<>();

        for (ApplicationInfo appInfo : installedApps) {
            if((appInfo.flags & appInfo.FLAG_SYSTEM) != 0){
                continue;
            }
            UsageEvents usageEvents = usageStatsManager.queryEvents(currentTime - 86400000, currentTime);
            String appName = appInfo.loadLabel(packageManager).toString();
            String apkPath = appInfo.sourceDir;

            String packageName = appInfo.packageName;
            String lastUsedStatus = "Not available";

            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);

                if (event.getPackageName().equals(appInfo.packageName)) {
                    // Calculate the time since the app was last used
                    long timeSinceLastUsed = currentTime - event.getTimeStamp();
                    // Convert the time to minutes, hours, or days as needed
                    if (timeSinceLastUsed < 60000) {
                        lastUsedStatus = "Last used moments ago";
                    } else if (timeSinceLastUsed < 3600000) {
                        lastUsedStatus = "Last used " + (timeSinceLastUsed / 60000) + " minutes ago";
                    } else if (timeSinceLastUsed < 86400000) {
                        lastUsedStatus = "Last used " + (timeSinceLastUsed / 3600000) + " hours ago";
                    } else {
                        lastUsedStatus = "Last used " + (timeSinceLastUsed / 86400000) + " days ago";
                    }

                    break;
                }
            }

            appList.add(new AppInfo(appName, lastUsedStatus, apkPath, packageName));
        }

        adapter.setAppList(appList);

    }


//
//    private class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder>{
//        private List<AppInfo> appList = new ArrayList<>();
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.app_list_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            AppInfo appInfo = appList.get(position);
//            holder.appNameTextView.setText(appInfo.getAppName());
//            holder.lastUsedTextView.setText(appInfo.getLastUsedStatus());
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return appList.size();
//        }
//
//        public void setAppList(List<AppInfo> appList) {
//            this.appList = appList;
//            notifyDataSetChanged();
//        }
//
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public TextView appNameTextView;
//            public TextView lastUsedTextView;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                appNameTextView = itemView.findViewById(R.id.appNameTextView);
//                lastUsedTextView = itemView.findViewById(R.id.lastUsedTextView);
//            }
//        }
//    }
public static class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    private List<AppInfo> appList = new ArrayList<>();
    private OnItemClickListener onItemClickListener; // Define the listener interface

    // Constructor to set the listener
    public AppListAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AppInfo appInfo = appList.get(position);
        holder.appNameTextView.setText(appInfo.getAppName());
        holder.lastUsedTextView.setText(appInfo.getLastUsedStatus());

        // Set the OnClickListener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public void setAppList(List<AppInfo> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView appNameTextView;
        public TextView lastUsedTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appNameTextView = itemView.findViewById(R.id.appNameTextView);
            lastUsedTextView = itemView.findViewById(R.id.lastUsedTextView);
        }
    }
}

}
