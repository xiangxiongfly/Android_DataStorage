package com.example.myapplication.room

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Transaction
import com.example.myapplication.databinding.ActivityRoomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    private lateinit var userDao: UserDao

    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDao = MyRoomDatabase.getDatabase(this).userDao()

        binding.btnAdd.setOnClickListener {
            addData()
//            addData2()
        }

        binding.btnDelete.setOnClickListener {
            deleteData()
        }

        binding.btnUpdate.setOnClickListener {
//            updateData()
            updateData2()
        }

        binding.btnQuery.setOnClickListener {
            queryData()
//            queryData2()
        }

        binding.btnTransaction.setOnClickListener {
            transaction()
        }

        binding.btnOther.setOnClickListener {
            startActivity(Intent(this, OtherActivity::class.java))
        }
    }


    //增加一条数据
    private fun addData() {
        thread {
            val user = User("小", "明", 18)
            userDao.insertUser(user)
        }
    }

    //增加多条数据
    private fun addData2() {
        thread {
            val userList =
                arrayListOf<User>(
                    User("小", "白", 18),
                    User("小", "黑", 28),
                    User("大", "白", 38),
                    User("大", "黑", 48)
                )
            userDao.insertAllUser(userList)
        }
    }

    //删除数据
    private fun deleteData() {
        thread { userDao.deleteUserByAge(28) }
    }

    //修改条件数据
    private fun updateData() {
        thread { userDao.updateUserWithAge(48) }
    }

    //修改数据，修改年龄>=20
    private fun updateData2() {
        thread {
            val userList = userDao.queryUserByAge(20)
            for (user in userList) {
                user.age = 1000
                userDao.updateUser(user)
            }
        }
    }

    //查询所有数据
    private fun queryData() {
        coroutineScope.launch {
            val userList = userDao.queryAllUsers()
            withContext(Dispatchers.Main) {
                binding.tvContent.text = userList.toString()
            }
        }
    }

    //查询条件数据
    private fun queryData2() {
        coroutineScope.launch {
            val userList = userDao.queryUserByAge(20)
            withContext(Dispatchers.Main) {
                binding.tvContent.text = userList.toString()
            }
        }
    }

    //事务
    private fun transaction() {
//        transaction1()
        transaction2()
    }

    //使用注解
    @Transaction
    private fun transaction1() {
        thread {
            userDao.insertUser(User("李", "四", 28))
            userDao.insertUser(User("张", "三", 18))
            userDao.deleteUserByAge(28)
        }
    }

    //使用runInTransaction
    private fun transaction2() {
        thread {
            MyRoomDatabase.getDatabase(this).runInTransaction {
                userDao.insertUser(User("李", "四", 28))
                userDao.insertUser(User("张", "三", 18))
                userDao.deleteUserByAge(28)
            }
        }
    }
}
