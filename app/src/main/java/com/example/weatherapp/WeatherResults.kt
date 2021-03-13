package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.api.OpenWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

class WeatherResults : AppCompatActivity() {

    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_results)

        getCurrentData()

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getCurrentData() {
        val zipCode = intent.getStringExtra("zip")

        val api: APIRequest = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO){
            try {
                val response: Response<OpenWeather> =
                    api.getOpenWeather("weather?zip=${zipCode}&appid={INSERT KEY HERE!!}") //NOT IDEAL TO SET HERE!!
                        .awaitResponse()

                if (response.isSuccessful) {
                    val data: OpenWeather = response.body()!!

                    withContext(Dispatchers.Main) {
                        val city: TextView = findViewById(R.id.textView)
                        city.text = "City: " + data.name.toString()
                        val celsius: TextView = findViewById(R.id.textView2)
                        celsius.text = "Current Temp (C): " + String.format(
                            "%.2f",
                            convertCelsius(data.main.temp.toInt())
                        )
                        val fahrenheit: TextView = findViewById(R.id.textView3)
                        fahrenheit.text = "Current Temp (F): " + String.format(
                            "%.2f",
                            convertFahrenheit(data.main.temp.toInt())
                        )
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Oops, An Error", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    fun convertCelsius(x : Int): Double {
        return x - 273.15
    }

    fun convertFahrenheit(x : Int): Double{
        return (x-273.15) * 9/5 + 32
    }
}