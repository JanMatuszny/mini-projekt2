package com.jmatuszny.projekt1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmatuszny.projekt1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.OptionsButton.setOnClickListener {
            val optionsIntent = Intent(this, OptionsActivity::class.java)
            startActivity(optionsIntent)
        }
        binding.ProductListButton.setOnClickListener {
            val productListIntent = Intent(this, ProductListActivity::class.java)
            startActivity(productListIntent)
        }
    }

    override fun onResume() {
        super.onResume()

        preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        binding.OptionsButton.layoutParams.width =
            preferences.getString("ButtonsWidth", "300")?.toInt() ?: 300
        binding.OptionsButton.layoutParams.height =
            preferences.getString("ButtonsHeight", "300")?.toInt() ?: 300

        binding.ProductListButton.layoutParams.width =
            preferences.getString("ButtonsWidth", "300")?.toInt() ?: 300
        binding.ProductListButton.layoutParams.height =
            preferences.getString("ButtonsHeight", "300")?.toInt() ?: 300

        binding.OptionsButton.setTextColor(preferences.getInt("ButtonsColor", Color.WHITE))
        binding.ProductListButton.setTextColor(preferences.getInt("ButtonsColor", Color.WHITE))
    }
}