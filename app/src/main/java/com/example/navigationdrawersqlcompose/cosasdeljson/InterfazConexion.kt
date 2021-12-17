package com.example.navigationdrawersqlcompose.cosasdeljson

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val url = "http://iesayala.ddns.net/loren/"

interface InterfazConexion {
    @GET("select_videojuego.php/")
    fun info(): Call<ListaVideojuegos>

}
object InstanciaVideojuego{
    val pInterfa: InterfazConexion

    init {
            val retro = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            pInterfa = retro.create(InterfazConexion::class.java)
    }



}