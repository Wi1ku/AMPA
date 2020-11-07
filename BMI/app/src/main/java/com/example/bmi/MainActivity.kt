package com.example.bmi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bmi.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isImperial = false
    val sharPrefKey = "history"
    val bmiIsZero = "0.00"
    val imperial = "Imperial"
    val metric = "Metric"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("massET", binding.massET.text.toString())
        outState.putString("heightET", binding.heightET.text.toString())
        outState.putString("massTV", binding.massTV.text.toString())
        outState.putString("heightTV", binding.heightTV.text.toString())
        outState.putString("bmiTV", binding.bmiTV.text.toString())
        outState.putBoolean("IsImperial", isImperial)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.apply {
            massTV.text = savedInstanceState.getString("massTV")
            heightTV.text = savedInstanceState.getString("heightTV")
            if(savedInstanceState.getBoolean("IsImperial")){
                isImperial = true
            }
            massET.setText(savedInstanceState.getString("massET"), TextView.BufferType.EDITABLE)
            heightET.setText(savedInstanceState.getString("heightET"), TextView.BufferType.EDITABLE)
            val bmi = savedInstanceState.getString("bmiTV")
            if(bmi != null){
                setBmi(bmi.toDouble(), binding.root)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.units_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.selectMetric -> {
                setUnits(metric)
                isImperial = false
                true
            }
            R.id.selectImperial -> {
                setUnits(imperial)
                isImperial = true
                true
            }
            R.id.selectHistory ->{
                val LAUNCH_SECOND_ACTIVITY = 1
                val i = BMIHistory.newIntent(this)
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showBmiDetails(view: View){
        val LAUNCH_SECOND_ACTIVITY = 1
        val bmi = binding.bmiTV.text.toString()
        if (bmi != bmiIsZero){
            val i = BmiDetails.newIntent(this, bmi)
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY)
        }
    }


    fun count(view: View) {
            binding.apply {
                val bmiCounter = BMICounter()
                if (heightET.text.isBlank()) {
                    heightET.error = getString(R.string.height_is_empty)
                    val toast = Toast.makeText(applicationContext, R.string.height_is_empty, Toast.LENGTH_SHORT)
                    toast.show()
                }
                else if (massET.text.isBlank()) {
                    massET.error = getString(R.string.mass_is_empty)
                    val toast = Toast.makeText(applicationContext, R.string.mass_is_empty, Toast.LENGTH_SHORT)
                    toast.show()
                }
                else{
                    val mass = massET.text.toString().toDouble()
                    val height = heightET.text.toString().toDouble()
                    if (bmiCounter.checkForCorrectValues(mass, height, isImperial)){
                        val bmi = bmiCounter.countBmi(mass, height, isImperial)
                        setBmi(bmi, view)
                        saveBmiResult(bmi, mass, height)
                    }
                    else{
                        val toast = Toast.makeText(applicationContext, R.string.invalid_input_data, Toast.LENGTH_SHORT)
                        toast.show()
                        }
                    }
                }
            }

    private fun setBmi(bmi: Double, view: View){
        binding.apply {
            bmiTV.text = bmi.toString()
            colourBmi(bmi, bmiTV)
            }
        }
    private fun setUnits(name: String){
        when(name){
            imperial -> {
                binding.apply {
                    massTV.text = getString(R.string.mass_lb)
                    heightTV.text = getString(R.string.height_in)
                }
            }
            metric -> {
                binding.apply {
                    massTV.text = getString(R.string.mass_kg)
                    heightTV.text = getString(R.string.height_cm)

                }
            }
        }
    }

    private fun colourBmi(bmi: Double, textView: TextView){
        when {
            bmi < 16 -> textView.setTextColor(getColor(R.color.colorVeryServerlyUnderweight))
            (bmi >= 16) and (bmi < 17) -> textView.setTextColor(getColor(R.color.colorServerlyUnderweight))
            (bmi >= 17) and (bmi < 18.5) -> textView.setTextColor(getColor(R.color.colorUnderweight))
            (bmi >= 18.5) and (bmi < 26) -> textView.setTextColor(getColor(R.color.colorNormal))
            (bmi >= 25) and (bmi < 30) -> textView.setTextColor(getColor(R.color.colorOverweight))
            (bmi >= 30) and (bmi < 35) -> textView.setTextColor(getColor(R.color.colorModeratlyObese))
            (bmi >= 35) and (bmi < 40) -> textView.setTextColor(getColor(R.color.colorServerlyObese))
            bmi >= 40 -> textView.setTextColor(getColor(R.color.colorVeryServerlyObese))
        }
    }



    private fun saveBmiResult(bmi: Double, mass: Double, height: Double){
        val record = BmiRecord(bmi, mass, height, isImperial, Calendar.getInstance().timeInMillis)
        var history = getHistory()
        if(history == null){
            history = mutableListOf<BmiRecord>()
        }
        else{
            history = history.toMutableList()
        }
        if(history.size > 9){
            history.removeFirst()
        }
        history.add(record)
        saveHistory(history)
    }

    fun getHistory(): List<BmiRecord>? {
        val sharedPref = getSharedPreferences(sharPrefKey, Context.MODE_PRIVATE)
        val historyString = sharedPref.getString(getString(R.string.History), "")!!
        return Gson().fromJson<List<BmiRecord>>(historyString)
    }

    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)

    fun saveHistory(list: List<BmiRecord>){
        val sharedPref = this.getSharedPreferences(sharPrefKey, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(list)
        with (sharedPref.edit()) {
            putString(getString(R.string.History), json)
            apply()
        }
    }

}