package com.bedirhandag.arabamcomandroidsample.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.ImageView
import com.bedirhandag.arabamcomandroidsample.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String, ) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_no_photo)
        .into(this)
}

var alert: AlertDialog? = null
fun Activity.showAlert(
    title: String? = null,
    msg: String,
    iconResId: Int? = null,
    block: (() -> Unit)
) {
    if (!isFinishing && !isDestroyed) {
        AlertDialog.Builder(this).apply {
            title?.let {
                setTitle(title)
            }
            setMessage(msg)
            iconResId?.let {
                setIcon(iconResId)
            }
            setCancelable(false)
            setPositiveButton(getString(R.string.placeholder_ok)) { _, _ -> block() }
            setNegativeButton(getString(R.string.placeholder_cancel)) { d, _ -> d.cancel() }
        }.also { _dialog ->
            when {
                msg.isNotEmpty() -> {
                    alert = _dialog.create()
                    _dialog.show()
                }
            }
        }
    }
}