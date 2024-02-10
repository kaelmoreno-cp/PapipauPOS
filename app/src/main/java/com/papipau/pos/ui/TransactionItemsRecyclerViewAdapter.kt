package com.papipau.pos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.papipau.pos.databinding.LayoutItemHeaderBinding
import com.papipau.pos.databinding.LayoutProductItemBinding
import com.papipau.pos.models.AddedProduct
import java.math.BigDecimal
import java.text.DecimalFormat

class TransactionItemsRecyclerViewAdapter(
    private val list: ArrayList<AddedProduct>
) : RecyclerView.Adapter<ViewHolder>() {

    class HeaderViewHolder(binding: LayoutItemHeaderBinding) : ViewHolder(binding.root)

    class ItemViewHolder(val binding: LayoutProductItemBinding) : ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0)
            return HeaderViewHolder(
                LayoutItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            return ItemViewHolder(
                LayoutProductItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = list[position]
            val df = DecimalFormat("#,###,###.00")
            holder.binding.apply {
                upcTextView.text = item.product.upc
                productNameTextView.text = item.product.name
                priceTextView.text = "P ${df.format(item.product.price)}"
                totalTextView.text = "P ${df.format(item.product.price.multiply(BigDecimal(1)))}"
            }
        }
    }
}