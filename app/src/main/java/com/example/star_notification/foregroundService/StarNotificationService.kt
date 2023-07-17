package com.example.star_notification.foregroundService

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.star_notification.R
import com.example.star_notification.app.StarNotificationChannel
import com.example.star_notification.ui.NotificationActivity
import java.util.Calendar

class StarNotificationService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> init()
            Actions.STOP.toString() -> stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun init() {
        val notificationIntent = Intent(this, NotificationActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.run {
                putExtra("lastTimeShowed", Calendar.getInstance().timeInMillis)
                this
            }

        val notificationPendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(this, StarNotificationChannel.ID)
            .setSmallIcon(R.mipmap.ic_star)
            .setContentTitle("Recompensa recebida")
            .setContentText("Parabéns! Você recebeu uma estrela")
            .setContentIntent(notificationPendingIntent)
            .build()

        startForeground(1, notification)
    }

    enum class Actions {
        START,
        STOP,
    }
}