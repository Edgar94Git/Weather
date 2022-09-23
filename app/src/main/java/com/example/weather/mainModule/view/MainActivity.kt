package com.example.weather.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.BR
import com.example.weather.R
import com.example.weather.common.entities.Forecast
import com.example.weather.common.utils.CommonUtils
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.mainModule.view.adapters.ForecastAdapter
import com.example.weather.mainModule.view.adapters.OnClickListener
import com.example.weather.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupObservers()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupAdapter() {
        adapter = ForecastAdapter(this)
    }

    private fun setupObservers() {
        binding.viewModel?.let {
            it.getSnackbarMsg().observe(this){ resMsg ->
                Snackbar.make(binding.root, resMsg, Snackbar.LENGTH_LONG).show()
            }
            it.getResult().observe(this){ result ->
                adapter.submitList(result.hourly)
            }
        }
    }

    private fun setupViewModel() {
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            binding.viewModel?.getWeatherAddForecast(19.1738, -96.1342,
            "884394befea40cd1385cf8109b488880", "hourly", "metric", "en")
        }
    }

    override fun onClick(forecast: Forecast) {
        Snackbar.make(binding.root, CommonUtils.getFullDate(forecast.dt), Snackbar.LENGTH_SHORT).show()
    }
}