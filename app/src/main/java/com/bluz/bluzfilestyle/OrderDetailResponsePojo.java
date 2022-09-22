package com.bluz.bluzfilestyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponsePojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalValue")
    @Expose
    private Double totalValue;
    @SerializedName("orderDetails")
    @Expose
    private OrderDetails orderDetails;

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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

}

class CustomerDetails {

    @SerializedName("ORDER_NO")
    @Expose
    private Integer orderNo;
    @SerializedName("FULL_NAME")
    @Expose
    private String fullName;
    @SerializedName("MOBILE_NO")
    @Expose
    private String mobileNo;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ORDER_STATUS")
    @Expose
    private String orderStatus;
    @SerializedName("PAYMENT_STATUS")
    @Expose
    private String paymentStatus;
    @SerializedName("STRIPE_SESSION_ID")
    @Expose
    private String stripeSessionId;

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

}

class OrderDetails {

    @SerializedName("customerDetails")
    @Expose
    private CustomerDetails customerDetails;
    @SerializedName("productList")
    @Expose
    private List<OrderDetailProduct> productList = null;

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public List<OrderDetailProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderDetailProduct> productList) {
        this.productList = productList;
    }

}

class OrderDetailProduct {

    @SerializedName("ORDER_NO")
    @Expose
    private Integer orderNo;
    @SerializedName("PRODUCT_ID")
    @Expose
    private String productId;
    @SerializedName("PRODUCT_NAME")
    @Expose
    private String productName;
    @SerializedName("PRODUCT_PRICE")
    @Expose
    private Double productPrice;
    @SerializedName("PRODUCT_IMAGE_URL")
    @Expose
    private String productImageUrl;
    @SerializedName("ORDER_PLACED_DATE")
    @Expose
    private String orderPlacedDate;
    @SerializedName("ITEM_COUNT")
    @Expose
    private Integer itemCount;

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

}
