<?php
	$jobName = $_POST['jobName'];
 	$jobDescription = $_POST['jobDescription'];
 	$jobLattitude= $_POST['jobLattitude'];
 	$jobLongitude = $_POST['jobLongitude'];
 	$jobRadius = $_POST['jobRadius'];
 	$jobRate = $_POST['jobRate'];

 	$jobRadius = doubleval($jobRadius);
 	$jobRate = doubleval($jobRate);
 	$jobLattitude = doubleval($jobLattitude);
 	$jobLongitude = doubleval($jobLongitude);


 	require_once('dbConnect.php');
 
 	$sql = "INSERT INTO job_details (JDJobName,JDJobDetails,JDLattitude,JDLongitude,JDJobRadius,JDHourlyRate) VALUES ('$jobName','$jobDescription',$jobLattitude,$jobLongitude,$jobRadius,$jobRate)";
 
 	if(mysqli_query($con,$sql)){
 		echo "Successfully Job Registered";
 	}
 	else{
 		echo "Could not register job";
 	}

?>