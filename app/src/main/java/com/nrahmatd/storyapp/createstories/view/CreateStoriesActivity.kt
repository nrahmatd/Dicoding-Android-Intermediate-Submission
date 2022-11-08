package com.nrahmatd.storyapp.createstories.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.baseview.BaseActivity
import com.nrahmatd.storyapp.createstories.viewmodel.CreateStoriesViewModel
import com.nrahmatd.storyapp.createstories.viewmodel.CreateStoriesViewModelFactory
import com.nrahmatd.storyapp.databinding.ActivityCreateStoriesBinding
import com.nrahmatd.storyapp.dialog.BottomSheetDialogChoose
import com.nrahmatd.storyapp.utils.GlobalVariable
import com.nrahmatd.storyapp.utils.MediaUtility
import com.nrahmatd.storyapp.utils.MediaUtility.reduceFileImage
import com.nrahmatd.storyapp.utils.MediaUtility.uriToFile
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.log
import com.nrahmatd.storyapp.utils.sendNotify
import com.nrahmatd.storyapp.utils.setRoundedOnImageView
import com.nrahmatd.storyapp.utils.toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CreateStoriesActivity : BaseActivity<ActivityCreateStoriesBinding>() {

    /**
     * Index 0: For validation image is ready
     * Index 1 : For validation description
     */
    private val isValidationForm = arrayOf(false, false)
    private lateinit var currentPhotoPath: String
    private var currentLocation: Location? = null
    private var getFile: File? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val createStoriesViewModel: CreateStoriesViewModel by viewModels {
        CreateStoriesViewModelFactory(this)
    }

    companion object {
        const val CREATE_STORIES = 1
    }

    override val bindingInflater: (LayoutInflater) -> ActivityCreateStoriesBinding
        get() = ActivityCreateStoriesBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        initView()
//        initViewModel()
        initOnClick()
        getResponse()
    }

    override fun statusBarColor(): Int = 0

    private fun initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.apply {
            btnUploadImage.setImageDrawable(
                ContextCompat.getDrawable(
                    this@CreateStoriesActivity,
                    R.drawable.ic_cloud_upload
                )
            )
            etDescription.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    isValidationForm[1] = p0?.isNotEmpty() == true
                    validateButtonIsEnable()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
    }

//    private fun initViewModel() {
//        createStoriesViewModel = ViewModelProvider(
//            this,
//            CreateStoriesViewModelFactory()
//        )[CreateStoriesViewModel::class.java]
//    }

    private fun initOnClick() {
        binding.apply {
            btnUploadImage.setOnClickListener {
                showBottomDialogChoose()
            }
            switchShareLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    getLastLocation()
                else
                    currentLocation = null
            }
            btnSave.setOnClickListener {
                loading()
                createStories()
            }
        }
    }

    private fun showBottomDialogChoose() {
        BottomSheetDialogChoose().apply {
            show(supportFragmentManager, "Show")
            listener = object : BottomSheetDialogChoose.OnClickListener {
                override fun onClick(type: Int) {
                    when (type) {
                        BottomSheetDialogChoose.CAMERA -> startIntentCamera()
                        else -> startIntentGallery()
                    }
                    dismiss()
                }
            }
        }
    }

    private fun validateFormIsValid(): Boolean =
        isValidationForm.all { it } and binding.etDescription.text.toString()
            .isNotEmpty()

    private fun validateButtonIsEnable() {
        binding.apply {
            btnSave.isEnabled = validateFormIsValid()
            if (validateFormIsValid())
                btnSave.setBackgroundResource(R.drawable.ripple_rounded_grey)
            else
                btnSave.setBackgroundResource(R.drawable.ripple_rounded_primary)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val file = File(currentPhotoPath).also { getFile = it }
            val os: OutputStream

            val bitmap = BitmapFactory.decodeFile(getFile?.path)
            val exif = ExifInterface(currentPhotoPath)
            val orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            val rotatedBitmap: Bitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(bitmap, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(bitmap, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(bitmap, 270)
                ExifInterface.ORIENTATION_NORMAL -> bitmap
                else -> bitmap
            }

            try {
                os = FileOutputStream(file)
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()

                getFile = file
            } catch (e: Exception) {
                e.printStackTrace()
            }

            isValidationForm[0] = true
            binding.imageView.setImageBitmap(rotatedBitmap)
            setRoundedOnImageView(binding.imageView, resources.getDimension(R.dimen.distance_16))
        }
    }

    /**
     * Start implicit intent camera
     */
    private fun startIntentCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        MediaUtility.createTempFile(application).also {
            val photoUri = FileProvider.getUriForFile(
                this,
                "com.nrahmatd.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            uriToFile(selectedImg, this).also { getFile = it }

            isValidationForm[0] = true
            binding.imageView.setImageURI(selectedImg)
            setRoundedOnImageView(binding.imageView, resources.getDimension(R.dimen.distance_16))
        }
    }

    /**
     * Start implicit intent gallery
     */
    private fun startIntentGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getLastLocation()
            }
            else -> {
                toast(this, getString(R.string.permission_denied))
                binding.switchShareLocation.isChecked = false
            }
        }
    }

    /**
     * Get current Gallery
     */
    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            /** Location permission granted */
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    this.currentLocation = location
                    log(
                        "Current Location",
                        "getLastLocation: ${location.latitude}, ${location.longitude}"
                    )
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.please_activate_location_message),
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.switchShareLocation.isChecked = false
                }
            }
        } else {
            /**
             * Location permission denied
             * */
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun createStories() {
        binding.apply {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val description =
                etDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val latitude =
                currentLocation?.latitude.toString().toRequestBody("text/plain".toMediaType())
            val longitude =
                currentLocation?.longitude.toString().toRequestBody("text/plain".toMediaType())

            createStoriesViewModel.createStories(
                CREATE_STORIES,
                imageMultipart,
                description,
                latitude,
                longitude
            )
        }
    }

    private fun getResponse() {
        createStoriesViewModel.response.observe(this) {
            when (it.code) {
                ResponseHelper.ERROR -> {
                    toast(this, it.response as String)
                    loadingDone()
                }
                CREATE_STORIES -> {
                    toast(this, getString(R.string.successfully_add_stories))
                    sendNotify(GlobalVariable.NOTIFY_STORIES, true)
                    finish()
                }
            }
        }
    }

    private fun loading() {
        binding.apply {
            btnSave.visibility = View.INVISIBLE
            spinKitLoading.visibility = View.VISIBLE
        }
    }

    private fun loadingDone() {
        binding.apply {
            btnSave.visibility = View.VISIBLE
            spinKitLoading.visibility = View.GONE
        }
    }
}
