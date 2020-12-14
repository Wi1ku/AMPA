package com.example.bmi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.Room.AppDatabase
import com.example.bmi.Room.BmiRecord
import com.example.bmi.Room.RecordDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking

class BMIHistory : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var databaseDao: RecordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_history)
        databaseDao = AppDatabase.getDatabase(this).RecordDao()
        val history = getHistory()


        viewManager = LinearLayoutManager(this)
        viewAdapter = HistoryAdapter(history)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }

    private fun getHistory(): List<BmiRecord>{
        return runBlocking {
            databaseDao.getAll()
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, BMIHistory::class.java)
            return intent
        }
    }

}