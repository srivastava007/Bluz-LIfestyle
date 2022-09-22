package com.bluz.bluzfilestyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderResponsePojo {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderList")
    @Expose
    private List<MyOrders> orderList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MyOrders> getOrdersList() {
        return orderList;
    }

    public void setOrdersList(List<MyOrders> orderList) {
        this.orderList = orderList;
    }

}
class MyOrders {

    @SerializedName("ORDER_NO")
    @Expose
    private Integer orderNo;
    @SerializedName("MOBILE_NO")
    @Expose
    private String mobileNo;
    @SerializedName("FULL_NAME")
    @Expose
    private String fullName;
    @SerializedName("FULL_ADDRESS")
    @Expose
    private String fullAddress;
    @SerializedName("PINCODE")
    @Expose
    private Integer pincode;
    @SerializedName("ORDER_STATUS")
    @Expose
    private String orderStatus;
    @SerializedName("ORDER_PLACED_DATE")
    @Expose
    private String orderPlacedDate;
    @SerializedName("PRODUCT_LIST")
    @Expose
    private List<OrderedProduct> productList = null;
    @SerializedName("PRODUCT_SUM")
    @Expose
    private Double productSum;
    @SerializedName("PaymentStatus")
    @Expose
    private Object paymentStatus;

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public List<OrderedProduct> getProductList() {
        return productList;
    }

    public void setOrderedProductList(List<OrderedProduct> productList) {
        this.productList = productList;
    }

    public Double getProductSum() {
        return productSum;
    }

    public void setProductSum(Double productSum) {
        this.productSum = productSum;
    }

    public Object getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
class OrderedProduct {

    @SerializedName("PRODUCT_NAME")
    @Expose
    private String productName;
    @SerializedName("PRODUCT_PRICE")
    @Expose
    private Double productPrice;
    @SerializedName("PRODUCT_IMAGE_URL")
    @Expose
    private String productImageUrl;
    @SerializedName("PRODUCT_ID")
    @Expose
    private String productId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}