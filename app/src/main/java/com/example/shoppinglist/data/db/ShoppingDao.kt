package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.data.db.entites.ShoppingItem

@Dao
interface ShoppingDao {
    //이미 저장된 항목이 있을 경우, 데이터를 덮어 씌우라는 의미

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item : ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getAllItem(): LiveData<List<ShoppingItem>>

}