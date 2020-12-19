package com.jmatuszny.projekt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jmatuszny.projekt1.databinding.ActivityEditProductBinding
import kotlinx.android.synthetic.main.activity_edit_product.*

class EditProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getIntExtra("id", -1)
        val viewModel = ProductViewModel(application)
        val product = viewModel.getProduct(productId)

        binding.Id.text = "Id: " + product.id.toString()
        binding.editTextName.setText(product.name)
        binding.editTextPrice.setText(product.price.toString())
        binding.editTextAmount.setText(product.amount.toString())
        binding.checkBoxIsBought.isChecked = product.isBought

        buttonSave.setOnClickListener {
            viewModel.update(
                Product(
                    id = product.id,
                    name = binding.editTextName.text.toString(),
                    price = Integer.parseInt(binding.editTextPrice.text.toString()),
                    amount = Integer.parseInt(binding.editTextAmount.text.toString()),
                    isBought = binding.checkBoxIsBought.isChecked))

            Toast.makeText(this, "Update successful!", Toast.LENGTH_LONG).show()
        }
    }
}