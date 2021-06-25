package com.example.myapplication.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MySQLiteOpenHelper(
    context: Context?
) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    companion object {
        const val DB_NAME = "mysqlite.db"
        const val VERSION = 3
    }

    private val CREATE_COURSE = """
        create table if not exists $TABLE_COURSE(
        id integer primary key autoincrement,
        name text,
        teacher text,
        price real
        )
    """.trimIndent()

    private val CREATE_TEACHER = """
        create table if not exists $TABLE_TEACHER(
        id integer primary key autoincrement,
        name text,
        age integer,
        desc text
        )
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_COURSE)
        db?.execSQL(CREATE_TEACHER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion <= 1) {
            updateVersion2(db)
        }
        if (oldVersion <= 2) {
            updateVersion3(db)
        }
    }

    private fun updateVersion2(db: SQLiteDatabase) {
        val sql = "alter table course add column teacher_id integer"
        db.execSQL(sql)
    }

    private fun updateVersion3(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put("price", "0.00")
        }
        db.update(TABLE_COURSE, values, null, null)
    }
}