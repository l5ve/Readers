package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MemoResponse {
        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        @SerializedName("content")
        private String content;

        @SerializedName("memo_date")
        private Timestamp memo_date;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getContent() {
            return content;
        }

        public String getMemo_date(){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(memo_date);
        }
}
