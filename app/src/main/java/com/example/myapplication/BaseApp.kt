package com.example.myapplication

import android.app.Application
import org.litepal.LitePal

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
    }
}