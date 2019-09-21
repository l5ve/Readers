package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

/**
 * 구독 정보를 저장하는 객체
 */
public class KeepItem extends WebtoonInfoItem{
    public String keepSeq;
    public String keepMemberSeq;
    public String keepDate;

    @Override
    public String toString() {
        return "KeepItem{" +
                "keepSeq='" + keepSeq + '\'' +
                ", keepMemberSeq='" + keepMemberSeq + '\'' +
                ", keepDate='" + keepDate + '\'' +
                '}';
    }
}
