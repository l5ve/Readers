package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        @SerializedName("name")
        private String name;

        @SerializedName("subs_num")
        private int subs_num;

        @SerializedName("bookmark_num")
        private int bookmark_num;

        @SerializedName("memo_num")
        private int memo_num;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getName() {
            return name;
        }

        public int getSubs_num() {
            return subs_num;
        }

        public int getBookmark_num() {
            return bookmark_num;
        }

        public int getMemo_num() {
            return memo_num;
        }
}
