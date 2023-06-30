package com.example.doggallery.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.doggallery.api.models.ImageResponse
import com.example.doggallery.db.ImagenPerro
import com.example.doggallery.models.ImageFavViewModel
import com.example.doggallery.models.ImageViewModel
import com.example.doggallery.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenFavoritos(navigate: NavHostController, imagenesViewModel: ImageFavViewModel) {
    val state = imagenesViewModel.state
    Column(modifier = Modifier.fillMaxSize()) {

        // Fila 1 (Parte superior)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(231, 224, 236)),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Mis Favoritos",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }

        // Fila 2 (Ocupa el espacio restante)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(231, 224, 236)),
            contentAlignment = Alignment.Center
        ) {

            PhotoGridFavs(imagenesViewModel = imagenesViewModel)
        }

        // Fila 3 (Parte inferior)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(231, 224, 236)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {


                Button(onClick = { imagenesViewModel.clearListPersonas()}, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "Borrar todos")
                }
            }
        }
    }
}

@Composable
fun PhotoGridFavs(imagenesViewModel: ImageFavViewModel) {
    val state = imagenesViewModel.state
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(state.imagenes) { photo ->
            TarjetaPerroFav(imageResponse = photo)
        }
    }
}

@Composable
fun TarjetaPerroFav(imageResponse: ImagenPerro){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
        Box(modifier = Modifier
            .size(200.dp)
            .clip(RectangleShape)
            .background(Color.White)
            .padding(5.dp)) {
            Row(
                modifier= Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageResponse.urlImagen)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RectangleShape)
                )
            }
        }
    }
}