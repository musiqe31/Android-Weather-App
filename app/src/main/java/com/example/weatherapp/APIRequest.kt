package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIRequest {

    @GET
    fun getOpenWeather(@Url url: String): Call<com.example.weatherapp.api.OpenWeather>

}