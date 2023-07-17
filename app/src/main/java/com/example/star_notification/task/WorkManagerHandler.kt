package com.example.star_notification.task

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class WorkManagerHandler(context: Context) {
    private var workManager: WorkManager

    init {
        workManager = WorkManager.getInstance(context)
    }

    fun enqueueOneTimeWorkRequest(workRequest: OneTimeWorkRequest) {
        workManager.enqueueUniqueWork(
            "star_notification_worker",
            ExistingWorkPolicy.KEEP,
            workRequest,
        )
    }
}