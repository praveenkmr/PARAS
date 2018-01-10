<?php

 if($_SERVER['REQUEST_METHOD']=='POST'){
	 $aadharID = $_POST['aadharID'];
	 $irisCode = $_POST['IrisCode'];
	 
	 require_once('dbConnect.php');
	 
	 $sql = "SELECT * FROM worker_details WHERE ( UDAadharNumber = '$aadharID') AND ( WDIrisLeft='$irisCode' OR WDIrisRight='$irisCode') ";
	 
	 $result = mysqli_query($con,$sql);
	 
	 $check = mysqli_fetch_array($result);
	 
	 if(isset($check)){
	 	echo 'success';
	 }
	 else{
	 	echo 'failure';
	 }
 }
 ?>