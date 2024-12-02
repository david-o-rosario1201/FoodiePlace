package edu.ucne.proyectofinalaplicada2.presentation.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RequestPermissionsScreen(viewModel: PermissionViewModel = viewModel()) {
    val context = LocalContext.current

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var notificationRequestCompleted by remember { mutableStateOf(false) }
    var cameraPermissionAttempt by remember { mutableStateOf(0) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            notificationRequestCompleted = true
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            viewModel.onPermissionResult(
                permission = android.Manifest.permission.CAMERA,
                isGranted = isGranted
            )
            if (!isGranted) {
                cameraPermissionAttempt++
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !hasNotificationPermission
        ) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            notificationRequestCompleted = true
        }
    }

    LaunchedEffect(notificationRequestCompleted) {
        if (notificationRequestCompleted && !hasCameraPermission && cameraPermissionAttempt < 2) {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    val dialogQueue = viewModel.visiblePermissionDialogQueue
    dialogQueue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when(permission){
                android.Manifest.permission.CAMERA -> {
                    CameraPermissionTextProvider()
                }
                else -> return@forEach },
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                LocalContext.current as Activity, permission
            ),
            onDismiss = viewModel::dismissDialog,
            onOkClick = {
                viewModel.dismissDialog()
                if (permission == android.Manifest.permission.CAMERA) {
                    cameraPermissionLauncher.launch(permission)
                }
            },
            onGoToAppSettingsClick = {
                val activity = context as? Activity
                activity?.openAppSettings()
            }
        )
    }
}

fun Activity.openAppSettings() {
    Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        android.net.Uri.fromParts("package", packageName, null)
    ).also { startActivity(it) }
}