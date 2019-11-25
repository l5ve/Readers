package com.capstone.readers.security;

import android.util.Log;

import java.security.MessageDigest;

public class SecurityUtil {
    public String encryptSHA256(String str){
        String sha = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            sha = sb.toString();
        } catch(Exception e) {
            // e.printStackTrace();
            Log.e("Encrypt Error", "NoSuchAlgorithmException");
            sha = null;
        }
        return sha;
    }
}

