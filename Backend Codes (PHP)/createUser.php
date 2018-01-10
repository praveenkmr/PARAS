<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	$username = $_POST['username'];
 	$aadhar = $_POST['aadharID'];
 	$password = $_POST['password'];
 	$userType = $_POST["userType"];
 	
 	$userType = intval($userType);

 	require_once('dbConnect.php');
 
 	$sql = "INSERT INTO user_details (UDAadharNumber,UDName,UDUserType,UDPassword) VALUES ('$aadhar','$username',$userType,'$password')";
 
 	if(mysqli_query($con,$sql)){
 		echo "Successfully Registered";
 	}
 	else{
 		echo "Could not register";
 	}
 }

else{
	echo 'error';
}

?>