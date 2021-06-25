package com.example.myapplication.room

import androidx.room.*


data class Address(var addr: String?, var detailAddress: String?)

@Entity
data class Consumer(
    var name: String,
    var age: Int,
    @Embedded
    var address: Address? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}


@Entity
data class Library(
    var libraryName: String,
    var consumerId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var libraryId: Long = 0L
}

//一对一关联类
data class ConsumerAndLibrary(
    @Embedded
    var consumer: Consumer,
    @Relation(parentColumn = "id", entityColumn = "consumerId")
    var library: Library
)

//一对多关联
data class ConsumerAndLibrarys(
    @Embedded
    var consumer: Consumer,
    @Relation(parentColumn = "id", entityColumn = "consumerId")
    var library: List<Library>
)


@Dao
interface ConsumerDao {
    @Insert
    fun addData(consumer: Consumer): Long

    @Insert
    fun addAllData(consumers: List<Consumer>)
}


@Dao
interface LibraryDao {
    @Insert
    fun addData(library: Library): Long

    @Insert
    fun addAllData(library: List<Library>)

    //一对一查询
    @Transaction
    @Query("SELECT * FROM Consumer")
    fun getConsumerAndLibraryList(): List<ConsumerAndLibrary>

    //一对多查询
    @Transaction
    @Query("SELECT * FROM Consumer")
    fun getConsumerAndLibrarysList(): List<ConsumerAndLibrarys>

}
