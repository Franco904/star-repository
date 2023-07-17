package com.example.star_notification.task

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.star_notification.R
import com.example.star_notification.application.notificationChannels.StarNotificationChannel
import com.example.star_notification.ui.NotificationActivity
import java.util.*

class StarNotificationWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val notification = NotificationCompat.Builder(applicationContext, StarNotificationChannel.ID)
            .setSmallIcon(R.mipmap.ic_star)
            .setContentTitle("Recompensa disponível")
            .setContentText("Aproveite a oportunidade para ganhar uma nova estrela!")
            .setContentIntent(getNotificationPendingIntent())
            .build()

        with(NotificationManagerCompat.from(applicationContext)) {
            try {
                notify(1, notification)
            } catch (e: SecurityException) {
                Log.e("StarNotificationWorker", "Permissão não concedida")
                e.printStackTrace()

                return Result.failure()
            }
        }

        return Result.success()
    }

    private fun getNotificationPendingIntent(): PendingIntent {
        val notificationIntent = Intent(applicationContext, NotificationActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.run {
                putExtra("lastTimeShowed", Calendar.getInstance().timeInMillis)
                this
            }

        return PendingIntent.getActivity(
            applicationContext, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }
}