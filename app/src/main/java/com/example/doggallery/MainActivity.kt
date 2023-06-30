package com.example.doggallery

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.doggallery.api.models.ImageResponse
import com.example.doggallery.models.Factory
import com.example.doggallery.models.ImageFavViewModel
import com.example.doggallery.models.ImageViewModel
import com.example.doggallery.screens.ScreenFavoritos
import com.example.doggallery.screens.ScreenMain
import com.example.doggallery.utils.Routes
import retrofit2.HttpException
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imagenesViewModel by viewModels<ImageViewModel>()
        val imagenesFavsViewModel by viewModels<ImageFavViewModel> {
            /*usamos el factory para crear un viewModel con argumentos*/
            Factory(this)
        }

        imagenesFavsViewModel.getImagenesFavs()

        setContent {
            imagenesViewModel.cambiaImagenesRandom()
            ScreenController(imagenesViewModel = imagenesViewModel, imagenesFavsViewModel =  imagenesFavsViewModel)
        }
    }
}

@Composable
fun ScreenController(imagenesViewModel:ImageViewModel,imagenesFavsViewModel:ImageFavViewModel){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination =  Routes.ScreenMain.route){
        composable(Routes.ScreenMain.route){ ScreenMain(navigate = navController,imagenesViewModel,imagenesFavsViewModel)}
        composable(Routes.ScreenFavs.route){ ScreenFavoritos(navigate = navController,imagenesFavsViewModel) }
    }
}
