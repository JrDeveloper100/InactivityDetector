package com.example.inactivitydetector

import android.os.Handler
import android.os.Looper

class UserInactivityDetector (private val inactivityTimeoutMillis: Long, private val listener: Listener) {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private var isMonitoring = false

    interface Listener {
        fun onUserInactive()
    }

    private val runnable = Runnable {
        listener.onUserInactive()
    }

    fun startMonitoring() {
        if (!isMonitoring) {
            handler.postDelayed(runnable, inactivityTimeoutMillis)
            isMonitoring = true
        }
    }

    fun stopMonitoring() {
        handler.removeCallbacks(runnable)
        isMonitoring = false
    }

    fun onUserInteraction() {
        if (isMonitoring) {
            stopMonitoring()
            startMonitoring()
        }
    }
}