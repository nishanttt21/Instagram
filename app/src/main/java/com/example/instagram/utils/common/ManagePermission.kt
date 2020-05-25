package com.example.instagram.utils.common

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Created by @author Deepak Dawade on 5/8/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class ManagePermission private constructor() {
    companion object {
        private lateinit var permissions: Array<String>
        private lateinit var activity: Activity
        private var permissionRequestCode: Int = 0
        fun requestPermissions(
            activity: Activity,
            permissions: Array<String>,
            requestCode: Int
        ): ManagePermission {
            this.activity = activity
            this.permissions = permissions
            permissionRequestCode = requestCode
            return ManagePermission()
        }
    }

    fun hasPermissions(): Boolean {
        var granted = 0
        val deniedPermission = ArrayList<String>()
        for (permission in permissions) {
            if (isPermissionGranted(permission)) {
                granted += 1
            } else if (isPermissionDenied(permission))
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                    deniedPermission.add(permission)
        }
        if (granted == permissions.size) return true
        else if (deniedPermission.size > 0)
            showSnackBar(
                "Permission required to access this feature",
                deniedPermission.toTypedArray()
            )
        else  Snackbar.make(activity.findViewById(android.R.id.content),"Enable Permission from Settings",Snackbar.LENGTH_INDEFINITE).setAction("Go To Settings"){
            redirectToAppPermissions()
        }.show()
        return granted == permissions.size
    }

    private fun isPermissionGranted(permission: String):
            Boolean = (ActivityCompat.checkSelfPermission(
        activity,
        permission
    ) == PackageManager.PERMISSION_GRANTED)

    private fun isPermissionDenied(permission: String):
            Boolean = (ActivityCompat.checkSelfPermission(
        activity,
        permission
    ) == PackageManager.PERMISSION_DENIED)

    private fun showSnackBar(message: String, deniedPermissions: Array<String>) {
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("ENABLE") {
            ActivityCompat.requestPermissions(activity, deniedPermissions, permissionRequestCode)
        }.show()
    }
    private fun redirectToAppPermissions() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts(
                "package",
                activity.packageName, null
            )
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }


}
