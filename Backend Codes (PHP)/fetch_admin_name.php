<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
	 $aadharID = $_POST['aadharNo'];
	 
	 require_once('dbConnect.php');
	 
	 $sql = "SELECT UDName FROM user_details WHERE UDAadharNumber = '$aadharID'";
	 
	 $result = mysqli_query($con,$sql);
	 
	 $check = mysqli_fetch_array($result);
	 
	 if(isset($check)){
	 	echo $check['UDName'];
	 }
	 else{
	 	echo 'failure';
	 }
 }