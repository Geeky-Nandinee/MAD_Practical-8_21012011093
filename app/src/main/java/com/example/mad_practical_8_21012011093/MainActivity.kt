package com.example.mad_practical_8_21012011093

import android.app.AlarmManager
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
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val card= findViewById<MaterialCardView>(R.id.card2)
        val alarmbutton=findViewById<Button>(R.id.CreateBtn)
        card.visibility=View.GONE
        alarmbutton.setOnClickListener {
            card.visibility=View.VISIBLE
            TimePickerDialog(this,{tp,hour,minute->setalarmtime(hour, minute)},Calendar.HOUR,Calendar.MINUTE,false).show()

        }
        //pass current time hour and minute
        //visible 2nd card view
        //simpledatetimeformate
        //cancel button alarm shoud stop and card2 should hide

    }

    fun setalarmtime(hour:Int,minute:Int){
    val alarmtime = Calendar.getInstance()
        val year= alarmtime.get(Calendar.YEAR)
        val month = alarmtime.get(Calendar.MONTH)
        val date = alarmtime.get(Calendar.DATE)
        alarmtime.set(year, month, date,hour,minute)
        setalarm(alarmtime.timeInMillis,AlarmBroadcastReceiver.ALARMSTART)
    }

    fun stop(){
        setalarm(-1,AlarmBroadcastReceiver.ALARMSTOP)
    }
    fun setalarm(militime:Long,action:String) {
        val intentalarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentalarm.putExtra(AlarmBroadcastReceiver.ALARMKEY,action)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            4356,
            intentalarm,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmmanager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (action == AlarmBroadcastReceiver.ALARMSTART) {
            alarmmanager.setExact(AlarmManager.RTC_WAKEUP, militime, pendingIntent)
        }
        else if (action == AlarmBroadcastReceiver.ALARMSTOP) {
            alarmmanager.cancel(pendingIntent)
            sendBroadcast(intentalarm)
        }

    }
}