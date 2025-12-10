package com.example.lab_week_10

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.databinding.ActivityMainBinding
import com.example.lab_week_10.viewmodels.TotalViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TotalViewModel by viewModels()
    private val dao by lazy { TotalDatabase.getDatabase(this).totalDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeTotal()

        binding.buttonIncrement.setOnClickListener {
            updateTotal()
        }
    }

    private fun observeTotal() {
        lifecycleScope.launch {
            dao.getTotalFlow().collect {
                binding.textTotal.text = "Total: ${it?.total ?: 0}"
                binding.textTimestamp.text = "Last Updated: ${it?.timestamp ?: "-"}"
            }
        }
    }

    private fun updateTotal() {
        lifecycleScope.launch {
            val saved = dao.getTotal()
            val newTotal = (saved?.total ?: 0) + 1

            val time = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            val data = Total(1, newTotal, time)

            dao.insert(data)
        }
    }
}