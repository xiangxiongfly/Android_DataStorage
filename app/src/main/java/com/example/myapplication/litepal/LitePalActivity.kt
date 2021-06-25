package com.example.myapplication.litepal

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLitePalBinding
import org.litepal.LitePal
import org.litepal.tablemanager.Connector
import java.util.*


class LitePalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLitePalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLitePalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db: SQLiteDatabase = Connector.getDatabase()

        binding.btnAdd.setOnClickListener {
//            addData()
            addData2()
        }

        binding.btnDelete.setOnClickListener {
//            deleteData()
            deleteData2()
//            deleteData3()
        }

        binding.btnModify.setOnClickListener {
//            modifyData()
            modifyData2()
        }

        binding.btnQuery.setOnClickListener {
            queryData()
//            queryData2()
        }

        binding.btnTransaction.setOnClickListener {
            transaction()
        }
    }

    //添加一条数据
    private fun addData() {
//        val course = Course("Java", "小红", 12.04, Date(), "D")
//        course.save()
    }

    //添加多条数据
    private fun addData2() {
        val courseList = arrayListOf<Course>()
        courseList.add(Course("JavaScript", "小花", 23.06, Date(), "A"))
        courseList.add(Course("HTML", "小白", 13.06, Date(), "B"))
        courseList.add(Course("CSS", "小黑", 33.06, Date(), "C"))
        LitePal.saveAll(courseList)
    }

    //删除指定id
    private fun deleteData() {
        LitePal.delete(Course::class.java, 1)
    }

    //删除条件数据
    private fun deleteData2() {
        LitePal.deleteAll(Course::class.java, "teacher = ? and id > 1", "小花")
    }

    //删除所有数据
    private fun deleteData3() {
        LitePal.deleteAll(Course::class.java)
    }

    private fun modifyData() {
        val values = ContentValues().apply {
            put("teacher", "小白")
        }
        LitePal.update(Course::class.java, values, 1)
    }

    private fun modifyData2() {
        val values = ContentValues().apply {
            put("teacher", "小👋")
        }
        LitePal.updateAll(Course::class.java, values, "name = ? and id > 5", "CSS")
    }

    //简单查询
    private fun queryData() {
        //查询id为1的数据
//        val course = LitePal.find(Course::class.java, 1)
//        binding.tvContent.text = course.toString()

        //查询表内第一条数据
//        val course2 = LitePal.findFirst(Course::class.java)
//        binding.tvContent.text = course2.toString()

        //查询表内所有数据
        val courseList = LitePal.findAll(Course::class.java)
        binding.tvContent.text = courseList.toString()
    }

    //条件查询
    private fun queryData2() {
        val courseList = LitePal.where("id > ?", "5")
            .order("price desc")
            .find(Course::class.java)
        binding.tvContent.text = courseList.toString()
    }

    //事务
    private fun transaction() {
        try {
            LitePal.beginTransaction()

            val course = LitePal.find(Course::class.java, 2)
            val values = ContentValues().apply {
                put("price", 100.01)
            }
            LitePal.update(Course::class.java, values, course.id)

            LitePal.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            LitePal.endTransaction()
        }
    }

    private fun toast(msg: Any) {
        Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
    }
}