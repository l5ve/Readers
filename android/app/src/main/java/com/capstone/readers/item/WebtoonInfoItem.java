package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

/**
 * 웹툰 정보를 저장하는 객체
 * 멤버변수 더 추가해야함
 */
@org.parceler.Parcel
public class WebtoonInfoItem {
    public int seq;
    @SerializedName("member_seq") public int memberSeq;
    public String name;
    public String platform; //연재처
    public String genre;    // 장르
    public String description;
    public String updatedDate;  // 최근 업데이트 날짜
    public boolean isCompleted; // 완결
    public boolean isKeep;  // 즐찾
    public int[] update_day;    // 업뎃 요일 월:0 화:1 ~ 일: 6
    public String[] writers;    // 작가

    @Override
    public String toString() {
        return "WebtoonInfoItem{" +
                "seq=" + seq +
                ", memberSeq=" + memberSeq +
                ", name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", isCompleted=" + isCompleted  +
                ", isKeep=" + isKeep  +
                ", update_day='" + toString(update_day) + '\'' +
                ", writers='" + toString(writers) + '\'' +
                '}';
    }

    public String toString(int[] update_day) {
        String str = "{";

        for(int i = 0; i < update_day.length; i++) {
            str = str + update_day[i];
            if (i != update_day.length - 1)
                str = str + ',';
        }

        str = str + '}';

        return str;
    }

    public String toString(String[] writers) {
        String str = "{";

        for(int i = 0; i < writers.length; i++) {
            str = str + writers[i];
            if (i != writers.length - 1)
                str = str + ',';
        }

        str = str + '}';

        return str;
    }

}
