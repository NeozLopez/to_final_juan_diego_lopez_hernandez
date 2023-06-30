package com.example.doggallery.models

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.doggallery.api.models.ImageResponse
import com.example.doggallery.db.ImagenPerro
import com.example.doggallery.db.ImagenPerroRepository
import com.example.doggallery.db.ImagenPerroRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.RuntimeException

data class ImageFavState(
    var imagenes:List<ImagenPerro> = mutableListOf()
)

/*Clase fabrica se usa para que el constructor del viewmodel pueda recibir argumentos*/
class Factory(private val contexto: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageFavViewModel(contexto) as T
    }
}

class ImageFavViewModel(contexto: Context):ViewModel() {
    var state by mutableStateOf(ImageFavState())
    private var scope: CoroutineScope = viewModelScope
    private var repository: ImagenPerroRepository? = null

    init {
        initRepository(contexto)
    }

    private fun initRepository(contexto: Context) {
        val database = ImagenPerroRoomDatabase.getDatabase(contexto)
        repository = ImagenPerroRepository(database.imagenPerroDAO())
    }

    /*metodo que cambia el estado de lista*/
    fun changeStateListPersonas(imagenesFavs: List<ImagenPerro>) {
        state = state.copy(
            imagenes = imagenesFavs
        )
    }

    /*Metodos de base de datos*/
    fun getImagenesFavs() {
        scope.launch(Dispatchers.IO) {
            repository?.getAll()?.collect { t -> changeStateListPersonas(t) }
        }
    }

    fun insertPersonas(urlImagenFav:String) {
        scope.launch(Dispatchers.IO) {
            repository?.insert(ImagenPerro(0,urlImagenFav))
        }
    }

    fun clearListPersonas() {
        state = state.copy(
            imagenes = mutableListOf()
        )
    }

}


