<?php

$server = "localhost";
$user = "root";
$pass = "clave";
$bd = "BDLoren";

//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Ha sucedido un error inexperado en la conexion de la base de datos");

$sql = "SELECT * FROM `Videojuegos`" ;
mysqli_set_charset($conexion, "utf8");
if(!$result = mysqli_query($conexion, $sql)) die();

$videojuegos = array();

while($row = mysqli_fetch_array($result)) {
            $name = $row['name'];
            $platform = $row['platform'];
            $price = $row['price'];
            $img = $row['img'];

        $videojuegos[] = array('name'=>$name,'platform '=>$platform, 'price'=>$price, 'img' =>$img);

}
$close = mysqli_close($conexion)or die ("pue no ha podio ser");

$json_string = json_encode($videojuegos);

echo $json_string;