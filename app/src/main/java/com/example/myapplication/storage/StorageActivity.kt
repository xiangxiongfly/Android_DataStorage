package com.example.myapplication.storage

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityStorageBinding
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class StorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

//        binding.externalStorageManagerInfo.setOnClickListener {
//            externalStorageManagerInfo()
//        }
//
//        binding.mediastoreSave.setOnClickListener {
//            mediaStoreSave()
//        }
//
//        binding.mediastoreQuery.setOnClickListener {
////            mediastoreQuery()
//        }
//
//        binding.mediastoreDelete.setOnClickListener {
////            mediastoreDelete()
//        }
//
//        binding.mediastoreUpdate.setOnClickListener {
////            mediastoreUpdate()
//        }
//
//        binding.scopedStorageSaf.setOnClickListener {
//            saf()
//        }

        binding.requestAllFilePermission.setOnClickListener {
            requestAllFilesAccessPermission()
        }

        binding.test.setOnClickListener {
            test()
        }

        binding.test2.setOnClickListener {
            test2()
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                111
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 111 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show()
        }
    }

//    /**
//     * 内部存储一些信息
//     */
//    fun getInnerDataInfo() {
//        //获取所有文件名
//        fileList().forEach {
//            Log.e("TAG", "内部存储文件：$it")
//        }
//
//        Log.e("TAG", "内部文件存储路径：$filesDir")
//        Log.e("TAG", "内部缓存文件存储路径：$cacheDir")
//    }
//
//    /**
//     * 内部存储保存数据
//     */
//    fun saveData() {
//        val content = "hello 内部存储"
//        val fileName = "内部存储测试.txt"
//        val output = openFileOutput(fileName, MODE_PRIVATE)
//        output.write(content.toByteArray())
//        output.flush()
//        output.close()
//    }
//
//    /**
//     * 内部存储读取数据
//     */
//    fun getData() {
//        var content = ""
//        val fileName = "内部存储测试.txt"
//        val input = openFileInput(fileName)
//        val buffer = ByteArray(1024)
//        var len = 0
//
//        while ((input.read(buffer).also { len = it }) != -1) {
//            val str = String(buffer, 0, len)
//            content += str
//        }
//        input.close()
//        Log.e("TAG", "content: $content")
//    }
//
//    /**
//     * 内部缓存存储保存数据
//     */
//    fun saveData2() {
//        val content = "hello 内部缓存存储"
//        val fileName = "内部缓存存储测试.txt"
//        val file = File(cacheDir, fileName)
//        val output = FileOutputStream(file)
//        output.write(content.toByteArray())
//        output.flush()
//        output.close()
//    }
//
//    /**
//     * 内部缓存存储读取数据
//     */
//    fun getData2() {
//        var content = ""
//        val fileName = "内部缓存存储测试.txt"
//        val file = File(cacheDir, fileName)
//        val input = FileInputStream(file)
//        val buffer = ByteArray(1024)
//        var len = 0
//        while ((input.read(buffer).also { len = it }) != -1) {
//            val str = String(buffer, 0, len)
//            content += str
//        }
//        input.close()
//        Log.e("TAG", "content: $content")
//    }
//
//    /**
//     * 外部存储一些信息
//     */
//    fun getOuterInfo() {
//        val ret = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
//        Log.e("TAG", "SD卡是否挂起：$ret")
//    }
//
//    /**
//     * 外部私有存储保存数据
//     */
//    fun saveOuterData() {
//        val content = "hello 外部存储"
//        val fileName = "外部存储测试.txt"
//
//        val file = File(getExternalFilesDir("测试"), fileName)
//        val output = FileOutputStream(file)
//        output.write(content.toByteArray())
//        output.flush()
//        output.close()
//    }
//
//    /**
//     * 外部私有存储读取数据
//     */
//    fun getOuterData() {
//        var content = ""
//        val fileName = "外部存储测试.txt"
//        val file = File(getExternalFilesDir("测试"), fileName)
//        val input = FileInputStream(file)
//        val buffer = ByteArray(1024)
//        var len = 0
//        while ((input.read(buffer).also { len = it }) != -1) {
//            val str = String(buffer, 0, len)
//            content += str
//        }
//        input.close()
//        Log.e("TAG", "content:$content")
//    }
//
//    /**
//     * 外部私有缓存目录保存数据
//     */
//    fun saveOuterData2() {
//        val content = "hello 外部缓存存储"
//        val fileName = "外部缓存存储测试.txt"
//
//        val file = File(externalCacheDir, fileName)
//        val output = FileOutputStream(file)
//        output.write(content.toByteArray())
//        output.flush()
//        output.close()
//    }
//
//    /**
//     * 外部私有缓存目录读取数据
//     */
//    fun getOuterData2() {
//        var content = ""
//        val fileName = "外部缓存存储测试.txt"
//        val file = File(externalCacheDir, fileName)
//        val input = FileInputStream(file)
//        val buffer = ByteArray(1024)
//        var len = 0
//        while ((input.read(buffer).also { len = it }) != -1) {
//            val str = String(buffer, 0, len)
//            content += str
//        }
//        input.close()
//        Log.e("TAG", "content:$content")
//    }
//
//    /**
//     * Android10以下，外部非私有存储
//     */
//    fun savePublicOuterData() {
//        if (!isScopedStorage()) {
//            val content = "hello 外部非私有存储测试"
//            val fileName = "外部非私有存储测试.txt"
//
//            val file = File(Environment.getExternalStorageDirectory(), fileName)
//            val output = FileOutputStream(file)
//            output.write(content.toByteArray())
//            output.flush()
//            output.close()
//        }
//    }

    /**
     * 分区存储一些信息
     */
