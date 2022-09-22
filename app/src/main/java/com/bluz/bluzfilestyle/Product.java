package com.bluz.bluzfilestyle;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int primaryKey;

    @ColumnInfo(name = "productID")
    public String productId;

    @ColumnInfo(name = "imageID")
    public String imageID;

    @ColumnInfo(name = "pName")
    public String pName;

    @ColumnInfo(name = "pPrice")
    public int pPrice;

    @ColumnInfo(name = "pQty")
    public int pQty;

    public Product(int primaryKey, String productId, String imageID, String pName, int pPrice, int pQty) {
        this.primaryKey = primaryKey;
        this.productId = productId;
        this.imageID = imageID;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pQty = pQty;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public int getpQty() {
        return pQty;
    }

    public void setpQty(int pQty) {
        this.pQty = pQty;
    }
}