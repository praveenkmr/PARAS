<?php
define('DB_NAME','login');
define('DB_USER','root');
define('DB_PASSWORD','');
define('DB_HOST','localhost'); 

$con=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME) or die('Unable To COnnect The Database');
?>