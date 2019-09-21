package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

/**
 * 사용자 정보를 저장하는 객체
 */
public class MemberInfoItem {
    public int seq;
    public String phone;
    public String name;
    public String gender;
    public String birthday;
    public String regDate;

    @Override
    public String toString() {
        return "MemberInfoItem{" +
                "seq=" + seq +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }

}
