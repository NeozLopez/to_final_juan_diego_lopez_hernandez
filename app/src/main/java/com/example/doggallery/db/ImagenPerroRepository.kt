package com.example.doggallery.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ImagenPerroRepository(private val personaDAO: ImagenPerroDAO){

    @WorkerThread
    fun getAll(): Flow<List<ImagenPerro>> {
        return personaDAO.getAll()
    }

    @WorkerThread
    suspend fun insert(persona: ImagenPerro){
        personaDAO.insert(persona)
    }
}