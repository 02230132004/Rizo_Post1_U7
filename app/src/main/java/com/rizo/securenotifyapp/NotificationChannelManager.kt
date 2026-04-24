package com.rizo.securenotifyapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationChannelManager(private val context: Context) {

    companion object {
        const val CHANNEL_GENERAL_ID = "channel_general"
        const val CHANNEL_URGENT_ID = "channel_urgent"
        const val CHANNEL_SILENT_ID = "channel_silent"
    }

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // General Channel
            val generalChannel = NotificationChannel(
                CHANNEL_GENERAL_ID,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para notificaciones generales estándar."
            }

            // Urgent Channel
            val urgentChannel = NotificationChannel(
                CHANNEL_URGENT_ID,
                "Urgent Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para alertas críticas y urgentes."
            }

            // Silent Channel
            val silentChannel = NotificationChannel(
                CHANNEL_SILENT_ID,
                "Silent Notifications",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Canal para notificaciones silenciosas de fondo."
            }

            notificationManager.createNotificationChannels(
                listOf(generalChannel, urgentChannel, silentChannel)
            )
        }
    }
}