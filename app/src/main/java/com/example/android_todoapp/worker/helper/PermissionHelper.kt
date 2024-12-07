package com.example.android_todoapp.worker.helper

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PermissionHelper(
    private val activity: FragmentActivity,
) {

    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
    }

    fun checkAndRequestPermissions(onResult: (Boolean) -> Unit = {}) {

        when {
            hasAllPermissions() -> onResult(true)
            shouldShowPermissionRationale() -> showPermissionRationale { granted ->
                if (granted) requestPermissions() else onResult(false)
            }

            else -> requestPermissions()
        }
    }

    private fun hasAllPermissions(): Boolean {
        return hasNotificationPermission() && hasAlarmPermission()
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    private fun hasAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            activity.getSystemService(AlarmManager::class.java)?.canScheduleExactAlarms() ?: false
        } else true
    }

    private fun shouldShowPermissionRationale(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
        } else false
    }

    private fun showPermissionRationale(onResult: (Boolean) -> Unit) {
        MaterialAlertDialogBuilder(activity)
            .setTitle("Permission Required")
            .setMessage("This app needs notification permission to remind you about your tasks.")
            .setPositiveButton("Grant") { _, _ -> onResult(true) }
            .setNegativeButton("Deny") { _, _ -> onResult(false) }
            .show()
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission()) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasAlarmPermission()) {
            requestAlarmPermission()
        }
    }

    private fun requestAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            }
            activity.startActivity(intent)
        }
    }
}