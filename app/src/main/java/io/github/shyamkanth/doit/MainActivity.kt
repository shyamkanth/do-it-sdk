package io.github.shyamkanth.doit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.shyamkanth.doit.databinding.ActivityMainBinding
import io.github.shyamkanth.doitsdk.DoitSdk
import io.github.shyamkanth.doitsdk.Utils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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

//        val doItSdk = DoitSdk(this)
//        val bitmap = doItSdk.generateImageWithName("SH", 1)
//        binding.imageView.setImageBitmap(bitmap)
//
//        doItSdk.openInfoDialog(this, "Test", "This is a test message", "Okay", true) {
//            Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
//        }
//
//        doItSdk.openAlertDialog(this, "Alert", "This is something which requires an action", "Yes", "No", false){ alertAction ->
//            if(alertAction == Utils.AlertAction.PRIMARY) {
//                Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show()
//            }
//            if(alertAction == Utils.AlertAction.SECONDARY) {
//                Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
//            }
//        }


    }
}