package com.example.mad_practical8_21012021060

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createButton: Button = findViewById(R.id.createAlarmbtn)
        val cv2: CardView = findViewById(R.id.cardView2)
        val cancelButton: Button = findViewById(R.id.cancelAlarmbtn)
        createButton.setOnClickListener {
            cv2.visibility = View.VISIBLE
        }
        cancelButton.setOnClickListener {
            cv2.visibility = View.GONE
        }

    }

    fun setAlarm(milliTime: Long, action: String) {
        val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            2345,
            intentAlarm,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (action == AlarmBroadcastReceiver.ALARM_START) {
           manager.setExact(AlarmManager.RTC_WAKEUP,milliTime,pendingIntent)
        }
    }
}
