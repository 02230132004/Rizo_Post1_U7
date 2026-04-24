package com.rizo.securenotifyapp

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.rizo.securenotifyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionManager = PermissionManager(this)

        setupListeners()
        updatePermissionStatus()
        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("FCM", "Error al obtener token", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM", "Token actualizado: $token")
        }
    }

    private fun setupListeners() {
        // Solicitud de permiso mediante el PermissionManager
        binding.btnRequestPermission.setOnClickListener {
            permissionManager.checkAndRequestPermission()
        }

        binding.btnShowGeneral.setOnClickListener {
            showTestNotification(
                "Prueba General",
                "Notificación enviada al canal general",
                NotificationChannelManager.CHANNEL_GENERAL_ID
            )
        }

        binding.btnShowUrgent.setOnClickListener {
            showTestNotification(
                "Prueba Urgente",
                "Notificación enviada al canal urgente",
                NotificationChannelManager.CHANNEL_URGENT_ID
            )
        }

        binding.btnCheckStatus.setOnClickListener {
            updatePermissionStatus()
        }
    }

    /**
     * Actualiza el TextView para mostrar el estado actual del permiso.
     * Es público para que PermissionManager pueda llamarlo.
     */
    fun updatePermissionStatus() {
        val isGranted = permissionManager.isPermissionGranted()
        val statusText = if (isGranted) "CONCEDIDO" else "DENEGADO / NO SOLICITADO"
        binding.tvPermissionStatus.text = "Estado del permiso: $statusText"
    }

    private fun showTestNotification(title: String, message: String, channelId: String) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    override fun onResume() {
        super.onResume()
        // Actualizamos el estado cada vez que el usuario vuelve a la app (por si cambió algo en ajustes)
        updatePermissionStatus()
    }
}