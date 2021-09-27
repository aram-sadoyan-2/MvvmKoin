package com.camp.mvvmkoin.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camp.mvvmkoin.model.Hits
@Dao
interface AppDAO {
    @Query("SELECT * FROM todo_items")
    fun getAll(): List<Hits>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: List<Hits>)
}