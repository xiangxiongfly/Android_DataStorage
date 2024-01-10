package com.example.myapplication.storage

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityFileStorage2Binding
import com.example.myapplication.isExternalStorageManager
import com.example.myapplication.toast
import java.io.*

const val REQUEST_IMAGE_SINGLE_CODE = 123
const val REQUEST_IMAGE_MULTIPLE_CODE = 456

/**
 * Android11及以上版本
 */
class FileStorage2Activity : BaseActivity() {
    private lateinit var viewBinding: ActivityFileStorage2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFileStorage2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    fun requestReadPermission(view: View) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSIONS_CODE
        )
    }

    fun checkScopedStorage(view: View) {
        toast("检查是否授予管理所有文件的权限：${isExternalStorageManager()}")
    }

    /**
     * 通过MediaStore保存图片到相册
     */
    fun saveImage(view: View) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.aaa)
//        val displayName = "${System.currentTimeMillis()}.png"
        val displayName = "aaa.png"
        val mineType = "image/png"
        val format = Bitmap.CompressFormat.PNG

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, mineType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
                //DIRECTORY_DCIM 相册，DIRECTORY_PICTURES 图片
            } else {
                put(
                    MediaStore.MediaColumns.DATA,
                    Environment.getExternalStorageDirectory().path + File.separator + Environment.DIRECTORY_DCIM + File.separator + displayName
                )
            }
        }

        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            val outputStream = contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                bitmap.compress(format, 100, outputStream)
                outputStream.close()
                toast("保存图片成功")
            }
        }
    }

    /**
     * 可以获取本应用程序的图片
     * 其他应用程序的图片需要申请READ_EXTERNAL_STORAGE权限
     */
    fun getImage(view: View) {
        val displayName = "aaa.png"
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Images.Media.DISPLAY_NAME + "=?"
        var selectionArgs = arrayOf(displayName)
        val cursor = contentResolver.query(uri, null, selection, selectionArgs, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val resultUri = ContentUris.withAppendedId(uri, id)
                Log.e("TAG", "查询成功: $resultUri") //content://media/external/images/media/1000000502
                showImageView(resultUri)
            }
            cursor.close()
        }
    }

    private fun showImageView(uri: Uri) {
        // 方式一：
        viewBinding.imageView.setImageURI(uri)

        // 方式二：
//        Glide.with(this).load(uri).into(viewBinding.imageView)

        // 方式三：
//        val fd = contentResolver.openFileDescriptor(uri, "r")
//        if (fd != null) {
//            val bitmap = BitmapFactory.decodeFileDescriptor(fd.fileDescriptor)
//            fd.close()
//            viewBinding.imageView.setImageBitmap(bitmap)
//        }
    }

    fun getAllImage(view: View) {
        startActivity(Intent(this, PhotosActivity::class.java))
    }

    /**
     * 只能删除本应用程序保存的图片
     */
    fun deleteImage(view: View) {
        val displayName = "aaa.png"
//        val displayName = "ccc.jpg"
        val selection = MediaStore.Images.Media.DISPLAY_NAME + "=?"
        val selectionArgs = arrayOf(displayName)
        val row: Int = contentResolver.delete(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            selection,
            selectionArgs
        )
        Log.e("TAG", "删除成功: $row")
    }

    /**
     * 只能修改本应用程序保存的图片
     */
    fun updateImage(view: View) {
        val displayName = "aaa.png"
        val selection = MediaStore.Images.Media.DISPLAY_NAME + "=?"
        val selectionArgs = arrayOf(displayName)
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "bbb.png")
        val row = contentResolver.update(
            Uri.parse("content://media/external/images/media/1000000534"),
            contentValues,
            selection, selectionArgs
        )
        Log.e("TAG", "修改成功：$row")
    }

    /**
     * 保存文件到Downloads目录下
     */
    fun saveDownload(view: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            toast("只支持Android_10及以上版本")
            return
        }
        val content = "这是一些测试数据，分区存储123"
        val fileName = "test.txt"
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resultUri =
            contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (resultUri != null) {
            val outputStream = contentResolver.openOutputStream(resultUri)
            if (outputStream != null) {
                val bos = BufferedOutputStream(outputStream)
                val bis = BufferedInputStream(ByteArrayInputStream(content.toByteArray()))
                val buffer = ByteArray(1024)
                var len = 0
                while (bis.read(buffer).also { len = it } != -1) {
                    bos.write(buffer, 0, len)
                    bos.flush()
                }
                bos.close()
                bis.close()
            }
        }
    }

    /**
     * 使用文件选择器
     */
    fun pickSingleImage(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_IMAGE_SINGLE_CODE)
    }

    /**
     * 使用文件选择器
     */
    fun pickMultipleImage(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) //多选
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_IMAGE_MULTIPLE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_SINGLE_CODE -> {
                    data?.let {
                        val uri = it.data
                        Log.e("TAG", "uri: $uri")
                        viewBinding.imageView.setImageURI(uri)
                    }
                }
                REQUEST_IMAGE_MULTIPLE_CODE -> {
                    if (data != null && data.clipData != null) {
                        with(data.clipData!!) {
                            for (i in 0 until this.itemCount) {
                                Log.e("TAG", "${i} uri: ${this.getItemAt(i).uri}")
                            }
                        }
                    }
                }
            }
        }
    }

    fun copyImage(view: View) {
        val uri = Uri.parse("content://media/external/images/media/68")
        val inputStream = contentResolver.openInputStream(uri)
        val dir = cacheDir
        val imageName = "helloabc.jpg"
        val imageFile = File(dir, imageName)
        val fileOutputStream = FileOutputStream(imageFile)
        val bos = BufferedOutputStream(fileOutputStream)
        val bis = BufferedInputStream(inputStream)
        val buffer = ByteArray(1024)
        var len = 0
        while (bis.read(buffer).also { len = it } != -1) {
            bos.write(buffer, 0, len)
            bos.flush()
        }
        bos.close()
        bis.close()
        viewBinding.imageView.setImageURI(Uri.parse(imageFile.absolutePath))
    }

    /**
     * 申请所有权
     */
    fun requestAllFilesPermission(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            AlertDialog.Builder(this)
                .setMessage("本程序需要授予管理所有文件的权限")
                .setPositiveButton("确定") { dialog, which ->
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    startActivity(intent)
                }
                .show()
        } else {
            toast("您已获得访问所有文件权限")
        }
    }

    // 测试所有权
    fun testWrite(view: View) {
        if (isExternalStorageManager()) {
            val content = "hello test123456"
            val fileName = "test123456.txt"
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            val output = FileOutputStream(file)
            output.write(content.toByteArray())
            output.flush()
            output.close()
        } else {
            toast("未授权")
        }
    }

    // 测试所有权
    fun testRead(view: View) {
        if (isExternalStorageManager()) {
            val fileName = "test123456.txt"
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            if (file.exists()) {
                var content = ""
                val input = FileInputStream(file)
                val buffer = ByteArray(1024)
                var len = 0
                while ((input.read(buffer).also { len = it }) != -1) {
                    val str = String(buffer, 0, len)
                    content += str
                }
                input.close()
                Log.e("TAG", "content: $content")
            }
        } else {
            toast("未授权")
        }
    }
}