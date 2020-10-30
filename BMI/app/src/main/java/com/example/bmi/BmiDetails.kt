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
                    bmi < 16 -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorVeryServerlyUnderweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Very Serverly Underweight")
                    }
                    (bmi >= 16) and (bmi < 17) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorServerlyUnderweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Serverly Underweight")
                    }
                    (bmi >= 17) and (bmi < 18.5) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorUnderweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Underweight")
                    }
                    (bmi >= 18.5) and (bmi < 26) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorNormal))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Normal")
                    }
                    (bmi >= 25) and (bmi < 30) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorOverweight))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Overweight")
                    }
                    (bmi >= 30) and (bmi < 35) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorModeratlyObese))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Moderately obese")
                    }
                    (bmi >= 35) and (bmi < 40) -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorServerlyObese))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Serverly obese")
                    }
                    bmi >= 40 -> {
                        resultInfoTV.setTextColor(getColor(R.color.colorVeryServerlyObese))
                        descriptionTV.text = getString(R.string.bmi_desc).format(bmi, "Very serverly obese")
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