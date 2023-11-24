package com.example.myapplication

import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV
import org.litepal.LitePal


class BaseApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        //初始化LitePal
        LitePal.initialize(this)

        //初始化MMKV
        val rootDir = MMKV.initialize(this)
        Log.e("TAG", "rootDir:$rootDir")
    }
}