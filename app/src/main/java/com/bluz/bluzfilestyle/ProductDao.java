package com.bluz.bluzfilestyle;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertRecord(Product product);

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE productID =:pID)")
    Boolean isExists(String pID);

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Query("DELETE FROM Product WHERE productID =:pID")
    void deleteById(String pID);

    @Query("UPDATE Product SET pQty =:pQty WHERE productID =:pID")
    void updateData(int pQty, String pID);

    @Query("DELETE FROM Product")
    void deleteAll();

}
