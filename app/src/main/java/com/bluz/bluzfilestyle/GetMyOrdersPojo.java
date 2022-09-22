package com.bluz.bluzfilestyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyOrdersPojo {

    @SerializedName("mainId")
    @Expose
    private String mainId;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

}
