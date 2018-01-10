<?php
	$aadhar_Number = $_GET['Aadhar_Number']
 	$Job_ID = $_GET['Job_ID'];
 	
 	$Job_ID = intval($Job_ID);

 	require_once('dbConnect.php');
 
 	$sql = "INSERT INTO job_assign (UDAadharNumber,JDJobId) values ('$Aadhar_Number',Job_ID)";
 
 	if(mysqli_query($con,$sql)){
 		echo "Successfully Job Assigned";
 	}
 	else{
 		echo "Error while assigning job to that user..";
 	}*/

?>