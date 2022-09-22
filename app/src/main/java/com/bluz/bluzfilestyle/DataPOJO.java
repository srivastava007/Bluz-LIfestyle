package com.bluz.bluzfilestyle;

public class DataPOJO {

    private int SNO;
    private int PRODUCT_CATEGORY_ID;
    private String PRODUCT_SUB_CATEGOTY_NAME;
    private String PRODUCT_NAME;
    private String PRODUCT_DESCRIPTION;
    private int PRODUCT_RATING;
    private String PRODUCT_PRICE;
    private String PRODUCT_OFFER_PRICE;
    private int PRODUCT_OFFER_DISCOUNT;
    private String PRODUCT_IMAGE_URL;
    private String PRODUCT_IMAGE_URL_LIST;
    private String PRODUCT_ID;
    private int status;
    private String SORT_DESCRIPTION;

    public DataPOJO(int sno, int product_category_id, String product_sub_categoty_name, String product_name, String product_description, int product_rating, String product_price, String product_offer_price, int product_offer_discount, String product_image_url, String product_image_url_list, String product_id, int status, String sort_description) {
        SNO = sno;
        PRODUCT_CATEGORY_ID = product_category_id;
        PRODUCT_SUB_CATEGOTY_NAME = product_sub_categoty_name;
        PRODUCT_NAME = product_name;
        PRODUCT_DESCRIPTION = product_description;
        PRODUCT_RATING = product_rating;
        PRODUCT_PRICE = product_price;
        PRODUCT_OFFER_PRICE = product_offer_price;
        PRODUCT_OFFER_DISCOUNT = product_offer_discount;
        PRODUCT_IMAGE_URL = product_image_url;
        PRODUCT_IMAGE_URL_LIST = product_image_url_list;
        PRODUCT_ID = product_id;
        this.status = status;
        SORT_DESCRIPTION = sort_description;
    }

    public int getSNO() {
        return SNO;
    }

    public void setSNO(int SNO) {
        this.SNO = SNO;
    }

    public int getPRODUCT_CATEGORY_ID() {
        return PRODUCT_CATEGORY_ID;
    }

    public void setPRODUCT_CATEGORY_ID(int PRODUCT_CATEGORY_ID) {
        this.PRODUCT_CATEGORY_ID = PRODUCT_CATEGORY_ID;
    }

    public String getPRODUCT_SUB_CATEGOTY_NAME() {
        return PRODUCT_SUB_CATEGOTY_NAME;
    }

    public void setPRODUCT_SUB_CATEGOTY_NAME(String PRODUCT_SUB_CATEGOTY_NAME) {
        this.PRODUCT_SUB_CATEGOTY_NAME = PRODUCT_SUB_CATEGOTY_NAME;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public String getPRODUCT_DESCRIPTION() {
        return PRODUCT_DESCRIPTION;
    }

    public void setPRODUCT_DESCRIPTION(String PRODUCT_DESCRIPTION) {
        this.PRODUCT_DESCRIPTION = PRODUCT_DESCRIPTION;
    }

    public int getPRODUCT_RATING() {
        return PRODUCT_RATING;
    }

    public void setPRODUCT_RATING(int PRODUCT_RATING) {
        this.PRODUCT_RATING = PRODUCT_RATING;
    }

    public String getPRODUCT_PRICE() {
        return PRODUCT_PRICE;
    }

    public void setPRODUCT_PRICE(String PRODUCT_PRICE) {
        this.PRODUCT_PRICE = PRODUCT_PRICE;
    }

    public String getPRODUCT_OFFER_PRICE() {
        return PRODUCT_OFFER_PRICE;
    }

    public void setPRODUCT_OFFER_PRICE(String PRODUCT_OFFER_PRICE) {
        this.PRODUCT_OFFER_PRICE = PRODUCT_OFFER_PRICE;
    }

    public int getPRODUCT_OFFER_DISCOUNT() {
        return PRODUCT_OFFER_DISCOUNT;
    }

    public void setPRODUCT_OFFER_DISCOUNT(int PRODUCT_OFFER_DISCOUNT) {
        this.PRODUCT_OFFER_DISCOUNT = PRODUCT_OFFER_DISCOUNT;
    }

    public String getPRODUCT_IMAGE_URL() {
        return PRODUCT_IMAGE_URL;
    }

    public void setPRODUCT_IMAGE_URL(String PRODUCT_IMAGE_URL) {
        this.PRODUCT_IMAGE_URL = PRODUCT_IMAGE_URL;
    }

    public String getPRODUCT_IMAGE_URL_LIST() {
        return PRODUCT_IMAGE_URL_LIST;
    }

    public void setPRODUCT_IMAGE_URL_LIST(String PRODUCT_IMAGE_URL_LIST) {
        this.PRODUCT_IMAGE_URL_LIST = PRODUCT_IMAGE_URL_LIST;
    }

    public String getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(String PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSORT_DESCRIPTION() {
        return SORT_DESCRIPTION;
    }

    public void setSORT_DESCRIPTION(String SORT_DESCRIPTION) {
        this.SORT_DESCRIPTION = SORT_DESCRIPTION;
    }
}