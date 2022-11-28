package com.yassir.moviesapp.Utilities

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.yassir.moviesapp.R

class LoadingDialog constructor(activity: Activity) {
    var activity: Activity? = null
    var dialog: AlertDialog? = null

    init {
        this.activity = activity
    }

    fun startLoadingDialog() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.TransparentDialog)
        var inflator: LayoutInflater? = activity?.layoutInflater
        builder.setView(inflator?.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder?.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }

}