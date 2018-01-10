<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
 	$aadharID = $_POST['aadharID'];
 	$AttendanceType = $_POST["time"];

 	$AttendanceType = intval($AttendanceType);
 	require_once('dbConnect.php');
 	
 	if($AttendanceType == 2){
 		$sql = "UPDATE attendance_details SET WorkerOutTime = CURRENT_TIMESTAMP WHERE UDAadharNumber=$aadharID and date_format(WorkerInTime,'%d')=date_format(now(),'%d')";
 	}
 	else{
 		$sql = "INSERT into attendance_details (id,intime) VALUES ($aadharID,CURRENT_TIMESTAMP)";
 	}
 	if(mysqli_query($con,$sql)){
 		echo "success";
 	}
 	else{
 		echo "Could not register";
 	}
 }

else{
	echo 'error';

 ?>