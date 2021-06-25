package com.example.myapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(var name: String, var pages: Int, var info: String) {

    @PrimaryKey(autoGenerate = true)
    var id = 0L

}