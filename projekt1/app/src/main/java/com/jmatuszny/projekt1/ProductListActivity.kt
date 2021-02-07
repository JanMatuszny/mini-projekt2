package com.jmatuszny.projekt1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jmatuszny.projekt1.databinding.ActivityProductListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class ProductListActivity : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val userUID = FirebaseAuth.getInstance().currentUser?.uid
        val mode = intent.getStringExtra("mode")

        if (mode == "private") {
            ref = database.getReference("users/$userUID/products")
        }
        else {
            ref = database.getReference("products")
        }

        val list = arrayListOf<Product>()
        val adapter = MyAdapter(this, list, ref)
//        viewModel.products.observe(this, Observer {products ->
//            products.let {
//                adapter.setProducts(it)
//            }
//        })

        binding.RecyclerView.adapter = adapter

        binding.AddButton.setOnClickListener {
            val product = Product(
            name = binding.NameEditText.text.toString(),
            price = binding.PriceEditText.text.toString().toLong(),
            amount = binding.AmountEditText.text.toString().toLong(),
            isBought = binding.IsBoughtCheckBox.isChecked)

            CoroutineScope(Dispatchers.IO).launch {
                ref.push().setValue(product)
            }

//            val broadcast = Intent(getString(R.string.addProduct))
//            broadcast.component = ComponentName(
//                    "com.jmatuszny.projekt2",
//                    "com.jmatuszny.projekt2.MyReceiver")
//            val id = viewModel.getLastProductId()
//            broadcast.putExtra("id", id)
//            sendBroadcast(broadcast, "com.jmatuszny.projekt1.MY_PERMISSION")
        }

        binding.DeleteAllButton.setOnClickListener {
            ref.removeValue()

            true
        }

        binding.EditButton.setOnClickListener {
            if (!binding.NameOfProductEditText.text.isNullOrEmpty()) {
                var editProductIntent = Intent(this, EditProductActivity::class.java)

                editProductIntent.putExtra("NameOfProduct", binding.NameOfProductEditText.text.toString())
                editProductIntent.putExtra("RefPath", ref.toString().substring(ref.root.toString().length))

                startActivity(editProductIntent)
            }
            else {
                Toast.makeText(this, "Write name!", Toast.LENGTH_LONG).show()
            }
        }

        binding.DeleteButton.setOnClickListener {
            val name = binding.NameOfProductEditText.text.toString()

            ref.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = snapshot.value as HashMap<String, HashMap<String, Objects>>
                    val productUID = response.keys.first()

                    ref.child(productUID).removeValue()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ProductListActivity, error.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}