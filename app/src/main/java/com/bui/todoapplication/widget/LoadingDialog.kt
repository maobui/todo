package com.bui.todoapplication.widget

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import android.widget.RelativeLayout

object LoadingDialog {
    private var dialog: Dialog? = null

    private fun createDialog(context: Context, cancelable: Boolean): Dialog {
        dialog = Dialog(context)

        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val layout = RelativeLayout(context)
        val progressBar = ProgressBar(context, null, R.attr.progressBarStyleLarge)
        val layoutParams = RelativeLayout.LayoutParams(100, 100)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, layoutParams)
        dialog!!.setContentView(layout)
        dialog!!.setCancelable(cancelable)
        return dialog as Dialog
    }

    fun showLoading(context: Context) {
        if (dialog == null) {
            createDialog(context, false)
        }

        dialog?.takeIf { !it.isShowing }?.show()
    }

    fun hideLoading() {
        dialog?.takeIf { it.isShowing }?.dismiss()
    }

    fun release() {
        dialog?.takeIf { it.isShowing }?.cancel()
    }

}
