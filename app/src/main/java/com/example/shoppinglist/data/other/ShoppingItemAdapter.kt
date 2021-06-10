package com.example.shoppinglist.data.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entites.ShoppingItem
import com.example.shoppinglist.databinding.ShoppingItemBinding
import com.example.shoppinglist.ui.ShoppingViewModel
import kotlinx.android.synthetic.main.shopping_item.view.*

class ShoppingItemAdapter(
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    inner class ShoppingViewHolder(private val binding : ShoppingItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ShoppingItem) {
            binding.apply {
                tvName.text = item.name
                tvAmount.text = "${item.amount}"

                ivDelete.setOnClickListener{
                    viewModel.delete(item)
                }
                ivMinus.setOnClickListener {
                    if (item.amount>0){
                        item.amount--
                        viewModel.upsert(item)
                    }
                }
                ivPlus.setOnClickListener {
                    item.amount++
                    viewModel.upsert(item)
                }
            }

        }

    }
    val differCallback = object : DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<ShoppingItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            ShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem:ShoppingItem = differ.currentList[position]
        holder.bind(curShoppingItem)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}