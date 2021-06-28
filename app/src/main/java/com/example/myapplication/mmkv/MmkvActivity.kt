package com.example.myapplication.mmkv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMmkvBinding
import com.tencent.mmkv.MMKV

class MmkvActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMmkvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            //指定实例
            val myMMKV = MMKV.mmkvWithID("myMMKV")

            //全局实例
            val mmkv = MMKV.defaultMMKV()
            mmkv.encode("name", "Tom")
            mmkv.encode("age", 38)
            mmkv.encode("sex", false)
            val hobby = setOf("篮球", "足球")
            mmkv.encode("hobby", hobby)
        }

        binding.btnGet.setOnClickListener {
            val mmkv = MMKV.defaultMMKV()
            val name = mmkv.decodeString("name")
            val age = mmkv.decodeInt("age")
            val sex = mmkv.decodeBool("sex")
            val hobby = mmkv.decodeStringSet("hobby")
            binding.tvContent.text = "姓名：$name，年龄：$age，性别：$sex，爱好：$hobby"
        }
    }
}