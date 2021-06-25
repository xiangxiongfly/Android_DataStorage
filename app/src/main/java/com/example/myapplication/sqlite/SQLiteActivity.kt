package com.example.myapplication.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySqliteBinding

class SQLiteActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqliteBinding

    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDB()

        binding.btnAdd.setOnClickListener {
            addData()
        }
        binding.btnAdd2.setOnClickListener {
            addData2()
        }

        binding.btnDelete.setOnClickListener {
            deleteData()
        }

        binding.btnDelete2.setOnClickListener {
            deleteData2()
        }

        binding.btnModify.setOnClickListener {
            modifyData()
        }

        binding.btnModify2.setOnClickListener {
            modifyData2()
        }

        binding.btnQuery.setOnClickListener {
            queryData()
        }

        binding.btnQuery2.setOnClickListener {
            queryData2()
        }

        binding.btnTransaction.setOnClickListener {
            transaction()
        }
    }

    private fun initDB() {
        dbHelper = MySQLiteOpenHelper(this)
        db = dbHelper.writableDatabase
    }

    private fun addData(): Boolean {
        val values = ContentValues().apply {
            put(COURSE_NAME, "Java")
            put(COURSE_TEACHER, "小黑")
            put(COURSE_PRICE, 12.04)
        }
        val success = db.insert(TABLE_COURSE, null, values) != -1L
        return success
    }

    private fun addData2() {
        val sql =
            "insert into $TABLE_COURSE (${COURSE_NAME}, ${COURSE_TEACHER}, ${COURSE_PRICE}) values(?,?,?)"
        db.execSQL(sql, arrayOf("Android", "小白", "12.03"))
    }

    private fun deleteData(): Boolean {
        val success = db.delete(TABLE_COURSE, "teacher=?", arrayOf<String>("小黑")) != -1
        return success
    }

    private fun deleteData2() {
        val sql = "delete from $TABLE_COURSE where teacher=?"
        db.execSQL(sql, arrayOf("小白"))
    }

    private fun modifyData(): Boolean {
        val values = ContentValues().apply {
            put("teacher", "小红")
        }
        val success = db.update(TABLE_COURSE, values, "name=?", arrayOf("Java")) != -1
        return success
    }

    private fun modifyData2() {
        val sql = "update $TABLE_COURSE set teacher=? where name=?"
        db.execSQL(sql, arrayOf("小花", "Java"))
    }

    private fun queryData() {
        val builder = StringBuilder()
        val cursor = db.query("course", null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id: String = cursor.getString(cursor.getColumnIndex("id"))
                val name: String = cursor.getString(cursor.getColumnIndex("name"))
                val teacher: String = cursor.getString(cursor.getColumnIndex("teacher"))
                val price: String = cursor.getString(cursor.getColumnIndex("price"))
                builder.append("id:${id},name:$name,teacher:$teacher,price:$price \n")
            }
        }
        cursor.close()
        binding.tvContent.text = builder.toString()
    }

    private fun queryData2() {
        val builder = StringBuilder()
        val sql =
            "select $COURSE_ID,$COURSE_NAME,$COURSE_TEACHER,$COURSE_PRICE from $TABLE_COURSE where $COURSE_ID>10"
        val cursor = db.rawQuery(sql, null)
        with(cursor) {
            while (moveToNext()) {
                val id: String = cursor.getString(cursor.getColumnIndex("id"))
                val name: String = cursor.getString(cursor.getColumnIndex("name"))
                val teacher: String = cursor.getString(cursor.getColumnIndex("teacher"))
                val price: String = cursor.getString(cursor.getColumnIndex("price"))
                builder.append("id:${id},name:$name,teacher:$teacher,price:$price \n")
            }
        }
        cursor.close()
        binding.tvContent.text = builder.toString()
    }

    private fun transaction() {
        db.beginTransaction()
        try {
            db.execSQL(
                "insert into Course (name,teacher,price) values(?,?,?)",
                arrayOf("Math", "卧槽", "10.01")
            )
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}