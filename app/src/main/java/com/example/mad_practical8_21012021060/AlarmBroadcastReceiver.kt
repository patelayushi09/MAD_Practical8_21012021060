package com.example.mad_practical8_21012021060

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver : BroadcastReceiver() {
companion object{
    val ALARM_KEY="key"
    val ALARM_STOP="stop"
    val ALARM_START="start"
}
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val data=intent.getStringExtra(ALARM_KEY)
        val intentService=Intent(context,AlarmService::class.java)
        if(data == ALARM_START){
            context.startService(intentService)
        }
        else if(data == ALARM_STOP){
            context.stopService(intentService)
        }
    }
}