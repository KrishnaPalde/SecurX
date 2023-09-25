package com.example.securx;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ScanInfo {
    private String scan_id;
    private String scan_link;

    ScanInfo(String id, String link) {
        this.scan_id = id;
        this.scan_link = link;
    };


    public String getScanLink() {
        return scan_link;
    }

    public void setScanLink(String link) {
        this.scan_link = link;
    }

    public String getScan_id() {
        return scan_id;
    }

    public void setScan_id(String scan_id) {
        this.scan_id = scan_id;
    }
}
