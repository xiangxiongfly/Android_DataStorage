package com.example.myapplication.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertAllUser(user: List<User>)

    @Update
    fun updateUser(newUser: User)

    @Query("update User set age=100 where age= :age")
    fun updateUserWithAge(age: Int)

    @Query("select * from User")
    fun queryAllUsers(): List<User>

    @Query("select * from User where age >= :age")
    fun queryUserByAge(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where age = :age")
    fun deleteUserByAge(age: Int): Int

}