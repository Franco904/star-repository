package com.example.star_notification.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.OneTimeWorkRequestBuilder
import com.example.star_notification.task.StarNotificationWorker
import com.example.star_notification.task.WorkManagerHandler
import java.util.*
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    private val workManagerHandler by lazy {
        WorkManagerHandler(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
        initNotificationTask()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val starChannel = NotificationChannel(
                StarNotificationChannel.ID,
                StarNotificationChannel.NAME,
                StarNotificationChannel.IMPORTANCE,
            )

            notificationManager.createNotificationChannels(listOf(
                starChannel,
            ))
        }
    }

    private fun initNotificationTask() {
        val currentTime = Calendar.getInstance()
        val targetTime = Calendar.getInstance()

        with(targetTime) {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 50)
            set(Calendar.SECOND, 0)

            if (before(currentTime)) {
                targetTime.add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val initialDelay = targetTime.timeInMillis - currentTime.timeInMillis
        val workRequest = OneTimeWorkRequestBuilder<StarNotificationWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        workManagerHandler.enqueueOneTimeWorkRequest(workRequest)
    }
}