package io.github.shyamkanth.doit

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import io.github.shyamkanth.doit.databinding.ActivityMainBinding
import io.github.shyamkanth.doitsdk.DoitSdk
import io.github.shyamkanth.doitsdk.helper.ImagePickerBottomSheet
import io.github.shyamkanth.doitsdk.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var doItSdk: DoitSdk
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        doItSdk = DoitSdk(this)
        setOnClicks()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setOnClicks() {
        val minDate = Calendar.getInstance().apply { set(2020, 0, 1) }.timeInMillis  // Jan 1, 2020
        val maxDate = Calendar.getInstance().apply { set(2025, 11, 31) }.timeInMillis // Dec 31, 2025

        binding.btnDatePicker.setOnClickListener {
            doItSdk.showDatePicker(this, binding.etDate)
        }

        binding.btnDatePickerWithMinDate.setOnClickListener {
            doItSdk.showDatePickerWithMinDate(this, binding.etDate, minDate)
        }

        binding.btnDatePickerWithMaxDate.setOnClickListener {
            doItSdk.showDatePickerWithMaxDate(this, binding.etDate, maxDate)
        }

        binding.btnDatePickerInRange.setOnClickListener {
            doItSdk.showDatePickerInRange(this, binding.etDate, minDate, maxDate)
        }

        binding.btnGetTodayDate.setOnClickListener {
            Snackbar.make(binding.root, doItSdk.getTodayDate(), Snackbar.LENGTH_SHORT).show()
        }

        binding.btnInfoDialog.setOnClickListener {
            val dialogTitle = "Information"
            val dialogMessage = "This is some information to be shown to user. This can include terns and user, privacy policy and anything you want."
            val buttonPrimaryText = "Okay"
            val isCancelable = true
            doItSdk.openInfoDialog(this, dialogTitle, dialogMessage, buttonPrimaryText, isCancelable) {
                // You can perform any action here after the dismissal of the dialog if you want.. it's completely optional
            }
        }

        binding.btnAlertDialog.setOnClickListener {
            val alertTitle = "Action"
            val alertMessage = "This is some action to be taken dialog with two option. This can include \"want to continue\", \"privacy policy\" and anything you want."
            val buttonPrimaryText = "Accept"
            val buttonSecondaryText = "Reject"
            val isCancelable = false
            doItSdk.openAlertDialog(this, alertTitle, alertMessage, buttonPrimaryText, buttonSecondaryText, isCancelable){ alertAction ->
                if(alertAction == Utils.AlertAction.PRIMARY) {
                    // Take action if primary button is clicked
                    Snackbar.make(binding.root, "$buttonPrimaryText clicked", Snackbar.LENGTH_SHORT).show()
                }
                if(alertAction == Utils.AlertAction.SECONDARY) {
                    // Take action if secondary button is clicked
                    Snackbar.make(binding.root,"$buttonSecondaryText clicked", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnShowLoader.setOnClickListener {
            doItSdk.showLoader(this, "Downloading data, please wait...")
            lifecycleScope.launch {
                delay(5000)
                doItSdk.hideLoader()
            }
        }

        binding.btnCheckInternet.setOnClickListener {
            val bool = doItSdk.isInternetAvailable(this)
            if(bool){
                val dialogTitle = "Success"
                val dialogMessage = "You are connected to Internet."
                val buttonPrimaryText = "Okay"
                val isCancelable = true
                doItSdk.openInfoDialog(this, dialogTitle, dialogMessage, buttonPrimaryText, isCancelable)
            } else {
                val dialogTitle = "Failure"
                val dialogMessage = "You are not connected to Internet."
                val buttonPrimaryText = "Okay"
                val isCancelable = true
                doItSdk.openInfoDialog(this, dialogTitle, dialogMessage, buttonPrimaryText, isCancelable)
            }
        }

        binding.btnImagePicker.setOnClickListener {
            doItSdk.requestImageAccessPermission(this, 100)
            val imagePicker = ImagePickerBottomSheet(this){ selectedUri ->
                Snackbar.make(binding.root, "$selectedUri", Snackbar.LENGTH_SHORT).show()
            }
            imagePicker.show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            Snackbar.make(binding.root, "Permission Denied", Snackbar.LENGTH_SHORT).show()
        }
    }
}