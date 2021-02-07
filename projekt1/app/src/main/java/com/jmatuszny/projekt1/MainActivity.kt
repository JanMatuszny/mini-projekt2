package com.jmatuszny.projekt1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.jmatuszny.projekt1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTextView.text = intent.getStringExtra("user")

        binding.OptionsButton.setOnClickListener {
            val optionsIntent = Intent(this, OptionsActivity::class.java)
            startActivity(optionsIntent)
        }
        binding.ProductListButton.setOnClickListener {
            val productListIntent = Intent(this, ProductListActivity::class.java)
            productListIntent.putExtra("mode", "private")

            startActivity(productListIntent)
        }

        binding.PublicProductsButton.setOnClickListener {
            val productListIntent = Intent(this, ProductListActivity::class.java)
            productListIntent.putExtra("mode", "public")

            startActivity(productListIntent)
        }

        binding.ShopListButton.setOnClickListener {
            val shopListIntent = Intent(this, ShopListActivity::class.java)

            startActivity(shopListIntent)
        }

        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(perms, 0)
        }
    }

    override fun onResume() {
        super.onResume()

//        preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
//
//        binding.titleTextView.text = intent.getStringExtra("user")
//
//        binding.OptionsButton.layoutParams.width = 350
////            preferences.getString("ButtonsWidth", "300")?.toInt() ?: 300
//        binding.OptionsButton.layoutParams.height = 150
////            preferences.getString("ButtonsHeight", "300")?.toInt() ?: 300
//
//        binding.ProductListButton.layoutParams.width = 350
////            preferences.getString("ButtonsWidth", "300")?.toInt() ?: 300
//        binding.ProductListButton.layoutParams.height = 150
////            preferences.getString("ButtonsHeight", "300")?.toInt() ?: 300
//
//        binding.PublicProductsButton.layoutParams.width = 350
////            preferences.getString("ButtonsWidth", "300")?.toInt() ?: 300
//        binding.PublicProductsButton.layoutParams.height = 150
////            preferences.getString("ButtonsHeight", "300")?.toInt() ?: 300
//
//        binding.OptionsButton.setTextColor(preferences.getInt("ButtonsColor", Color.WHITE))
//        binding.ProductListButton.setTextColor(preferences.getInt("ButtonsColor", Color.WHITE))
    }
}