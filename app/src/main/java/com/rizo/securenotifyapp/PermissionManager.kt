package com.rizo.securenotifyapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: ComponentActivity) {

    // Se usa la Activity Result API para solicitar el permiso
    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Aquí se puede notificar a la Activity si el permiso fue concedido o no
        if (activity is MainActivity) {
            activity.updatePermissionStatus()
        }
    }

    /**
     * Lógica principal para solicitar el permiso.
     * En Android 13+ (API 33), solicita POST_NOTIFICATIONS.
     * En versiones anteriores, no hace nada ya que el permiso no existe.
     */
    fun checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                // 1. El permiso ya está concedido
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Ya tenemos el permiso
                }
                // 2. El usuario ya lo denegó antes: mostramos el Rationale
                activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    showRationaleDialog()
                }
                // 3. Primera vez o el usuario marcó "No volver a preguntar"
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // En Android 12 o inferior, el permiso no se solicita en tiempo de ejecución.
            // Las notificaciones están habilitadas por defecto o se gestionan desde ajustes.
            showLegacyMessage()
        }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permiso de Notificaciones")
            .setMessage("Esta aplicación necesita permisos para enviarte notificaciones push importantes sobre tu seguridad.")
            .setPositiveButton("Conceder") { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
                // Al cancelar, no hacemos nada más, cumpliendo el requisito.
            }
            .setCancelable(false)
            .show()
    }

    private fun showLegacyMessage() {
        AlertDialog.Builder(activity)
            .setTitle("Información")
            .setMessage("En tu versión de Android (menor a la 13), las notificaciones se gestionan directamente desde los ajustes del sistema.")
            .setPositiveButton("Entendido", null)
            .show()
    }

    /**
     * Verifica si las notificaciones están permitidas.
     * Funciona para todas las versiones de Android.
     */
    fun isPermissionGranted(): Boolean {
        return NotificationManagerCompat.from(activity).areNotificationsEnabled()
    }
}