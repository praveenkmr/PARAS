<?php

if($_SERVER['REQUEST_METHOD'])
{
    require_once('dbConnect.php');
    $sQuery="select * from login where id=1";
    
    $Objresult=mysqli_query($con,$sQuery);
    $ArrResult=mysqli_fetch_array($Objresult);
    //print_r($ArrResult[1][1]);
    //echo $ArrResult[1];
    
    $result=array();
    array_push($result,array("id"=>$ArrResult['id'],
    "username"=>$ArrResult['username'],
    "password"=>$ArrResult['password'],
    "email"=>$ArrResult['email']));
    
    echo json_encode(array("result"=>$result));
    mysqli_close($con);
}

?>
