package com.example.navigationdrawersqlcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationdrawersqlcompose.ui.theme.NavigationDrawerSQLComposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class SQLCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerSQLComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var textFieldValueName by rememberSaveable { mutableStateOf("") }
    var textFieldValuePlatform by rememberSaveable { mutableStateOf("") }
    var textFieldValuePrice by rememberSaveable { mutableStateOf("") }
    var textFieldValueImg by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Text(
            text = "SQL INSERT",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.Green,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        TextField(
            value = textFieldValueName,
            onValueChange = { nuevo ->
                textFieldValueName = nuevo
            },
            label = {
                Text(text = "Introducir nombre")
            },
            modifier = Modifier
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        TextField(
            value = textFieldValuePlatform,
            onValueChange = { nuevo ->
                textFieldValuePlatform = nuevo
            },
            label = {
                Text(text = "Introducir plataforma")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        TextField(
            value = textFieldValuePrice,
            onValueChange = { nuevo ->
                textFieldValuePrice = nuevo
            },
            label = {
                Text(text = "Introducir precio")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        TextField(
            value = textFieldValueImg,
            onValueChange = { nuevo ->
                textFieldValueImg = nuevo
            },
            label = {
                Text(text = "Introducir url imagen")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        Spacer(Modifier.height(20.dp) )

        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 100.dp, height = 50.dp)
            ,
            onClick = {
                insertar(textFieldValueName,textFieldValuePlatform,textFieldValuePrice, textFieldValueImg)
                textFieldValueName=""
                textFieldValuePlatform=""
                textFieldValuePrice=""
                textFieldValueImg=""
            }
        ){
            Text(text = "Insert"
            )
        }
    }
}


fun insertar(name:String, platform:String, price: String, img:String){
    val url = "http://iesayala.ddns.net/loren/insertguitar.php/?name=$name&platform=$platform&price=$price&img=$img"
    leerUrl(url)
}


fun leerUrl(urlString:String){
    GlobalScope.launch(Dispatchers.IO)   {
        val response = try {
            URL(urlString)
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