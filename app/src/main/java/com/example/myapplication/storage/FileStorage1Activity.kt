package com.example.myapplication.storage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityFileStorage1Binding
import com.example.myapplication.isMounted
import com.example.myapplication.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

const val TAG = "TAG"
const val REQUEST_PERMISSIONS_CODE = 123

/**
 * Android9及以下版本
 */
class FileStorage1Activity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityFileStorage1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFileStorage1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnGetInfo.setOnClickListener {
            Log.e(TAG, "getFileDir(): ${filesDir}") // /data/user/0/com.example.myapplication/files

            Log.e(TAG, "getCacheDir(): ${cacheDir}") // /data/user/0/com.example.myapplication/cache

            Log.e(
                TAG,
                "getExternalFilesDir(): ${getExternalFilesDir(null)}"
            ) // /storage/emulated/0/Android/data/com.example.myapplication/files

            Log.e(
                TAG, "getExternalFilesDir(): ${getExternalFilesDir(Environment.DIRECTORY_MOVIES)}"
            ) // /storage/emulated/0/Android/data/com.example.myapplication/files/Movies

            Log.e(
                TAG,
                "getExternalCacheDir(): ${externalCacheDir}"
            ) // /storage/emulated/0/Android/data/com.example.myapplication/cache

            Log.e(
                TAG,
                "Environment.getExternalStorageDirectory(): ${Environment.getExternalStorageDirectory()}"
            ) // /storage/emulated/0

            Log.e(
                TAG,
                "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES): ${
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                }"
            ) // /storage/emulated/0/Pictures

            fileList().forEach {
                Log.e(TAG, "内部存储文件: ${it}")
            }
        }
    }

    fun innerSaveData(view: View) {
        val content = "hello内部存储测试数据"
        val fileName = "内部存储.txt"
        val output = openFileOutput(fileName, MODE_PRIVATE)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    fun innerGetData(view: View) {
        val fileName = "内部存储.txt"
        var content = ""
        var len = 0
        val buffer = ByteArray(1024)
        val input = openFileInput(fileName)
        while (input.read(buffer).also { len = it } != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e(TAG, "content: ${content}")
    }

    fun innerCacheSaveData(view: View) {
        val content = "hello内部缓存存储测试数据"
        val fileName = "内部缓存存储.txt"
        val file = File(cacheDir, fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    fun innerCacheGetData(view: View) {
        val fileName = "内部缓存存储.txt"
        val file = File(cacheDir, fileName)
        var content = ""
        var len = 0
        val buffer = ByteArray(1024)
        val input = FileInputStream(file)
        while (input.read(buffer).also { len = it } != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e(TAG, "content: ${content}")
    }

    fun checkMounted(view: View) {
        toast("SD卡是否挂起：${isMounted()}")
    }

    fun outerSaveData(view: View) {
        val content = "hello外部存储测试数据"
        val fileName = "外部存储.txt"
        val file = File(getExternalFilesDir("外部存储测试"), fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    fun outerGetData(view: View) {
        val fileName = "外部存储.txt"
        val file = File(getExternalFilesDir("外部存储测试"), fileName)
        var content = ""
        var len = 0
        val buffer = ByteArray(1024)
        val input = FileInputStream(file)
        while (input.read(buffer).also { len = it } != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e(TAG, "content: ${content}")
    }

    fun outerCacheSaveData(view: View) {
        val content = "hello外部缓存存储测试数据"
        val fileName = "外部缓存存储.txt"
        val file = File(externalCacheDir, fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    fun outerCacheGetData(view: View) {
        val fileName = "外部缓存存储.txt"
        val file = File(externalCacheDir, fileName)
        var content = ""
        var len = 0
        val buffer = ByteArray(1024)
        val input = FileInputStream(file)
        while (input.read(buffer).also { len = it } != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e(TAG, "content: ${content}")
    }

    fun requestPermission(view: View) {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSIONS_CODE
        )
    }

    fun checkPermission(): Boolean = ActivityCompat.checkSelfPermission(
        this, Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED


    fun outerPublicSaveData(view: View) {
        if (!checkPermission()) {
            toast("请开启权限")
            return
        }
        val content = "hello外部公有存储测试数据"
        val fileName = "外部公有存储.txt"
        val file = File(Environment.getExternalStorageDirectory(), fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    fun outerPublicGetData(view: View) {
        if (!checkPermission()) {
            toast("请开启权限")
            return
        }
        val fileName = "外部公有存储.txt"
        val file = File(Environment.getExternalStorageDirectory(), fileName)
        var content = ""
        var len = 0
        val buffer = ByteArray(1024)
        val input = FileInputStream(file)
        while (input.read(buffer).also { len = it } != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e(TAG, "content: ${content}")
    }
}