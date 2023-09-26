package com.example.mad_practical8_21012021060


import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.WindowCompat
import com.example.mad_practical8_21012021060.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window,false)
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cardView2.visibility=View.GONE
        binding.createAlarmbtn.setOnClickListener {
            showTimeDialog()
        }
        binding.cancelAlarmbtn.setOnClickListener {
            setAlarm(-1,"Stop")
            binding.cardView2.visibility=View.GONE
        }
    }

    fun showTimeDialog(){
        val calendar:Calendar= Calendar.getInstance()
        val hour:Int=calendar.get(Calendar.HOUR_OF_DAY)
        val minutes:Int=calendar.get(Calendar.MINUTE)
        val picker:TimePickerDialog(this,{tp,sHour,sMinute->sendDialogDataToActivity(sHour,sMinute)},hour,minutes,false)
        picker.show()
    }

    private fun sendDialogDataToActivity(hour:Int,minute:Int){

    }




    fun setAlarm(milliTime:Long, action:String)
    {
        val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 2345, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == AlarmBroadcastReceiver.ALARM_START)
        {
            manager.setExact(AlarmManager.RTC_WAKEUP, milliTime, pendingIntent)
        }
    }
}
