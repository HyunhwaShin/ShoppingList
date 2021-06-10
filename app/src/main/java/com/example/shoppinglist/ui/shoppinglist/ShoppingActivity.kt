package com.example.shoppinglist.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entites.ShoppingItem
import com.example.shoppinglist.data.other.ShoppingItemAdapter
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.databinding.ActivityShoppingBinding
import com.example.shoppinglist.ui.ShoppingViewModel
import com.example.shoppinglist.ui.ShoppingViewModelFactory

class ShoppingActivity : AppCompatActivity() {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(viewModel)

        binding.rvShoppingItems.apply {
            this.layoutManager=LinearLayoutManager(applicationContext)
            this.adapter = adapter
        }

        viewModel.getAllShoppingItem().observe(this,Observer{
            adapter.submitList(it)
        })


        binding.feb.setOnClickListener{
            AddShoppingItemDialog(this,object : AddDialogListener{
                override fun onAddButtonClicked(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            }).show()
        }
    }
}

