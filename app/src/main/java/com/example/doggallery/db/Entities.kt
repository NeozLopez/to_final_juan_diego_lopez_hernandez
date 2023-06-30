package com.example.doggallery.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="imagenesperros")
data class ImagenPerro(
    @PrimaryKey(autoGenerate=true) val id:Int=0,
    @ColumnInfo(name="urlimagen") val urlImagen:String="",
)