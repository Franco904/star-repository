package com.example.star_notification.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.example.star_notification.databinding.ActivityMainBinding
import com.example.star_notification.databinding.ActivityNotificationBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationActivity: AppCompatActivity() {
    private val binding: ActivityNotificationBinding by lazy {
        ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setLastTimeShowedNotificationText()
    }

    private fun setLastTimeShowedNotificationText() {
        val lastTimeShowedNotificationMillis = intent.getLongExtra("lastTimeShowed", 0L)
        val lastTimeShowedNotificationText = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.US).run {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = lastTimeShowedNotificationMillis
            }
            format(calendar.time)
        }

        binding.lastNotificationShowedTimeTv.text = lastTimeShowedNotificationText
    }
}