package com.example.doggallery.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagenPerroDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(imagen: ImagenPerro)

    @Query("SELECT * FROM imagenesperros")
    fun getAll(): Flow<List<ImagenPerro>>
}