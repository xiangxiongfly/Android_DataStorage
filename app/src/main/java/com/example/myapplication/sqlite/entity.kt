package com.example.myapplication.sqlite

const val TABLE_COURSE = "Course"
const val COURSE_ID = "id"
const val COURSE_NAME = "name"
const val COURSE_TEACHER = "teacher"
const val COURSE_PRICE = "price"

data class Course(
    val id: Int,
    var name: String,
    var teacher: String,
    var price: Double
)


const val TABLE_TEACHER = "Teacher"

data class Teacher(
    val id: Int,
    var name: String,
    var age: Int,
    var desc: String
)
