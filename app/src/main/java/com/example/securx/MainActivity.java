package com.example.securx;


import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//        startActivity(intent);
//
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AppListAdapter();
        recyclerView.setAdapter(adapter);

        loadInstalledApps();
    }
    private void loadInstalledApps() {
        PackageManager packageManager = getPackageManager();
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();

        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        List<AppInfo> appList = new ArrayList<>();

        for (ApplicationInfo appInfo : installedApps) {
            if((appInfo.flags & appInfo.FLAG_SYSTEM) != 0){
                continue;
            }
            UsageEvents usageEvents = usageStatsManager.queryEvents(currentTime - 86400000, currentTime);
            String appName = appInfo.loadLabel(packageManager).toString();
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

            appList.add(new AppInfo(appName, lastUsedStatus));
        }

        adapter.setAppList(appList);
    }



    private class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
        private List<AppInfo> appList = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.app_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AppInfo appInfo = appList.get(position);
            holder.appNameTextView.setText(appInfo.getAppName());
            holder.lastUsedTextView.setText(appInfo.getLastUsedStatus());
        }

        @Override
        public int getItemCount() {
            return appList.size();
        }

        public void setAppList(List<AppInfo> appList) {
            this.appList = appList;
            notifyDataSetChanged();
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
