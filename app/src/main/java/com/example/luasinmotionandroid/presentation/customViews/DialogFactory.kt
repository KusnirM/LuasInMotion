package com.example.luasinmotionandroid.presentation.customViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.luasinmotionandroid.R
import kotlinx.android.synthetic.main.alert_dialog_default.view.*

object DialogFactory {

    fun showMessage(
        context: Context?,
        title: String?,
        body1: String? = null,
        callOnOk: () -> Unit = {},
        callOnCancel: () -> Unit = {},
        okButtonLabel: String? = null,
        cancelButtonLabel: String? = null
    ): AlertDialog? {
        if (context == null) return null
        val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_default, null)
        val dialog = AlertDialog.Builder(context, R.style.DialogNoTitle).setView(view).create()
        view.title.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
        view.title.text = title
        view.body1.visibility = if (body1.isNullOrEmpty()) View.GONE else View.VISIBLE
        view.body1.text = body1

        view.ok.text = okButtonLabel ?: context.getString(R.string.ok)
        view.ok.setOnClickListener {
            dialog.dismiss()
            callOnOk()
        }

        view.cancel.visibility = if (cancelButtonLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
        view.cancel.text = cancelButtonLabel ?: context.getString(R.string.cancel)
        view.cancel.setOnClickListener {
            dialog.dismiss()
            callOnCancel()
        }
        dialog.show()
        return dialog
    }
}
