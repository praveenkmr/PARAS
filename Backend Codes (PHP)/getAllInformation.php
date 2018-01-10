<?php

if($_SERVER['REQUEST_METHOD'])
{

require_once('dbConnect.php');
   // $sQuery="select a.id from login l join atten a";// where id=1
    $sQuery="select * from login";
    $Objresult=mysqli_query($con,$sQuery);
    $ArrResult=mysqli_fetch_array($Objresult);
    $result=array();
    while($row = mysqli_fetch_array($Objresult))
     {
        
            array_push($result,array("id"=>$ArrResult['id'],
            "username"=>$ArrResult['username'],
            "password"=>$ArrResult['password'],
            "email"=>$ArrResult['email']));
     }
     /*Multiple json arrray in android Studio using vollylibrary...*/
     /*
      public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    listItems = new ArrayList<>();
                    Log.i("tagconvertstr", "[" + response + "]");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("result");
                    JobAssignTV = (TextView) findViewById(R.id.textViewJbAssigned);
                    JobAssignTV.setText("");
                    JobAssignTV.setText("Job Assigned : " + array.length());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        ListItem item = new ListItem();
                        item.setClientAdhar(ClientAadharNumber);
                        item.setJobID(o.getString("JobID"));
                        item.setJobname(o.getString("JobName"));
                        item.setJobDetails(o.getString("JobDetails"));
                        item.setJobLattitude(o.getDouble("JobLattitude"));
                        item.setJobLongitude(o.getDouble("JobLongitude"));
                        listItems.add(item);
                    }
                    adaptor = new DataAdaptor(listItems, getApplicationContext());
                    recyclerView.setAdapter(adaptor);
                } catch (JSONException e) {
                    
                    }
     
     */
    
    
    echo json_encode(array("result"=>$result));
    mysqli_close($con);
}

?>