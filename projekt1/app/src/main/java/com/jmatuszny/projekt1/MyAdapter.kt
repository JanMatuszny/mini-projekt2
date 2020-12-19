package com.jmatuszny.projekt1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmatuszny.projekt1.databinding.ListElementBinding

class MyAdapter(val productViewModel: ProductViewModel) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var productList = emptyList<Product>()

    class MyViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.IdTextView.text = productList[position].id.toString()
        holder.binding.NameTextView.text = productList[position].name
        holder.binding.PriceTextView.text = productList[position].price.toString()
        holder.binding.AmountTextView.text = productList[position].amount.toString()
        holder.binding.IsBoughtCheckBox.isChecked = productList[position].isBought
        holder.binding.root.setOnClickListener {
            productViewModel.delete(productList[position].id)
            notifyDataSetChanged()
        }
        holder.binding.IsBoughtCheckBox.setOnClickListener {
            productList[position].isBought = holder.binding.IsBoughtCheckBox.isChecked
            productViewModel.update(productList[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = productList.size

    fun setProducts(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }
}