package com.example.weather.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.weather.BR
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        binding.viewModel?.let {
            it.getSnackbarMsg().observe(this){ resMsg ->
                Snackbar.make(binding.root, resMsg, Snackbar.LENGTH_LONG).show()
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
            binding.viewModel.getWeatherAddForecast(19.1880, -96.136,
            "884394befea40cd1385cf8109b488880", "metric", "en")
        }
    }
}