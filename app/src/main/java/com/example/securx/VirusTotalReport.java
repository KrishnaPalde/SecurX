package com.example.securx;
import java.util.Map;

public class VirusTotalReport {
    private Meta meta;
    private Data data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Meta {
        private FileInfo file_info;

        public FileInfo getFile_info() {
            return file_info;
        }

        public void setFile_info(FileInfo file_info) {
            this.file_info = file_info;
        }
    }

    public static class FileInfo {
        private String sha256;
        private String sha1;
        private String md5;
        private int size;

        public String getSha256() {
            return sha256;
        }

        public void setSha256(String sha256) {
            this.sha256 = sha256;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class Data {
        private Attributes attributes;
        private String type;
        private String id;
        private Links links;

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }
    }

    public static class Attributes {
        private long date;
        private String status;
        private Stats stats;
        private Map<String, ScanResult> results;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Stats getStats() {
            return stats;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public Map<String, ScanResult> getResults() {
            return results;
        }

        public void setResults(Map<String, ScanResult> results) {
            this.results = results;
        }
    }

    public static class Stats {
        private int harmless;
        private int type_unsupported;
        private int suspicious;
        private int confirmed_timeout;
        private int timeout;
        private int failure;
        private int malicious;
        private int undetected;

        public int getHarmless() {
            return harmless;
        }

        public void setHarmless(int harmless) {
            this.harmless = harmless;
        }

        public int getType_unsupported() {
            return type_unsupported;
        }

        public void setType_unsupported(int type_unsupported) {
            this.type_unsupported = type_unsupported;
        }

        public int getSuspicious() {
            return suspicious;
        }

        public void setSuspicious(int suspicious) {
            this.suspicious = suspicious;
        }

        public int getConfirmed_timeout() {
            return confirmed_timeout;
        }

        public void setConfirmed_timeout(int confirmed_timeout) {
            this.confirmed_timeout = confirmed_timeout;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getFailure() {
            return failure;
        }

        public void setFailure(int failure) {
            this.failure = failure;
        }

        public int getMalicious() {
            return malicious;
        }

        public void setMalicious(int malicious) {
            this.malicious = malicious;
        }

        public int getUndetected() {
            return undetected;
        }

        public void setUndetected(int undetected) {
            this.undetected = undetected;
        }
    }

    public static class ScanResult {
        private String category;
        private String engine_name;
        private String engine_version;
        private String result;
        private String method;
        private String engine_update;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getEngine_name() {
            return engine_name;
        }

        public void setEngine_name(String engine_name) {
            this.engine_name = engine_name;
        }

        public String getEngine_version() {
            return engine_version;
        }

        public void setEngine_version(String engine_version) {
            this.engine_version = engine_version;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getEngine_update() {
            return engine_update;
        }

        public void setEngine_update(String engine_update) {
            this.engine_update = engine_update;
        }
    }

    public static class Links {
        private String item;
        private String self;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }
    }
}

