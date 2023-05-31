package com.app.sportbetting.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.R
import com.app.sportbetting.databinding.FragmentMyAccountBinding
import com.app.sportbetting.models.profile.getProfile.Body
import com.app.sportbetting.models.profile.getProfile.updateProfile.ProfileParamModel
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.*
import com.app.sportbetting.viewmodel.ProfileViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*


class MyAccount : Fragment(), View.OnClickListener {

    var preference: SharedPreference? = null

    lateinit var viewModel: ProfileViewModel
    var binding: FragmentMyAccountBinding? = null
    var dialog: Dialog? = null

    var file: File? = null


    private val IMAGE_DIRECTORY: String = "/wizard"
    private var imageFile: File? = null

    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val requestCode: Int = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = FragmentMyAccountBinding.inflate(layoutInflater)
        }
        initView()
        setObservers()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.calendar?.setOnClickListener(this)
        binding?.updateAccount?.setOnClickListener(this)
        binding?.imageView3?.setOnClickListener {
            checkPermission(requireContext())
        }


    }

    private fun initView() {
        preference = SharedPreference.getInstance(requireContext())
        setupViewModel()

        getProfileData()

    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.ivBack).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tvAppBarLabel).text =
            getString(R.string.my_account)

    }

    private fun getProfileData() {
        if(isAdded){
            viewModel.getProfile()
        }

    }


    var dd = ""
    var mm = ""
    var yy = ""
    @SuppressLint("SetTextI18n")
    private fun setDob() {
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                if ((month+1).toString().length == 1 && day.toString().length == 1) {
                    val dd = day.toString()
                    val mm = (month + 1).toString()
                    val yy = year.toString()

                    binding?.textView12?.text = "$year/0${month + 1}/0$day"
                } else if ((month+1).toString().length == 1) {
                    dd = day.toString()
                    mm = (month + 1).toString()
                    yy = year.toString()

                    binding?.textView12?.text = "$year/0${month + 1}/$day"
                } else if (day.toString().length == 1) {
                    dd = day.toString()
                    mm = (month + 1).toString()
                    yy = year.toString()

                    binding?.textView12?.text = "$year/${month + 1}/0$day"
                } else {
                    dd = day.toString()
                    mm = (month + 1).toString()
                    yy = year.toString()

                    binding?.textView12?.text = "$year/${month + 1}/$day"
                }
            }, mYear, mMonth, mDay
        )

        datePickerDialog.show()
    }


    private fun setObservers() {
        viewModel.getProfileResult.observe(requireActivity(), {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        if(isAdded) {

                            MyApplication.hideLoader()
                            Log.d("TAG", "setObservers: $body")
                            requireActivity().toast(message!!)
                            if (body != null) {
                                setViews(body)
                            }
                        }
                    }
                    Status.LOADING -> {
//                        MyApplication.showLoader(requireContext())
                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        viewModel.updateProfileResult.observe(requireActivity(), {
            it.let { data ->
                val message = data?.data?.message

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        requireActivity().toast(message!!)

                    }
                    Status.LOADING -> {
                        MyApplication.showLoader(requireActivity())
                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })


    }


    private fun setViews(body: Body) {

        if(body.profile_pic==null){
            binding?.imageView3?.setImageResource(R.drawable.ic_ellipse)
        }
        else {
            binding?.imageView3?.let {
                Glide.with(requireContext()).load(Constants.IMAGE_URL + body.profile_pic)
                    .fitCenter()
                    .into(it)
            }
        }
        binding?.tippingEdit?.setText(body.tipping_name)
        binding?.country?.setText(body.country)
        binding?.textView12?.text = body.dob
        binding?.emailAddress?.setText(body.email)
        binding?.firstName?.setText(body.name)
        binding?.surname?.setText(body.surname)
        binding?.timeZone?.setText(body.time_zone)
        if (body.gender == "Male") {
            binding?.male?.isChecked = true
        } else {
            binding?.female?.isChecked = true

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, requestCode, data)
        when (requestCode) {
            1 -> {
                if (data != null) {
                    val thumbnail = data.extras?.get("data") as Bitmap?
                    binding?.imageView3?.setImageBitmap(thumbnail)
                    if (thumbnail != null) {
                        saveImage(thumbnail)
                        val tempUri: Uri = getImageUri(requireContext(), thumbnail)
                        val selectedfile: String = FilePath.getPath(requireContext(), tempUri)
                        file = File(selectedfile)
//                        getProfileChange(File(selectedfile))
                    }
                }
            }
            2 -> {
                if (data != null) {
                    val contentURI = data.data
                    val image: String = FilePath.getPath(requireContext(), contentURI)
                    val bitmap = BitmapFactory.decodeFile(image)
                    saveImage(bitmap)
                    binding?.imageView3?.setImageBitmap(bitmap)
                    file = File(image)
//                    getProfileChange(File(image))
                }
            }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(requireContext())
                }
            }
        }
    }

    private fun checkPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), permissionList, requestCode)
        } else {
            optionDialog("Take photo", arrayOf("Camera", "Gallery"))
        }
    }

    private fun saveImage(bitmap: Bitmap): String? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory()
                .toString() + IMAGE_DIRECTORY
        )
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        try {
            imageFile = File(
                wallpaperDirectory,
                Calendar.getInstance().timeInMillis.toString() + ".jpg"
            )
            imageFile?.createNewFile()
            val fo = File(imageFile.toString())
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(imageFile?.path),
                arrayOf("image/jpeg"),
                null
            )
            return imageFile?.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun uploadProfilePic(body: Body?) {
        binding?.imageView3?.let {
            Glide.with(requireContext()).load(Constants.IMAGE_URL + body?.profile_pic).fitCenter()
                .into(it)
        }


    }


    private fun getImageUri(photo1: Context, photo: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            photo,
            System.currentTimeMillis().toString(),
            null
        )
        return Uri.parse(path)
    }


    private fun optionDialog(
        title: String,
        list: Array<CharSequence>,

        buttonRequired: Boolean = false
    ) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
        builder.setCancelable(true)
        builder.setItems(list) { dialog, position ->
            when (position) {
                0 -> {
                    cameraIntent(1)
                }
                1 -> {
                    galleryIntent(2)
                }
                else -> {
                    dialog.dismiss()
                }
            }
        }
        if (buttonRequired) {
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->

            })
        }
        builder.show()
    }


    private fun getProfileChange(file: File) {
    }

    private fun cameraIntent(requestCode: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, requestCode)
    }

    private fun galleryIntent(requestCode: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        requireActivity().startActivityForResult(galleryIntent, requestCode)
    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(ProfileViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MyAccount {
            return MyAccount()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.calendar -> {
                setDob()
            }
            binding?.imageView3 -> {
                checkPermission(requireContext())
            }
            binding?.updateAccount -> {
                if (isFieldEmpty()) {
                    val firstname = binding?.firstName?.text.toString()
                    val surname = binding?.surname?.text.toString()
                    val dob = binding?.textView12?.text.toString()
                    val tipping = binding?.tippingEdit?.text.toString()
                    val country = binding?.country?.text.toString()
                    val email = binding?.emailAddress?.text.toString()
                    val gender = "male"
                    val profileModel =
                        ProfileParamModel(firstname, surname, tipping, dob, country, gender, email)
                    file?.let { viewModel.updateProfile(profileModel, it) }
                }
            }
        }
    }

    private fun isFieldEmpty(): Boolean {
        var isEmpty = true
        when {
            binding?.emailAddress?.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                requireActivity().toast("Enter email")
            }
            binding?.firstName?.text?.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(requireContext(), "Enter Your First name", Toast.LENGTH_SHORT)
                    .show()
            }

            binding?.surname?.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(requireContext(), "Enter Your surname", Toast.LENGTH_SHORT)
                    .show()
            }

            binding?.tippingEdit?.text.toString().isNullOrBlank() -> {
                isEmpty = false
                Toast.makeText(
                    requireContext(),
                    "Enter Your username (Tipping name)",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }


        }
        return isEmpty
    }

}