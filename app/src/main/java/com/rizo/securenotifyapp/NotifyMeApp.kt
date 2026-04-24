package com.rizo.securenotifyapp

import android.app.Application

class NotifyMeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannelManager(this).createNotificationChannels()
    }
}