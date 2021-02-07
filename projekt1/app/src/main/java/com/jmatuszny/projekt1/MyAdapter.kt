package com.jmatuszny.projekt1

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.jmatuszny.projekt1.databinding.ListElementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MyAdapter(val context: Context, val list: ArrayList<Product>, val ref: DatabaseReference) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    init {
        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previous: String?) {
                CoroutineScope(IO).launch {
                    var response = snapshot.value as HashMap<String, Object>
                    list.add(Product(
                            name = response["name"] as String,
                            price = response["price"] as Long,
                            amount = response["amount"] as Long,
                            isBought = response["bought"] as Boolean))
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previous: String?) {
                CoroutineScope(IO).launch {
                    var response = snapshot.value as HashMap<String, Object>
                    list.remove(Product(
                            name = response["name"] as String,
                            price = response["price"] as Long,
                            amount = response["amount"] as Long,
                            isBought = response["bought"] as Boolean))
                    list.add(Product(
                            name = response["name"] as String,
                            price = response["price"] as Long,
                            amount = response["amount"] as Long,
                            isBought = response["bought"] as Boolean))
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(IO).launch {
                    var response = snapshot.value as HashMap<String, Object>
                    list.remove(Product(
                            name = response["name"] as String,
                            price = response["price"] as Long,
                            amount = response["amount"] as Long,
                            isBought = response["bought"] as Boolean))
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }
        })
    }

    class MyViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.NameTextView.text = list[position].name
        holder.binding.PriceTextView.text = list[position].price.toString()
        holder.binding.AmountTextView.text = list[position].amount.toString()
        holder.binding.IsBoughtCheckBox.isChecked = list[position].isBought
//        holder.binding.IdTextView.setOnClickListener {
//            ref.orderByValue().equalTo(list[position].name)
//                .addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    CoroutineScope(IO).launch {
//                        snapshot.children.iterator().next().ref.removeValue()
//                    }
//                }
//                override fun onCancelled(error: DatabaseError) {
//                    Log.e("MyAdapter", "Failed to delete value.",error.toException())
//                }
//            })
//        }
    }
    override fun getItemCount(): Int = list.size
}

//
//
//class MyAdapter(val productViewModel: ProductViewModel) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//
//    private var productList = emptyList<Product>()
//
//    class MyViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ListElementBinding.inflate(inflater)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.binding.IdTextView.text = productList[position].id.toString()
//        holder.binding.NameTextView.text = productList[position].name
//        holder.binding.PriceTextView.text = productList[position].price.toString()
//        holder.binding.AmountTextView.text = productList[position].amount.toString()
//        holder.binding.IsBoughtCheckBox.isChecked = productList[position].isBought
////        holder.binding.root.setOnClickListener {
////            productViewModel.delete(productList[position].id)
////            notifyDataSetChanged()
////        }
////        holder.binding.IsBoughtCheckBox.setOnClickListener {
////            productList[position].isBought = holder.binding.IsBoughtCheckBox.isChecked
////            productViewModel.update(productList[position])
////            notifyDataSetChanged()
////        }
//    }
//
//    override fun getItemCount(): Int = productList.size
//
//    fun setProducts(products: List<Product>) {
//        productList = products
//        notifyDataSetChanged()
//    }
//}