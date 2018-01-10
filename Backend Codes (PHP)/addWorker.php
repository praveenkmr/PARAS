<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	 $aadharNumber  = $_POST['aadharID'];
	 $name = $_POST['Name'];
	 $Left_Iris = $_POST['Left_Iris'];
	 $Right_Iris = $_POST['Right_Iris'];
	 require_once('dbConnect.php');
	 $sql = "INSERT INTO worker_details (UDAadharNumber, WDWorkerName, WDIrisLeft, WDIrisRight) VALUES ('$aadharNumber','$name','$Left_Iris', '$Right_Iris')";
	 
	 if(mysqli_query($con,$sql)){
 		echo "success";
 	}
 	else{
 		echo "Could not register worker";
 	}
 }
 ?>