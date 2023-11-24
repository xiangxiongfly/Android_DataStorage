package com.example.myapplication

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Environment
import android.view.WindowManager
import android.widget.Toast

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
 * 判断是否开启分区尺寸
 */
fun isScopedStorage(): Boolean =
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