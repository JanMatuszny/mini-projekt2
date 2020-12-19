package com.jmatuszny.projekt1

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jmatuszny.projekt1.databinding.ActivityOptionsBinding
import kotlinx.android.synthetic.main.activity_options.*


class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        binding.ButtonsWidthTextView.text = "Buttons width: " + preferences.getString("ButtonsWidth", "?")
        binding.ButtonsHeightTextView.text = "Buttons height: " + preferences.getString("ButtonsHeight", "?")

        when (preferences.getInt("ButtonsColor", Color.WHITE)) {
            Color.WHITE -> {
                RadioGroup.check(binding.WhiteRadioButton.id)
            }
            Color.GREEN -> {
                RadioGroup.check(binding.GreenRadioButton.id)
            }
            Color.CYAN -> {
                RadioGroup.check(binding.CyanRadioButton.id)
            }
        }

        binding.SaveButton.setOnClickListener {
            val editor = preferences.edit()
            var width = preferences.getString("ButtonsWidth", "?")
            var height = preferences.getString("ButtonsHeight", "?")

            if (!binding.ButtonsWidthEditText.text.isNullOrEmpty())
                width = binding.ButtonsWidthEditText.text.toString()

            if (!binding.ButtonsHeightEditText.text.isNullOrEmpty())
                height = binding.ButtonsHeightEditText.text.toString()

            val buttonsColor = getButtonsColor(binding.RadioGroup)

            editor.putString("ButtonsWidth", width)
            editor.putString("ButtonsHeight", height)
            editor.putInt("ButtonsColor", buttonsColor)
            editor.apply()

            binding.ButtonsWidthTextView.text = "Buttons width: $width"
            binding.ButtonsHeightTextView.text = "Buttons height: $height"

            Toast.makeText(this, "Buttons width: $width\nButtons height: $height", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getButtonsColor(group: RadioGroup) : Int {
         when (group.checkedRadioButtonId) {
            binding.WhiteRadioButton.id -> return Color.WHITE
            binding.GreenRadioButton.id -> return Color.GREEN
            binding.CyanRadioButton.id -> return Color.CYAN
        }
        return Color.WHITE
    }
}