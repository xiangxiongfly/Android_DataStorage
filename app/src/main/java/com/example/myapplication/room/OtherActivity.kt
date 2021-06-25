package com.example.myapplication.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityOtherBinding
import kotlin.concurrent.thread

class OtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConsumerLibrary.setOnClickListener {
            thread {
                MyRoomDatabase.getDatabase(this).consumerDao().addAllData(
                    listOf(
                        Consumer("小明", 18),
                        Consumer("小黑", 28),
                        Consumer("小白", 38),
                    )
                )

                MyRoomDatabase.getDatabase(this).libraryDao().addAllData(
                    listOf(
                        Library("歌曲1", 1),
                        Library("歌曲2", 2),
                        Library("歌曲3", 3),
                        Library("歌曲4", 1),
                        Library("歌曲5", 2),
                        Library("歌曲6", 3)
                    )
                )
            }
        }

        binding.btnQuery.setOnClickListener {
            thread {
                //一对一查询
                val consumerAndLibraryList =
                    MyRoomDatabase.getDatabase(this).libraryDao().getConsumerAndLibraryList()
                for (i in consumerAndLibraryList) {
                    log(i)
                }

                log("=========")

                //一对多查询
                val consumerAndLibrarysList =
                    MyRoomDatabase.getDatabase(this).libraryDao().getConsumerAndLibrarysList()
                for (i in consumerAndLibrarysList) {
                    log(i)
                }
            }
        }
    }

    private fun log(msg: Any) {
        Log.e("TAG", "$msg\n")
    }
}
