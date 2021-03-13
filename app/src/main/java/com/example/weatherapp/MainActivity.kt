package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val button: Button = findViewById(R.id.btn)
        button.setOnClickListener {
            val editText : EditText = findViewById(R.id.editTextNumber)
            //Already restricted to only numbers in XML
            if(editText.text.isEmpty() ){
                Toast.makeText(applicationContext, "Please Input A Zip Code", Toast.LENGTH_SHORT).show()
            }else {
                val message = editText.text.toString()
                val intent = Intent(this, WeatherResults::class.java)
                intent.putExtra("zip", message)

                startActivity(intent)
            }
        }
        }
    }
