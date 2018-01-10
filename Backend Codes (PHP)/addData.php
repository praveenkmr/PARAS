<?php

require("dbConnect.php");

$type=$_POST['Type'];
echo "Type=".$type;
if($type==2){
    $val=$_POST['EMAIL'];
}
echo "provided value is ". $val;

?>