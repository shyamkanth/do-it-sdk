package io.github.shyamkanth.doitsdk

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.EditText

class DoitSdk(private val context: Context) {

    fun generateImageWithName(
        initials: String,
        position: Int
    ): Bitmap{
        return Utils.generateInitialsImage(initials = initials, position = position)
    }

    fun shoeDatePicker(
        context: Context,
        editText: EditText,
    ) {
        Utils.showDatePicker(editText, context)
    }

    fun getTodayDate() : String {
        return Utils.getTodayDate()
    }

    fun openAlertDialog(
        activity: Activity,
        alertTitle: String,
        alertMessage: String,
        buttonPrimaryText: String,
        buttonSecondaryText: String,
        isCancelable: Boolean,
        onResult: (Utils.AlertAction) -> Unit
    ){
        Utils.openAlertDialog(
            activity, alertTitle, alertMessage, buttonPrimaryText, buttonSecondaryText, isCancelable
        ) { alertAction: Utils.AlertAction ->
            if(alertAction == Utils.AlertAction.PRIMARY){
                onResult(Utils.AlertAction.PRIMARY)
            }
            if(alertAction == Utils.AlertAction.SECONDARY){
                onResult(Utils.AlertAction.SECONDARY)
            }
        }
    }

    fun openInfoDialog(
        activity: Activity,
        dialogTitle: String,
        dialogMessage: String,
        buttonPrimaryText: String,
        isCancelable: Boolean,
        onDismiss: () -> Unit = {}
    ){
        Utils.openInfoDialog(activity, dialogTitle, dialogMessage, buttonPrimaryText, isCancelable, onDismiss)
    }

    fun hideKeyboard(context: Context, view: View) {
        Utils.hideKeyboard(context, view)
    }
}