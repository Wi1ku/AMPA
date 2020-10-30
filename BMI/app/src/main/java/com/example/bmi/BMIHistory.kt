package com.example.bmi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BMIHistory : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_history)
        val history = intent.getStringArrayExtra("history")

        viewManager = LinearLayoutManager(this)
       // viewAdapter = history?.let { History_adapter(it) }!!

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    companion object {

        fun newIntent(context: Context, history: DoubleArray): Intent {
            val intent = Intent(context, BmiDetails::class.java)
            intent.putExtra("history", history)
            return intent
        }
    }

}