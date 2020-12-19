package com.jmatuszny.projekt1

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmatuszny.projekt1.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val viewModel = ProductViewModel(application)
        val adapter = MyAdapter(viewModel)
        viewModel.products.observe(this, Observer {products ->
            products.let {
                adapter.setProducts(it)
            }
        })

        binding.RecyclerView.adapter = adapter

        binding.AddButton.setOnClickListener {
            viewModel.insert(Product(
                name = binding.NameEditText.text.toString(),
                price = Integer.parseInt(binding.PriceEditText.text.toString()),
                amount = Integer.parseInt(binding.AmountEditText.text.toString()),
                isBought = binding.IsBoughtCheckBox.isChecked))

            val broadcast = Intent(getString(R.string.addProduct))
            broadcast.component = ComponentName(
                    "com.jmatuszny.projekt2",
                    "com.jmatuszny.projekt2.MyReceiver")

            val id = viewModel.getLastProductId()
            broadcast.putExtra("id", id)
            sendBroadcast(broadcast, "com.jmatuszny.projekt1.MY_PERMISSION")
        }

        binding.DeleteAllButton.setOnClickListener {
            viewModel.deleteAll()
            true
        }

        binding.EditButton.setOnClickListener {
            if (!binding.IdEditText.text.isNullOrEmpty()) {
                var editProductIntent = Intent(this, EditProductActivity::class.java)

                editProductIntent.putExtra("id", Integer.parseInt(binding.IdEditText.text.toString()))

                startActivity(editProductIntent)
            }
            else {
                Toast.makeText(this, "Write id!", Toast.LENGTH_LONG).show()
            }
        }

        binding.DeleteButton.setOnClickListener {
            val id = Integer.parseInt(binding.IdEditText.text.toString())
            viewModel.delete(id)
        }
    }
}