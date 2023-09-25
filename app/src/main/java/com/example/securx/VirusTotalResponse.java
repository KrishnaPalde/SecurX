package com.example.securx;

public class VirusTotalResponse {
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            private String type;
            private String id;
            private Links links;

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

        public static class Links {
            private String self;

            public String getSelf() {
                return self;
            }

            public void setSelf(String self) {
                this.self = self;
            }
        }


}
