package com.rizo.securenotifyapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotifyMeMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token actualizado: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val type = remoteMessage.data["type"]
        val title = remoteMessage.notification?.title ?: "Notificación FCM"
        val body = remoteMessage.notification?.body ?: "Has recibido un mensaje"

        val channelId = when (type) {
            "urgent" -> NotificationChannelManager.CHANNEL_URGENT_ID
            "silent" -> NotificationChannelManager.CHANNEL_SILENT_ID
            else -> NotificationChannelManager.CHANNEL_GENERAL_ID
        }

        showNotification(title, body, channelId)
    }

    private fun showNotification(title: String, message: String, channelId: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}