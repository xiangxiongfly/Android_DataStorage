package com.example.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

private const val DB_NAME = "myroom"
private const val DB_VERSION = 1

@Database(
    version = DB_VERSION,
    entities = [User::class, Book::class, Consumer::class, Library::class]
)
abstract class MyRoomDatabase : RoomDatabase() {

    companion object {
        private var instance: MyRoomDatabase? = null


        //版本1升级到版本2策略
        private val updateVersion_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val sql =
                    "create table Book(id integer primary key autoincrement not null, name text not null, pages integer not null)"
                database.execSQL(sql)
            }
        }

        //版本2升级到版本3
        private val updateVersion_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val sql = "alter table Book add column info text not null default 'unknown'"
                database.execSQL(sql)
            }
        }

        @Synchronized
        fun getDatabase(context: Context): MyRoomDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                MyRoomDatabase::class.java,
                DB_NAME
            )
                .addMigrations(updateVersion_1_2, updateVersion_2_3)
                .build().apply {
                    instance = this
                }
        }
    }

    abstract fun userDao(): UserDao


    abstract fun consumerDao(): ConsumerDao

    abstract fun libraryDao(): LibraryDao

}
