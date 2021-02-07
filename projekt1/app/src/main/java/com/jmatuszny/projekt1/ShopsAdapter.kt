package com.jmatuszny.projekt1

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.jmatuszny.projekt1.databinding.ListShopsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopsAdapter(val context: Context, val list: ArrayList<Shop>, val ref: DatabaseReference) :
    RecyclerView.Adapter<ShopsAdapter.MyViewHolder>() {
    init {
        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previous: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    var response = snapshot.value as HashMap<String, Object>
                    list.add(Shop(
                        name = response["name"] as String,
                        description = response["description"] as String,
                        radius = response["radius"] as Long,
                        latitude = response["latitude"] as Double,
                        longitude = response["longitude"] as Double))
                    withContext(Dispatchers.Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previous: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    var response = snapshot.value as HashMap<String, Object>
                    list.remove(Shop(
                        name = response["name"] as String,
                        description = response["description"] as String,
                        radius = response["radius"] as Long,
                        latitude = response["latitude"] as Double,
                        longitude = response["longitude"] as Double))
                    list.add(Shop(
                        name = response["name"] as String,
                        description = response["description"] as String,
                        radius = response["radius"] as Long,
                        latitude = response["latitude"] as Double,
                        longitude = response["longitude"] as Double))
                    withContext(Dispatchers.Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    var response = snapshot.value as HashMap<String, Object>
                    list.remove(Shop(
                        name = response["name"] as String,
                        description = response["description"] as String,
                        radius = response["radius"] as Long,
                        latitude = response["latitude"] as Double,
                        longitude = response["longitude"] as Double))
                    withContext(Dispatchers.Main) {
                        notifyDataSetChanged()
                    }
                }
            }
        })
    }

    class MyViewHolder(val binding: ListShopsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListShopsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.NameTextView.text = list[position].name
        holder.binding.DescriptionTextView.text = list[position].description
        holder.binding.RadiusTextView.text = list[position].radius.toString()

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