package com.example.weather.mainModule.view.adapters

import com.example.weather.common.entities.Forecast

interface OnClickListener {
    fun onClick(forecast: Forecast)
}