//    fun externalStorageManagerInfo() {
////        val ret = Environment.isExternalStorageManager() //true 表示传统模式，false 表示分区存储
////        Log.e("TAG", "是否开启分区存储：$ret")
//    }
//
//    fun mediaStoreSave() {
////        saveDocument()
////        saveImage()
//    }

    /**
     * 保存文档
     */
//    fun saveDocument() {
//        if (isScopedStorage()) {
//            val content = "Hello 分区存储，保存一些文本信息"
//            val dirName = "测试"
//            val fileName = "Hello.txt"
//            val path = Environment.DIRECTORY_DOWNLOADS + File.separator + dirName
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Downloads.RELATIVE_PATH, path)
//                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
//                put(MediaStore.Downloads.TITLE, fileName)
//            }
//            val resultUri: Uri? =
//                contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//            resultUri?.let {
//                val output = contentResolver.openOutputStream(it)
//                val bos = BufferedOutputStream(output)
//                bos.write(content.toByteArray())
//                bos.flush()
//                bos.close()
//                output?.close()
//            }
//        }
//    }

    /**
     * 保存图片
     */
//    fun saveImage() {
//        if (isScopedStorage()) {
//            val imageName = "hello.jpg"
//            val dirName = "图片测试"
//            val path = Environment.DIRECTORY_PICTURES + File.separator + dirName
//            val contentValue = ContentValues().apply {
//                put(MediaStore.Images.ImageColumns.RELATIVE_PATH, path)
//                put(MediaStore.Images.ImageColumns.DISPLAY_NAME, imageName)
//                put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpg")
//                put(MediaStore.Images.ImageColumns.TITLE, imageName)
//            }
//            val uri =
//                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValue)
//            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
//            uri?.let {
//                val output = contentResolver.openOutputStream(it)
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
//                output?.close()
//            }
//        }
//    }

    var queryUri: Uri? = null

    /**
     *查询数据
     */
//    fun mediastoreQuery() {
//        if (isScopedStorage()) {
//            val imageName = "hello.jpg"
//            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            val selection = MediaStore.Images.Media.DISPLAY_NAME + "=?"
//            var args = arrayOf(imageName)
//            val cursor = contentResolver.query(uri, null, selection, args, null)
//            cursor?.let {
//                if (it.moveToFirst()) {
//                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
//                    queryUri = ContentUris.withAppendedId(uri, id)
//                    binding.imageView.setImageURI(queryUri)
//                    Log.e("TAG", "查询成功: $queryUri")
//                }
//                it.close()
//            }
//        }
//    }

    /**
     * 删除数据
     */
//    fun mediastoreDelete() {
//        if (isScopedStorage()) {
//            queryUri?.let {
//                val row: Int = contentResolver.delete(it, null, null)
//                Log.e("TAG", "删除成功: $row")
//            }
//        }
//    }

//    fun mediastoreUpdate() {
//        if (isScopedStorage()) {
//            queryUri?.let {
//                val contentValues = ContentValues().apply {
//                    put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "hello_01.jpg")
//                }
//                val update: Int = contentResolver.update(it, contentValues, null, null)
//                Log.e("TAG", "修改成功：$update")
//            }
//        }
//    }

//    /**
//     * 跳转系统文件浏览器
//     */
//    fun saf() {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = "image/*"
//        startActivityForResult(intent, 222)
//    }

    fun requestAllFilesAccessPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || Environment.isExternalStorageManager()) {
            Toast.makeText(
                this,
                "We can access all files on external storage now",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val builder = AlertDialog.Builder(this)
                .setTitle("Tip")
                .setMessage("We need permission to access all files on external storage")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    startActivityForResult(intent, 333)
                }
                .setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    // 写入数据
    fun test() {
        val content = "hello test123"
        val fileName = "test123.txt"

        val file = File(Environment.getExternalStorageDirectory(), fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    // 读取数据
    fun test2() {
        val fileName = "test123.txt"
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
            Log.e("TAG", "content:$content")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
//            222 -> {
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    val uri = data.data
//                    uri?.let {
//                        binding.safImageView.setImageBitmap(
//                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
//                        )
//                    }
//                }
//            }
            333 -> {
                requestAllFilesAccessPermission()
            }
        }
    }
}

fun isScopedStorage(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()


