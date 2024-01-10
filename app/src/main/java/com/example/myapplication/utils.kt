package com.example.myapplication

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.view.WindowManager
import android.widget.Toast
import java.io.File


/**
 * Toast
 */
fun toast(message: String) {
    Toast.makeText(BaseApplication.context, message, Toast.LENGTH_SHORT).show()
}


/**
 * 判断SD卡是否挂起
 */
fun isMounted(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

/**
 * 判断是否已授权SD卡所有权
 */
fun isExternalStorageManager(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()

/**
 * 获取屏幕宽度
 */
fun getScreenWidth(): Int {
    val wm = BaseApplication.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}

fun createTmpFile(context: Context): File {
    //使用媒体目录存储媒体文件
    val dir = context.getExternalFilesDir(DIRECTORY_PICTURES)
    //创建临时文件并返回
    return File.createTempFile("zhaopian", "jpg", dir)
}
