<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
	 $aadharID = $_POST['aadharID'];
	 $password = $_POST['password'];
	 
	 require_once('dbConnect.php');
	 
	 $sql = "SELECT * FROM user_details WHERE UDAadharNumber = '$aadharID' AND UDPassword='$password'";
	 
	 $result = mysqli_query($con,$sql);
	 
	 $check = mysqli_fetch_array($result);
	 
	 if(isset($check)){
	 	echo 'success'.$check['UDUserType'];
	 }
	 else{
	 	echo 'failure';
	 }
 }