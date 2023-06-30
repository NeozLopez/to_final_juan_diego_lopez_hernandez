package com.example.doggallery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImagenPerro::class], version = 1)
abstract class ImagenPerroRoomDatabase: RoomDatabase(){

    /*funcion abstracta para crear el DAO*/
    abstract fun imagenPerroDAO():ImagenPerroDAO

    /*Carga una sola vez los elementos que esten adentro*/
    companion object{

        @Volatile
        private var INSTANCE:ImagenPerroRoomDatabase?=null

        /*Funcion que nos crea la conexion a base de datos*/
        fun getDatabase(context: Context):ImagenPerroRoomDatabase{

            return INSTANCE?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    ImagenPerroRoomDatabase::class.java,
                    "prueba.db"
                ).build()
                    .also {
                        INSTANCE=it
                    }
            }
        }
    }
}