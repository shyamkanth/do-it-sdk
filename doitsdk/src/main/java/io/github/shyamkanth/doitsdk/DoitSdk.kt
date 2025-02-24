package io.github.shyamkanth.doitsdk

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import io.github.shyamkanth.doitsdk.utils.Utils
import io.github.shyamkanth.doitsdk.utils.Utils.AlertAction

class DoitSdk(private val context: Context) {

    fun generateImageWithName(
        initials: String,
        position: Int
    ): Bitmap{
        return Utils.generateInitialsImage(initials = initials, position = position)
    }

    fun showDatePicker(
        context: Context,
        editText: EditText,
    ) {
        Utils.showDatePicker(editText, context)
    }

    fun showDatePickerWithMinDate(context: Context, editText : EditText, minDate: Long){
        Utils.showDatePickerWithMinDate(editText, context, minDate)
    }

    fun showDatePickerWithMaxDate(context: Context, editText : EditText, maxDate: Long){
        Utils.showDatePickerWithMaxDate(editText, context, maxDate)
    }

    fun showDatePickerInRange(context: Context, editText: EditText, minDate: Long, maxDate: Long){
        Utils.showDatePickerInRange(editText, context, minDate, maxDate)
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

    fun showLoader(activity: Activity, loaderText: String? = null){
        Utils.showLoader(activity, loaderText)
    }

    fun hideLoader(){
        Utils.hideLoader()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isInternetAvailable(context: Context): Boolean {
        return Utils.isInternetAvailable(context)
    }

    fun requestImageAccessPermission(activity: Activity, requestCode: Int) {
        Utils.requestImageAccessPermission(activity, requestCode)
    }

    fun openImageDialog(
        activity: Activity,
        alertTitle: String,
        imgUri: Uri?,
        btnPrimaryText: String,
        btnSecondaryText: String,
        isCancelable: Boolean,
        onResult: (AlertAction) -> Unit
    ) {
        Utils.openImageDialog(activity, alertTitle, imgUri, btnPrimaryText, btnSecondaryText, isCancelable) { alertAction: Utils.AlertAction ->
            if(alertAction == Utils.AlertAction.PRIMARY){
                onResult(Utils.AlertAction.PRIMARY)
            }
            if(alertAction == Utils.AlertAction.SECONDARY){
                onResult(Utils.AlertAction.SECONDARY)
            }
        }
    }

    fun openImageDialog(
        activity: Activity,
        alertTitle: String,
        imgBitmap: Bitmap?,
        btnPrimaryText: String,
        btnSecondaryText: String,
        isCancelable: Boolean,
        onResult: (AlertAction) -> Unit
    ) {
        Utils.openImageDialog(activity, alertTitle, imgBitmap, btnPrimaryText, btnSecondaryText, isCancelable){ alertAction: Utils.AlertAction ->
            if(alertAction == Utils.AlertAction.PRIMARY){
                onResult(Utils.AlertAction.PRIMARY)
            }
            if(alertAction == Utils.AlertAction.SECONDARY){
                onResult(Utils.AlertAction.SECONDARY)
            }
        }
    }
}