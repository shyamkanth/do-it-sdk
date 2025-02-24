package io.github.shyamkanth.doitsdk.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import io.github.shyamkanth.doitsdk.databinding.LayoutAlertBinding
import io.github.shyamkanth.doitsdk.databinding.LayoutImageInsertBinding
import io.github.shyamkanth.doitsdk.databinding.LayoutInfoBinding
import io.github.shyamkanth.doitsdk.databinding.LayoutLoaderBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {

    enum class AlertAction {
        PRIMARY,
        SECONDARY
    }

    @SuppressLint("DefaultLocale")
    fun showDatePicker(editText: EditText, context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    @SuppressLint("DefaultLocale")
    fun showDatePickerWithMinDate(editText: EditText, context: Context, minDate: Long) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            }, year, month, day
        )
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.show()
    }

    @SuppressLint("DefaultLocale")
    fun showDatePickerWithMaxDate(editText: EditText, context: Context, maxDate: Long) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate = maxDate
        datePickerDialog.show()
    }

    @SuppressLint("DefaultLocale")
    fun showDatePickerInRange(editText: EditText, context: Context, minDate: Long, maxDate: Long) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            }, year, month, day
        )
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate
        datePickerDialog.show()
    }


    fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun generateInitialsImage(
        initials: String,
        position: Int,
        diameter: Int = 100
    ): Bitmap {
        val colorArray = listOf(
            Color.parseColor("#E9CAF8"),
            Color.parseColor("#CAE7F8"),
            Color.parseColor("#D0F8CA"),
            Color.parseColor("#F8D2CA"),
            Color.parseColor("#FFD700"),
            Color.parseColor("#FFA07A"),
            Color.parseColor("#98FB98"),
            Color.parseColor("#87CEEB"),
            Color.parseColor("#FF69B4"),
            Color.parseColor("#20B2AA")
        )

        val backgroundColor = colorArray[position % colorArray.size]

        val bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
        }

        val radius = diameter / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        paint.color = Color.BLACK
        paint.textSize = diameter / 2.5f
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.textAlign = Paint.Align.CENTER

        val bounds = Rect()
        paint.getTextBounds(initials, 0, initials.length, bounds)
        val y = radius - (bounds.top + bounds.bottom) / 2f

        canvas.drawText(initials, radius, y, paint)

        return bitmap
    }


    fun openAlertDialog(
        activity: Activity,
        alertTitle: String,
        alertMessage: String,
        btnPrimaryText: String,
        btnSecondaryText: String,
        isCancelable: Boolean,
        onResult: (AlertAction) -> Unit
    ) {
        val alertBinding = LayoutAlertBinding.inflate(LayoutInflater.from(activity))
        val alertDialog = AlertDialog.Builder(activity)
            .setView(alertBinding.root)
            .setCancelable(isCancelable)
            .create()

        alertBinding.tvAlertTitle.text = alertTitle
        alertBinding.tvAlertMessage.text = alertMessage
        alertBinding.btnPrimary.text = btnPrimaryText
        alertBinding.btnSecondary.text = btnSecondaryText
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        alertBinding.btnSecondary.setOnClickListener {
            onResult(AlertAction.SECONDARY)
            alertDialog.dismiss()
        }

        alertBinding.btnPrimary.setOnClickListener {
            onResult(AlertAction.PRIMARY)
            alertDialog.dismiss()
        }

    }

    fun openInfoDialog(
        activity: Activity,
        dialogTitle: String,
        dialogMessage: String,
        btnPrimaryText: String,
        isCancelable: Boolean,
        onDismiss: () -> Unit = {}
    ) {
        val alertBinding = LayoutInfoBinding.inflate(LayoutInflater.from(activity))
        val alertDialog = AlertDialog.Builder(activity)
            .setView(alertBinding.root)
            .setCancelable(isCancelable)
            .create()

        alertBinding.tvAlertTitle.text = dialogTitle
        alertBinding.tvAlertMessage.text = dialogMessage
        alertBinding.btnPrimary.text = btnPrimaryText
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        alertBinding.btnPrimary.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.setOnDismissListener {
            onDismiss()
        }

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
        openImageDialogInternal(activity, alertTitle, imgUri, null, btnPrimaryText, btnSecondaryText, isCancelable, onResult)
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
        openImageDialogInternal(activity, alertTitle, null, imgBitmap, btnPrimaryText, btnSecondaryText, isCancelable, onResult)
    }

    private fun openImageDialogInternal(
        activity: Activity,
        alertTitle: String,
        imgUri: Uri?,
        imgBitmap: Bitmap?,
        btnPrimaryText: String,
        btnSecondaryText: String,
        isCancelable: Boolean,
        onResult: (AlertAction) -> Unit
    ) {
        val alertBinding = LayoutImageInsertBinding.inflate(LayoutInflater.from(activity))
        val alertDialog = AlertDialog.Builder(activity)
            .setView(alertBinding.root)
            .setCancelable(isCancelable)
            .create()

        alertBinding.tvAlertTitle.text = alertTitle

        imgUri?.let { alertBinding.image.setImageURI(it) }
        imgBitmap?.let { alertBinding.image.setImageBitmap(it) }

        alertBinding.btnPrimary.text = btnPrimaryText
        alertBinding.btnSecondary.text = btnSecondaryText
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        alertBinding.btnSecondary.setOnClickListener {
            onResult(AlertAction.SECONDARY)
            alertDialog.dismiss()
        }

        alertBinding.btnPrimary.setOnClickListener {
            onResult(AlertAction.PRIMARY)
            alertDialog.dismiss()
        }
    }


    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private var alertLoader: AlertDialog? = null

    fun showLoader(activity: Activity, loaderText: String? = null): AlertDialog? {
        if (activity.isFinishing) return null  // Prevent crash if activity is finishing

        val alertBinding = LayoutLoaderBinding.inflate(LayoutInflater.from(activity))
        alertLoader = AlertDialog.Builder(activity)
            .setView(alertBinding.root)
            .setCancelable(false)
            .create()

        if(loaderText == null){
            alertBinding.progressText.visibility = View.GONE
        } else {
            alertBinding.progressText.visibility = View.VISIBLE
            alertBinding.progressText.text = loaderText
        }
        alertLoader?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertLoader?.show()

        return alertLoader
    }

    fun hideLoader() {
        alertLoader?.dismiss()
        alertLoader = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun requestImageAccessPermission(activity: Activity, requestCode: Int) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    requestCode
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {}
            else -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    requestCode
                )
            }
        }
    }

}