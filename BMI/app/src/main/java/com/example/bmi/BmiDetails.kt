package com.example.bmi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.bmi.databinding.BmiDetailsBinding

class BmiDetails : Activity() {

    lateinit var binding: BmiDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BmiDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            resultInfoTV.text = intent.getStringExtra("bmi")
        }
    }





    companion object {

        fun newIntent(context: Context, bmi: String): Intent {
            val intent = Intent(context, BmiDetails::class.java)
            intent.putExtra("bmi", bmi)
            return intent
        }
    }
}