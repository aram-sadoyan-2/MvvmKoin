package com.camp.mvvmkoin.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.camp.mvvmkoin.model.Hits

@Database(
    entities = [Hits::class],
    version = 1, exportSchema = false
)
abstract class ImageDatabase : RoomDatabase() {

    abstract val appDao: AppDAO


}