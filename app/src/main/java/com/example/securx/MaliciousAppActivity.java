package com.example.securx;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MaliciousAppActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malicious_app_layout);

        // Get references to UI elements
        TextView appNameTextView = findViewById(R.id.appNameTextView);
        TextView packageNameTextView = findViewById(R.id.packageNameTextView);
        TextView maliciousTextView = findViewById(R.id.maliciousTextView);
        TextView scannedNumber = findViewById(R.id.scannedNumber);
        Button homeBtn = findViewById(R.id.homeButton);

        // Retrieve data from the previous activity
        Intent intent = getIntent();
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        String appName = intent.getStringExtra("appName");
        String packageName = intent.getStringExtra("packageName");
        boolean isMalicious = intent.getBooleanExtra("isMalicious", false);
        int unDetectedBy = intent.getIntExtra("undetectedBy", 62);

        // Set the app name and package name in the UI
        appNameTextView.setText(appName);
        packageNameTextView.setText(packageName);
        scannedNumber.setText(String.format("Scanned By %d Antiviruses", unDetectedBy));

        // Set the malicious status based on the boolean value
        if (isMalicious) {
            maliciousTextView.setText("Malicious");
            maliciousTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            maliciousTextView.setText("Not Malicious");
            maliciousTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }
}

