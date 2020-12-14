package com.example.bmi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.bmi.databinding.BmiDetailsBinding

class BmiDetails : Activity() {

    lateinit var binding: BmiDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BmiDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            val bmi_string = intent.getStringExtra("bmi")
            if (bmi_string != null) {
                resultInfoTV.text = bmi_string
                val bmi = bmi_string.toDouble()
                when {
                    bmi < VERYSEVERLYUNDERWEIGHT_END_VAL -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorVeryServerlyUnderweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Very_Serverly_Underweight))
                    }
                    (bmi >= VERYSEVERLYUNDERWEIGHT_END_VAL) and (bmi < SEVERLYUNDERWEIGHT_END_VAL) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorServerlyUnderweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Serverly_Underweight))
                    }
                    (bmi >= SEVERLYUNDERWEIGHT_END_VAL) and (bmi < UNDERWEIGHT_END_VAL) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorUnderweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Underweight))
                    }
                    (bmi >= UNDERWEIGHT_END_VAL) and (bmi < NORMAL_END_VAL) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorNormal))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Normal))
                    }
                    (bmi >= NORMAL_END_VAL) and (bmi < OVERWEIGHT_END_VAL) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorOverweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Overweight))
                    }
                    (bmi >= OVERWEIGHT_END_VAL) and (bmi < MODERATLYOBESE_END_VAL) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorModeratlyObese))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Moderately_obese))
                    }
                    (bmi >= MODERATLYOBESE_END_VAL) and (bmi < SEVERLYOBESE_END_VAL) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorServerlyObese))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Serverly_obese))
                    }
                    bmi >= SEVERLYOBESE_END_VAL -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorVeryServerlyObese))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, getString(R.string.Very_serverly_obese))
                    }
                }
            }
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