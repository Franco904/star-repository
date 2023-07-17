package com.example.star_notification.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.star_notification.databinding.ActivityMainBinding
import com.example.star_notification.foregroundService.StarNotificationService

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0,
            )
        }

        setUpShowStarNotification()
    }

    private fun setUpShowStarNotification() {
        binding.showNotificationButton.setOnClickListener {
            Intent(applicationContext, StarNotificationService::class.java).also {
                it.action = StarNotificationService.Actions.START.toString()
                startService(it)
            }
        }
    }
}