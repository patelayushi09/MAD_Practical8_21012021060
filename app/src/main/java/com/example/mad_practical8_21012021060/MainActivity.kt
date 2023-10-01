package com.example.mad_practical8_21012021060

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.mad_practical8_21012021060.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
       
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                updateClock()
                delay(1000) // Delay for 1 second (1000 milliseconds)
            }
        }
        binding.createAlarmbtn.setOnClickListener {

            showTimerDialog()
           
        }

        binding.cancelAlarmbtn.setOnClickListener {

            binding.cardView2.visibility = View.GONE
            
           
            val alarmCalender = Calendar.getInstance()
            setAlarm(alarmCalender.timeInMillis, "STOP")
        }
    }

    private fun updateClock()
    {
        val currentTime = System.currentTimeMillis()
        binding.tv4.text = SimpleDateFormat("hh:mm:ss a MMM, dd yyyy", Locale.US).format(currentTime)
    }
    private fun showTimerDialog()
    {
        val cal: Calendar = Calendar.getInstance()
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cal.get(Calendar.MINUTE)

       

        val picker = TimePickerDialog(
            this,
            {tp, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute)},
            hour,
            minutes,
            false
        )
        picker.show()
    }

    private fun sendDialogDataToActivity(hour: Int, minute: Int)
    {
        val alarmCalender = Calendar.getInstance()
        val year: Int = alarmCalender.get(Calendar.YEAR)
        val month: Int = alarmCalender.get(Calendar.MONTH)
        val day: Int = alarmCalender.get(Calendar.DATE)
        alarmCalender.set(year, month, day, hour, minute, 0)
       
        binding.tv6.text = SimpleDateFormat("hh:mm:ss a", Locale.US).format(alarmCalender.time)
        binding.cardView2.visibility = View.VISIBLE
        setAlarm(alarmCalender.timeInMillis, "START")
    }

    private fun setAlarm(milliTime:Long, action:String)
    {
        val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 2345, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == AlarmBroadcastReceiver.ALARM_START)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliTime, pendingIntent)
            Toast.makeText(this, "Start Alarm", Toast.LENGTH_LONG).show()
        }
        else if(action == AlarmBroadcastReceiver.ALARM_STOP)
        {
            alarmManager.cancel(pendingIntent)
     
            val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
            intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
            sendBroadcast(intent)
            Toast.makeText(this, "Stop Alarm", Toast.LENGTH_LONG).show()

            val stopServiceIntent = Intent(this, AlarmService::class.java)
            stopService(stopServiceIntent)
        }
    }
}

