package com.example.myapplication

import android.app.Application
import android.util.Log
import com.tencent.mmkv.MMKV
import org.litepal.LitePal


class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化LitePal
        LitePal.initialize(this)

        //初始化MMKV
        val rootDir = MMKV.initialize(this)
        Log.e("TAG", "rootDir:$rootDir")
    }
}