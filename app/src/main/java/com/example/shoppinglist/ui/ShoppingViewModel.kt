package com.example.shoppinglist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.db.entites.ShoppingItem
import com.example.shoppinglist.data.repository.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class ShoppingViewModel(private val repository: ShoppingRepository): ViewModel(){
    var shoppingItem : LiveData<List<ShoppingItem>> = repository.getAllShoppingItem()

    fun upsert(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }
    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }
    fun getAllShoppingItem():LiveData<List<ShoppingItem>> = repository.getAllShoppingItem()
}

sealed class UiState{
    object  Empty : UiState()
}


//class ShoppingViewModel (
//    private val repository: ShoppingRepository
//        ): ViewModel() {
//    fun upsert(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
//        repository.upsert(item)
//    }
//    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
//        repository.delete(item)
//    }
//    fun getAllShoppingItem() = repository.getAllShoppingItem()
//
//}