package com.example.navigationdrawercompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.navigationdrawersqlcompose.R
import com.example.navigationdrawersqlcompose.cosasdeljson.InstanciaVideojuego
import com.example.navigationdrawersqlcompose.cosasdeljson.ListaVideojuegos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
            }
        }
    }


@Composable
fun MainScreen() {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ) {
        Navigation(navController = navController)
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {

    TopAppBar(
        title = { Text(text = "Navigation Drawer", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = Color.Green,
        contentColor = Color.Black
    )
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {

    val items = listOf(
        NavegacionItem.Home,
        NavegacionItem.Profile,
        NavegacionItem.Settings,

    )

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {

                navController.navigate(items.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Loren",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DrawerItem(item: NavegacionItem, selected: Boolean, onItemClick: (NavegacionItem) -> Unit) {
    val background = if (selected) Color.Red else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}


@Composable
fun anidevideojuegosç() {

    var txtNombre by rememberSaveable { mutableStateOf("") }
    var txtplatform by rememberSaveable { mutableStateOf("") }
    var txtprice by rememberSaveable { mutableStateOf("") }
    var txtImagen by rememberSaveable { mutableStateOf("") }
    val contexto = LocalContext.current

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.onSurface,
                        MaterialTheme.colors.secondaryVariant,
                    )
                )
            ),

        ) {
        TextField(value = txtNombre, onValueChange = { nuevo -> txtNombre = nuevo }, label = {
            Text(text = "Nombre")
        },
            modifier = Modifier
                .padding(10.dp, 30.dp, 10.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Left)

        )
        TextField(value = txtplatform, onValueChange = { nuevo -> txtplatform = nuevo }, label = {
            Text(text = "Plataforma")
        },
            modifier = Modifier
                .padding(10.dp, 30.dp, 10.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Left)

        )
        TextField(value = txtprice, onValueChange = { nuevo -> txtprice = nuevo }, label = {
            Text(text = "Precio")
        },
            modifier = Modifier
                .padding(10.dp, 30.dp, 10.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Left)

        )

        TextField(value = txtImagen, onValueChange = { nuevo -> txtImagen = nuevo }, label = {
            Text(text = "Url a la imagen")
        },
            modifier = Modifier
                .padding(10.dp, 30.dp, 10.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Left)

        )
        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.width(65.dp))

            Button(
                modifier = Modifier
                    .size(width = 100.dp, height = 50.dp),
                onClick = {
                    insert(txtNombre, txtplatform, txtprice, txtImagen)
                    txtNombre = ""
                    txtplatform = ""
                    txtprice = ""
                    txtImagen = ""
                    Toast.makeText(contexto, "Registro añadido", Toast.LENGTH_SHORT).show()

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                )
            ) {
                Text(
                    text = "INSERT",
                    color = Color.White
                )
            }

        }
    }


}

@Composable
fun borrar() {
    var txtTitulo by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.onSurface,
                        MaterialTheme.colors.secondaryVariant,
                    )
                )
            ),
    ) {


        TextField(
            value = txtTitulo,
            onValueChange = { nuevo ->
                txtTitulo = nuevo
            },
            label = {
                Text(text = "Introduce el titulo")
            },
            modifier = Modifier
                .padding(10.dp, 30.dp, 10.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Left)
        )

        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.width(65.dp))

            Button(
                modifier = Modifier
                    .background(Color.Black, RoundedCornerShape(100.dp))
                    .size(width = 100.dp, height = 50.dp),
                onClick = {
                    borrar(txtTitulo)
                    txtTitulo = ""
                    Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                )
            ) {
                Text(
                    text = "DELETE",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController, startDestination = NavegacionItem.Home.route) {

        composable(NavegacionItem.Home.route) {
            HomeScreen()
        }

        composable(NavegacionItem.Profile.route) {
            anidevideojuegosç()
        }

        composable(NavegacionItem.Settings.route) {
            borrar()
        }


    }}

    @Composable
    fun CargaImagen(url: String) {
        Image(
            painter = rememberImagePainter(url),
            contentDescription = "img",
            contentScale = ContentScale.FillWidth,

            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(1.dp)
        )
    }

@Composable
    fun HomeScreen(){

        var carte = sacaelJSON()


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.onSurface,
                            MaterialTheme.colors.secondaryVariant,
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            LazyColumn(
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.Start


            )
            {

                items(carte) { video ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .border(3.dp, Color.Black)
                                .fillMaxHeight()
                                .fillMaxWidth(),
                        )

                        {

                            CargaImagen(url = video.img)

                        }

                    }
                    Column {

                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)){


                            Text(
                                text = video.name,
                                color = Color.White,
                                modifier = Modifier

                                    .fillMaxWidth()

                                    .padding(10.dp, 0.dp, 0.dp, 0.dp)                                        .height(40.dp)
                                    .size(30.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold

                                ))

                        }


                    }
                }
            }
        }
}












    fun lee(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                URL(url)
                    .openStream()
                    .bufferedReader()
                    .use { it.readText() }
            } catch (e: IOException) {
                "Error with ${e.message}."
                Log.d("io", e.message.toString())
            } catch (e: Exception) {
                "Error with ${e.message}."
                Log.d("io", e.message.toString())
            }
        }

        return
    }

    fun insert(name: String, platform: String, price: String, img: String) {
        val url =
            "http://iesayala.ddns.net/loren/insertvideogame.php/?name=$name&platform=$platform&price=$price&descripcion=$img&imagen"
        lee(url)

    }


    fun borrar(name: String) {
        val url = "http://iesayala.ddns.net/loren/delete_videojuego.php/?name=$name"
        lee(url)

    }


    @Composable
    fun sacaelJSON(): ListaVideojuegos {
        val contexto = LocalContext.current

        var vidoejuegosss by rememberSaveable{
            mutableStateOf(ListaVideojuegos())}
        val videojo = InstanciaVideojuego.pInterfa.info();

        videojo.enqueue(object : Callback<ListaVideojuegos> {
            override fun onResponse(
                call: Call<ListaVideojuegos>,
                response: Response<ListaVideojuegos>
            ) {
                val videoInfo: ListaVideojuegos? = response.body()
                if (videoInfo!= null) {

                    vidoejuegosss = videoInfo

                }

            }

            override fun onFailure(call: Call<ListaVideojuegos>, t: Throwable) {

                Toast.makeText(contexto, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        return vidoejuegosss


    }

}

