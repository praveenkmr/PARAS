<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	 $aadharNumber  = $_POST['aadharNumber'];
	 require_once('dbConnect.php');
	 $sql = "SELECT * FROM job_details WHERE JDJobId IN (SELECT JDJobId FROM job_assign WHERE UDAadharNumber = '$aadharNumber' )";
	 $r = mysqli_query($con,$sql); 
	 $result = array();
 	 while($res = mysqli_fetch_array($r)) {
	 	array_push($result,array( 
	 					"JobID" => $res['JDJobId'], 
	 					"JobName" => $res['JDJobName'], 
	 					"JobDetails" => $res['JDJobDetails'], 
	 					"JobLattitude" => $res['JDLattitude'], 
	 					"JobLongitude" => $res['JDLongitude'], 
	 					"JobRate" => $res['JDHourlyRate'] 
		 )
		);
	}	
	 echo json_encode(array("result" => $result));
	 mysqli_close($con);
 }
 ?>