package com.example.mad_practical_8_21012011093

import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.sql.Time

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val card= findViewById<MaterialCardView>(R.id.card2)
        val alarmbutton=findViewById<Button>(R.id.CreateBtn)
        card.visibility=View.GONE
        alarmbutton.setOnClickListener {
            card.visibility=View.VISIBLE
            TimePickerDialog(this,{tp,hour,minute->setalarmtime(hour, minute)},11,0,false).show()

        }


    }
    fun setalarmtime(hour:Int,minute:Int){

    }
    fun setalarm(militime:Long,acton:String){
        val intentalarm=Intent(applicationContext,AlarmBroadcastReceiver::class.java)
        val pendingIntent= PendingIntent.getBroadcast(applicationContext,4356,intentalarm,PendingIntent.FLAG_UPDATE_CURRENT)
    }
}