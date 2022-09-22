package com.bluz.bluzfilestyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderDetailPojo {

    @SerializedName("mainId")
    @Expose
    private String mainId;
    @SerializedName("orderNo")
    @Expose
    private Integer orderNo;

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

}