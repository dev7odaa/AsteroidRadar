package com.udacity.asteroidradar.app

import android.app.Application
import android.os.Build
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Constraints
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private val debug = false

    override fun onCreate() {
        super.onCreate()

        delayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {

        lateinit var constraints: androidx.work.Constraints

        if (debug) {
            constraints = androidx.work.Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

//            constraints = Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .build()
        } else {
            constraints = androidx.work.Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()
        }

        val timeUnit = if(debug) TimeUnit.SECONDS else TimeUnit.DAYS
        val interval:Long = if(debug) 10 else 1

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshAsteroidsWorker>(interval, timeUnit)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteroidsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatingRequest)
    }
}