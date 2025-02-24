package io.github.shyamkanth.doitsdk.helper

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.shyamkanth.doitsdk.adapter.ImageAdapter
import io.github.shyamkanth.doitsdk.databinding.BottomSheetImagePickerBinding
import io.github.shyamkanth.doitsdk.utils.Utils

class ImagePickerBottomSheet(
    private val context: Context,
    private val onImageSelected: (Uri) -> Unit
) : BottomSheetDialog(context) {

    private lateinit var binding: BottomSheetImagePickerBinding
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageAdapter = ImageAdapter { selectedUri ->
            Utils.openImageDialog(context as Activity, "Confirm Image", selectedUri, "Insert", "Choose again", true){
                if(it == Utils.AlertAction.PRIMARY){
                    onImageSelected(selectedUri)
                    dismiss()
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = imageAdapter
        }

        fetchImages()
    }

    private fun fetchImages() {
        val images = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null, null, "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(columnIndex)
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(uri)
            }
        }
        imageAdapter.submitList(images)
    }
}
