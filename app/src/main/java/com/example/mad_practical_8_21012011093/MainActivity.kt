package com.example.mad_practical_8_21012011093

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addAlarm : MaterialButton = findViewById(R.id.CreateBtn)

        val card : MaterialCardView = findViewById(R.id.card2)

        card.visibility = View.GONE

        addAlarm.setOnClickListener {
            TimePickerDialog(this, {tp, hour, minute -> setAlarmTime(hour, minute)}, Calendar.HOUR,Calendar.MINUTE,false).show()
            TimePickerDialog(this, { tp, hour, minute -> sendDialogDataToActivity(hour, minute) }, Calendar.HOUR, Calendar.MINUTE, false).show()
            card.visibility = View.VISIBLE
        }

        val cancelAlarm : MaterialButton = findViewById(R.id.CancelBtn)
        cancelAlarm.setOnClickListener {
            stop()
            card.visibility = View.GONE
        }
    }
    fun setAlarmTime(hour : Int, minute : Int){
        val alarmTime = Calendar.getInstance()
        val year = alarmTime.get(Calendar.YEAR)
        val month = alarmTime.get(Calendar.MONTH)
        val date = alarmTime.get(Calendar.DATE)
        alarmTime.set(year, month, date, hour, minute, 0)
        setAlarm(alarmTime.timeInMillis, AlarmBroadcastReceiver.ALARMSTART)
    }
    fun stop(){
        setAlarm(-1,AlarmBroadcastReceiver.ALARMSTOP)
    }
    fun setAlarm(millitime : Long, action : String) {
        val intentalarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentalarm.putExtra(AlarmBroadcastReceiver.ALARMKEY,action)
        val pendingintent = PendingIntent.getBroadcast(applicationContext,4356,intentalarm,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == AlarmBroadcastReceiver.ALARMSTART){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,millitime,pendingintent)
        }
        else if(action == AlarmBroadcastReceiver.ALARMSTOP){
            alarmManager.cancel(pendingintent)
            sendBroadcast(intentalarm)
        }
    }
    private fun sendDialogDataToActivity(hour: Int, minute: Int){
        val textAlarmTime : TextView = findViewById(R.id.textAlarmTime)
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)
        textAlarmTime.text = SimpleDateFormat("hh:mm:ss a").format(alarmCalendar.time)
        setAlarm(alarmCalendar.timeInMillis,"Start")
        Toast.makeText(
            this,
            "Time: hours:${hour}, minutes:${minute}," +
                    "millis:${alarmCalendar.timeInMillis}",
            Toast.LENGTH_LONG
        ).show()
    }
}