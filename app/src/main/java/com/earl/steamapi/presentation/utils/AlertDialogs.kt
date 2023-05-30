package com.earl.steamapi.presentation.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.earl.steamapi.R

fun showErrorAlertDialog(context: Context, error: String): AlertDialog {
    return AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.network_error))
        .setMessage(error)
        .setNeutralButton(context.getString(R.string.okay)) { _, _ -> }
        .create()
}