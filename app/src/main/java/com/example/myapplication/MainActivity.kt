package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.litepal.LitePalActivity
import com.example.myapplication.mmkv.MmkvActivity
import com.example.myapplication.room.RoomActivity
import com.example.myapplication.sp.SpActivity
import com.example.myapplication.sqlite.SQLiteActivity
import com.example.myapplication.storage.FileStorage1Activity
import com.example.myapplication.storage.FileStorage2Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickSQLite(v: View) {
        startActivity(Intent(this, SQLiteActivity::class.java))
    }

    fun clickLitePal(v: View) {
        startActivity(Intent(this, LitePalActivity::class.java))
    }

    fun clickRoom(v: View) {
        startActivity(Intent(this, RoomActivity::class.java))
    }

    fun clickSp(v: View) {
        startActivity(Intent(this, SpActivity::class.java))
    }

    fun clickMmkv(v: View) {
        startActivity(Intent(this, MmkvActivity::class.java))
    }

    fun clickStorage1(v: View) {
        startActivity(Intent(this, FileStorage1Activity::class.java))
    }

    fun clickStorage2(v: View) {
        startActivity(Intent(this, FileStorage2Activity::class.java))
    }
}
