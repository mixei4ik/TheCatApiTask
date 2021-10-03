package com.example.thecatapitask

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.load
import coil.request.ImageRequest
import com.example.thecatapitask.databinding.FragmentCatInfoBinding
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

private const val CAT_URL = "url"
private const val CAT_ID = "id"

class CatInfoFragment : Fragment() {

    private var param1: String = ""
    private var param2: String = ""

    private var _binding: FragmentCatInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(CAT_URL).toString()
            param2 = it.getString(CAT_ID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            imageCat.load(param1)
            textCatId.text = param2
            saveCat.setOnClickListener {getBitmapFromUrlAndSave(param1) }
        }
    }

    private fun getBitmapFromUrlAndSave(bitmapURL: String) = lifecycleScope.launch {
        val imageLoader = Coil.imageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(param1)
            .build()
        try {
            val downloadedBitmap = (imageLoader.execute(request).drawable as BitmapDrawable).bitmap
            saveToGallery(downloadedBitmap)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveToGallery(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Saved to Photos", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(CAT_URL, param1)
                    putString(CAT_ID, param2)
                }
            }
    }
}