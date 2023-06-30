package com.example.doggallery.models

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doggallery.api.models.ImageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ImageState(
    var imagenes:List<ImageResponse> = mutableListOf(),
    var raza:String = ""
)

class ImageViewModel: ViewModel() {

    var state by mutableStateOf(ImageState())
    private val repository:ApiDogRepository = ApiDogRepository()
    private val coroutineScope: CoroutineScope = viewModelScope


    fun cambiaImagenesRandom(){
        coroutineScope.launch (Dispatchers.IO){
            val imagenes = mutableListOf<ImageResponse>()
            for (i in 1..10){
                imagenes.add(repository.getImageRandom())
            }

            state = state.copy(
                imagenes = imagenes
            )
        }
    }

    fun changeStateRaza(nuevaRaza: String) {
        state = state.copy(
            raza = if (nuevaRaza.isNotEmpty() ) nuevaRaza else ""
        )
    }

    fun cambiaImagenesRazaRandom(){
        coroutineScope.launch (Dispatchers.IO){
            try{
                val imagenes = mutableListOf<ImageResponse>()
                for (i in 1..10){
                    imagenes.add(repository.getImageBreedRandom(state.raza.trim()))
                }
                state = state.copy(
                    imagenes = imagenes
                )
            }catch (ex:Exception){
                state = state.copy(
                    imagenes =  mutableListOf()
                )
            }
        }
    }
}