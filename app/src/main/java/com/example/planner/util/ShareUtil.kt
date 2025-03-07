package com.example.planner.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShareBitmapFromComposable(context: Context, graphicsLayer: GraphicsLayer, writeStorageAccessState: MultiplePermissionsState, actionAfterShare:()->Unit) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    /* val writeStorageAccessState = rememberMultiplePermissionsState(
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             // No permissions are needed on Android 10+ to add files in the shared storage
             emptyList()
         } else {
             listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
         }
     )*/

    if (writeStorageAccessState.allPermissionsGranted) {
        coroutineScope.launch {
            val bitmap = graphicsLayer.toImageBitmap()
            val uri = bitmap.asAndroidBitmap().saveToDisk(context)
            shareBitmap(context, uri)
            actionAfterShare()
        }
    } else if (writeStorageAccessState.shouldShowRationale) {
        coroutineScope.launch {
            val result = snackbarHostState.showSnackbar(
                message = "The storage permission is needed to save the image",
                actionLabel = "Grant Access"
            )

            if (result == SnackbarResult.ActionPerformed) {
                writeStorageAccessState.launchMultiplePermissionRequest()
            }
            actionAfterShare()
        }
    } else {
        writeStorageAccessState.launchMultiplePermissionRequest()
        actionAfterShare()
    }
}


 fun shareBitmap(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, createChooser(intent, "Share your image"), null)
}