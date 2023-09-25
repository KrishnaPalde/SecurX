package com.example.securx;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPHelper {



    public static VirusTotalResponse sendAPKToCheck(File apkFile) {

        try {
            Gson gson = new Gson();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                    .readTimeout(1, TimeUnit.MINUTES); // read timeout
            OkHttpClient client = builder.build();

            RequestBody formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "base.apk",
                            RequestBody.create(MediaType.parse("multipart/form-data"), apkFile))
                    .build();

            Request request = new Request.Builder()
                .url("https://www.virustotal.com/api/v3/files")
                .post(formBody)
                .addHeader("accept", "application/json")
                .addHeader("X-Apikey", "24726e35c01e7e079118dccccdb8952fa240ce806d533455e01da7a32465e250")
                .addHeader("content-type", "multipart/form-data")

                .build();


            Response response = client.newCall(request).execute();

//            Log.d("API TESTING", response.body().string());
            Log.d("API TESTING", "After Response Received");

            return gson.fromJson(response.body().string(), VirusTotalResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VirusTotalReport getScanReports(ScanInfo sf){

        try {
            Gson gson = new Gson();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                    .readTimeout(1, TimeUnit.MINUTES); // read timeout
            OkHttpClient client = builder.build();

            Request request = new Request.Builder()
                    .url(sf.getScanLink())
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("X-Apikey", "24726e35c01e7e079118dccccdb8952fa240ce806d533455e01da7a32465e250")
                    .build();


            Response response = client.newCall(request).execute();
//            Log.d("API TESTING", response.body().string().getClass().getName());

            return gson.fromJson(response.body().string(), VirusTotalReport.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
