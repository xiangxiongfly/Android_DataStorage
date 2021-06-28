package com.example.myapplication.sp

import android.content.SharedPreferences


fun SharedPreferences.open(
    isCommit: Boolean = false,
    block: SharedPreferences.Editor.() -> Unit
) {
    val edit = edit()
    edit.block()
    if (isCommit) {
        edit.commit()
    } else {
        edit.apply()
    }
}