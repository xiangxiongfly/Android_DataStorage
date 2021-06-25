package com.example.myapplication.litepal

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport
import java.util.*

data class Course(
    val name: String,
    val teacher: String,
    val price: Double,
    val date: Date,
    @Column(defaultValue = "很好") val comment: String,
) : LitePalSupport() {
    val id: Long = 0

    override fun toString(): String {
        return "Course(id=$id, name=$name, teacher=$teacher, price=$price, date=$date, comment=$comment)"
    }
}

data class Teacher(
    val name: String,
    val age: Int,
    val desc: String
) : LitePalSupport() {
    val id: Long = 0
}

data class Comment(val content: String) : LitePalSupport() {
    val id: Long = 0
}