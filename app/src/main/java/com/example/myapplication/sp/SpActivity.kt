package com.example.myapplication.sp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySpBinding

class SpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {

            //方式一
//            val sp = getSharedPreferences("app", Context.MODE_PRIVATE)
//            val edit = sp.edit()
//            edit.putString("name", "Tom")
//            edit.putInt("age", 18)
//            edit.putBoolean("sex", true)
//            edit.apply()

            //方式二
            getSharedPreferences("app", Context.MODE_PRIVATE).open {
                putString("name", "Jake")
                putInt("age", 28)
                putBoolean("sex", false)
            }

        }

        binding.btnGet.setOnClickListener {
            val sp = getSharedPreferences("app", Context.MODE_PRIVATE)
            val name = sp.getString("name", "")
            val age = sp.getInt("age", 0)
            val sex = sp.getBoolean("sex", false)
            binding.tvContent.text = "姓名：$name，年龄：$age，性别：$sex"
        }
    }
}