package com.example.doggallery.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.doggallery.R
import com.example.doggallery.api.models.ImageResponse
import com.example.doggallery.models.ImageFavViewModel
import com.example.doggallery.models.ImageViewModel
import com.example.doggallery.ui.theme.PurpleGrey80
import com.example.doggallery.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(navigate: NavHostController, imagenesViewModel: ImageViewModel,imagenesFavsViewModel: ImageFavViewModel) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(231, 224, 236)),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
            ) {

                Column(modifier = Modifier.fillMaxWidth(0.7f))  {

                    TextField(
                        value = state.raza,
                        onValueChange ={ data -> imagenesViewModel.changeStateRaza(data.lowercase()) },
                        label = { Text("Buscar por raza") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        maxLines = 1
                    )
                }

                Column(modifier = Modifier.fillMaxWidth(1f))  {
                    Button(onClick = {
                        imagenesViewModel.cambiaImagenesRazaRandom()
                    },   shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(5.dp)) {
                        Text(text = "Buscar")
                    }
                }
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

                PhotoGrid(imagenesViewModel=imagenesViewModel,imagenesFavsViewModel)
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

                Button(onClick = { imagenesViewModel.cambiaImagenesRandom() }, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "Aleatorio")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = { navigate.navigate(Routes.ScreenFavs.route)}, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "Favoritos")
                }
            }
        }
    }
}

@Composable
fun PhotoGrid(imagenesViewModel: ImageViewModel,imagenesFavsViewModel: ImageFavViewModel) {
    val state = imagenesViewModel.state
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(state.imagenes) { photo ->
            TarjetaPerro(imageResponse = photo,imagenesFavsViewModel)
        }
    }
}

@Composable
fun TarjetaPerro(imageResponse: ImageResponse,imagenesFavsViewModel: ImageFavViewModel){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
        Box(modifier = Modifier
            .size(200.dp)
            .clip(RectangleShape)
            .background(Color.White)
            .padding(2.dp)) {
            Row(
                modifier= Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start ) {

                Box(){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageResponse.message)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RectangleShape)
                    )

                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(32.dp),
                        color = Color(0x44ffffff)
                    ) {
                        FavoriteButton(modifier = Modifier.padding(8.dp),imageResponse.message,imagenesFavsViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    id:String,
    imagenesFavsViewModel: ImageFavViewModel
) {

    //var isFavorite by remember { mutableStateOf(false) }
    val contexto= LocalContext.current

    IconToggleButton(
        checked = false,
        onCheckedChange = {
            //isFavorite = !isFavorite
            Toast.makeText(contexto,"Se agrego imagen.",Toast.LENGTH_LONG).show()
            imagenesFavsViewModel.insertPersonas(id)
        }
    ) {
        Icon(
            tint = Color(0xffE91E63),
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = Icons.Filled.FavoriteBorder,
            contentDescription = null
        )
    }
}