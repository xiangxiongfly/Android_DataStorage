package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.litepal.LitePalActivity
import com.example.myapplication.room.RoomActivity
import com.example.myapplication.sqlite.SQLiteActivity

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
}
