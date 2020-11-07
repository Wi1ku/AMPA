package com.example.bmi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BMIHistory : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val sharPrefKey = "history"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_history)
        var history = getHistory()

        viewManager = LinearLayoutManager(this)
        viewAdapter = HistoryAdapter(history)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }

    fun getHistory(): List<BmiRecord> {
        val sharedPref = getSharedPreferences(sharPrefKey, Context.MODE_PRIVATE)
        val historyString = sharedPref.getString(getString(R.string.History), "")!!
        return Gson().fromJson<List<BmiRecord>>(historyString) ?: emptyList()
    }

    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)


    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, BMIHistory::class.java)
            return intent
        }
    }

}