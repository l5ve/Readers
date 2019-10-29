package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;


        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
}
