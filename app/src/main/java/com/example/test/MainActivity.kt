package com.example.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var prizeTextView: TextView
    private var pizzaPrice = 0.0
    private lateinit var pizzaCheckBoxes: Array<CheckBox>
    private val toppingPrices = mapOf(
        "avocado" to 1.0,
        "broccoli" to 1.0,
        "onions" to 1.0,
        "zucchini" to 1.0,
        "lobster" to 2.0,
        "oyster" to 2.0,
        "salmon" to 2.0,
        "tuna" to 2.0,
        "bacon" to 3.0,
        "duck" to 3.0,
        "ham" to 3.0,
        "sausage" to 3.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pizzaImage1ImageView = findViewById<ImageView>(R.id.pizzaImage1ImageView)

        // Tambahkan penanganan klik di sini
        pizzaImage1ImageView.setOnClickListener {
            val intent = Intent(this, detailactivity1::class.java)
            startActivity(intent)
        }
        val pizzaImage2ImageView = findViewById<ImageView>(R.id.pizzaImage2ImageView)
        pizzaImage2ImageView.setOnClickListener {
            val intent = Intent(this, detailactivity2::class.java)
            startActivity(intent)
        }
        val pizzaImage3ImageView = findViewById<ImageView>(R.id.pizzaImage3ImageView)
        pizzaImage3ImageView.setOnClickListener {
            val intent = Intent(this, detailactivity3::class.java)
            startActivity(intent)
        }
        prizeTextView = findViewById(R.id.prizeTextView)

        pizzaCheckBoxes = Array(12) { index ->
            findViewById(resources.getIdentifier("checkBox${index + 1}", "id", packageName))
        }

        val pizzaCardViews = arrayOf<CardView>(
            findViewById(R.id.cardViewPizza1),
            findViewById(R.id.cardViewPizza2),
            findViewById(R.id.cardViewPizza3)
        )

        for ((index, pizzaCardView) in pizzaCardViews.withIndex()) {
            pizzaCardView.setOnClickListener {
                resetToppings()
                pizzaPrice = when (index) {
                    0 -> 8.0
                    1 -> 10.0
                    else -> 12.0
                }
                updatePrize()
                updateToppingsAvailability(index)
            }
        }

        val radioButtons = arrayOf(
            findViewById<RadioButton>(R.id.radioSmall),
            findViewById<RadioButton>(R.id.radioMedium),
            findViewById<RadioButton>(R.id.radioLarge)
        )

        for ((index, radioSmall) in radioButtons.withIndex()) {
            radioSmall.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    resetToppings()
                    pizzaPrice = when (index) {
                        0 -> 8.0
                        1 -> 9.0
                        else -> 10.0
                    }
                    updatePrize()
                    updateToppingsAvailability(index)
                }
            }
        }
        for ((index, radioLarge) in radioButtons.withIndex()) {
            radioLarge.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    resetToppings()
                    pizzaPrice = when (index) {
                        0 -> 12.0
                        1 -> 13.0
                        else -> 14.0
                    }
                    updatePrize()
                    updateToppingsAvailability(index)
                }
            }
        }
        for ((index, radioMedium) in radioButtons.withIndex()) {
            radioMedium.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    resetToppings()
                    pizzaPrice = when (index) {
                        0 -> 10.0
                        1 -> 11.0
                        else -> 13.0
                    }
                    updatePrize()
                    updateToppingsAvailability(index)
                }
            }
        }



        for ((index, pizzaCheckBox) in pizzaCheckBoxes.withIndex()) {
            pizzaCheckBox.setOnCheckedChangeListener { _, _ ->
                updatePrize()
            }
        }
    }

    private fun updatePrize() {
        var totalPrice = pizzaPrice

        for (pizzaCheckBox in pizzaCheckBoxes) {
            if (pizzaCheckBox.isChecked) {
                val topping = pizzaCheckBox.text.toString().toLowerCase()
                totalPrice += toppingPrices[topping] ?: 0.0
            }
        }

        prizeTextView.text = "$ " + totalPrice.toString()

        Log.d("PriceDebug", "Total Price: $totalPrice")
    }

    private fun updateToppingsAvailability(pizzaIndex: Int = -1) {
        val selectedPizzaToppings = when (pizzaIndex) {
            0 -> listOf("avocado","brocoli","onions","zuchinni","tuna","ham")
            1 -> listOf("lobster","brocoli","onions","zuchinni","lobster","oyster","salmon","bacon","ham")
            2 -> listOf("harm","brocoli","onions","zuchinni","tuna","ham","bacon","duck","sausage")
            else -> toppingPrices.keys.toList()
        }

        for ((index, pizzaCheckBox) in pizzaCheckBoxes.withIndex()) {
            val topping = pizzaCheckBox.text.toString().toLowerCase()
            pizzaCheckBox.isEnabled = selectedPizzaToppings.contains(topping)
            if (!selectedPizzaToppings.contains(topping)) {
                pizzaCheckBox.isChecked = false
            }
        }
    }

    private fun resetToppings() {
        for (pizzaCheckBox in pizzaCheckBoxes) {
            pizzaCheckBox.isChecked = false
        }
    }

}


