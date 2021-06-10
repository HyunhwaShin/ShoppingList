package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entites.ShoppingItem

class ShoppingRepository (
    private val db : ShoppingDatabase
    ) {
    suspend fun upsert(item : ShoppingItem) = db.getShoppingDao().upsert(item)
    suspend fun delete(item : ShoppingItem) = db.getShoppingDao().delete(item)

    fun getAllShoppingItem():LiveData<List<ShoppingItem>> = db.getShoppingDao().getAllItem()
}