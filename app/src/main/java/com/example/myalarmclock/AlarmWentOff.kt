package com.example.myalarmclock

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class AlarmWentOff : AppCompatActivity() {

    private lateinit var toolbarTB: Toolbar
    private lateinit var imageGifIV: ImageView
    private lateinit var turnOffBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarm_went_off)

        toolbarTB = findViewById(R.id.toolbarWentOff)
        imageGifIV = findViewById(R.id.turnOffGIV)
        turnOffBTN = findViewById(R.id.turnOffBTN)

        turnOffBTN.setOnClickListener {
            finish()
            exitProcess(0)
        }

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