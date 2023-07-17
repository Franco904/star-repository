package com.example.star_notification.task

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class WorkManagerHandler(private val workManager: WorkManager) {
    fun enqueueOneTimeWorkRequest(workRequest: OneTimeWorkRequest) {
        workManager.enqueueUniqueWork(
            "star_notification_worker",
            ExistingWorkPolicy.KEEP,
            workRequest,
        )
    }
}