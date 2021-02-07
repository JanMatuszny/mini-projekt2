package com.jmatuszny.projekt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jmatuszny.projekt1.databinding.ActivityEditProductBinding
import kotlinx.android.synthetic.main.activity_edit_product.*
import java.util.*
import kotlin.collections.HashMap

class EditProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productName = intent.getStringExtra("NameOfProduct")
        val refPath = intent.getStringExtra("RefPath")
        val ref = FirebaseDatabase.getInstance().getReference(refPath!!)

        var productUID = ""

        ref.orderByChild("name").equalTo(productName).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val response = snapshot.value as HashMap<String, HashMap<String, Objects>>
                productUID = response.keys.first()

                val product = response[productUID]

                binding.editTextName.setText(product!!["name"].toString())
                binding.editTextPrice.setText(product!!["price"].toString())
                binding.editTextAmount.setText(product!!["amount"].toString())
                binding.checkBoxIsBought.isChecked = product!!["bought"] as Boolean
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProductActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })

        buttonSave.setOnClickListener {
            val product = Product(
                name = binding.editTextName.text.toString(),
                price = binding.editTextPrice.text.toString().toLong(),
                amount = binding.editTextAmount.text.toString().toLong(),
                isBought = binding.checkBoxIsBought.isChecked)

            ref.child(productUID).setValue(product)

            Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show()
        }
    }
}