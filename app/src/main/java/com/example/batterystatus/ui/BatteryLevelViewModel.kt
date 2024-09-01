package com.example.batterystatus.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.ViewModel
import com.example.batterystatus.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BatteryLevelViewModel : ViewModel() {

    private val _batteryImageResource = MutableStateFlow(R.drawable.battery_full)
    val batteryImageResource = _batteryImageResource.asStateFlow()

    fun updateBatteryImage(batteryLevel: Int) {
        _batteryImageResource.update {
            if (batteryLevel >= 10) R.drawable.battery_full
            else R.drawable.battery_low
        }
    }

    fun startBatteryLevelDetection(context: Context) {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val batteryPct = calculateBatteryPercentage(intent)
                updateBatteryImage(batteryPct)
            }
        }
        context.registerReceiver(batteryReceiver, intentFilter)
    }

    private fun calculateBatteryPercentage(intent: Intent): Int {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        return (level * 100) / scale
    }
}
