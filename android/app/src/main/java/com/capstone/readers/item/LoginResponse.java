package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        @SerializedName("name")
        private String name;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getName() { return name; }
}
