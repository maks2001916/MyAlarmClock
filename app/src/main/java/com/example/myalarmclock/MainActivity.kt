package com.example.myalarmclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var calendar: Calendar? = null
    private var materialTimePicker: MaterialTimePicker? = null

    private lateinit var toolbarTB: Toolbar
    private lateinit var outputTimeTV: TextView
    private lateinit var setAlarmBTN: Button

    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        toolbarTB = findViewById(R.id.toolbarMain)
        outputTimeTV = findViewById(R.id.outputTimeTV)
        setAlarmBTN = findViewById(R.id.setAlarmBTN)

        setActionBar(toolbarTB)

        setAlarmBTN.setOnClickListener {
            materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Выберите время")
                .build()
            materialTimePicker!!.addOnPositiveButtonClickListener {
                calendar = Calendar.getInstance()
                calendar?.set(Calendar.SECOND, 0)
                calendar?.set(Calendar.MILLISECOND, 0)
                calendar?.set(Calendar.MINUTE, materialTimePicker!!.minute)
                calendar?.set(Calendar.HOUR, materialTimePicker!!.hour)

                var alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.setExact (
                    RTC_WAKEUP,
                    calendar?.timeInMillis!!,
                    getAlarmPandingIntent()
                )

                Toast.makeText(
                    this,
                    "${getString(R.string.alarm_clock_set_to)} ${dateFormat.format(calendar!!.time)}",
                    Toast.LENGTH_SHORT)
            }
            materialTimePicker!!.show(supportFragmentManager, "tag_picker")
        }

    }

    private fun getAlarmPandingIntent(): PendingIntent {
        var intent = Intent(this, AlarmReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {R.id.exit -> finish() }
        return super.onOptionsItemSelected(item)
    }

}