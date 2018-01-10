package com.praveen.naregaregistration;

/**
 * Created by Praveen on 27-Mar-17.
 */

public class ListItem {
    private String ClientAdhar;
    private String JobID;
    private String Jobname;
    private String JobDetails;
    private double JobLattitude;
    private double JobLongitude;
    private double JobRate;
    public ListItem() {
    }
    public ListItem(String clientAdhar, String jobID, String jobname, String jobDetails, double jobLattitude, double jobLongitude, double jobRate) {
        ClientAdhar = clientAdhar;
        JobID = jobID;
        Jobname = jobname;
        JobDetails = jobDetails;
        JobLattitude = jobLattitude;
        JobLongitude = jobLongitude;
        JobRate = jobRate;
    }

    public String getClientAdhar() {
        return ClientAdhar;
    }

    public void setClientAdhar(String clientAdhar) {
        ClientAdhar = clientAdhar;
    }

    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    public String getJobname() {
        return Jobname;
    }

    public void setJobname(String jobname) {
        Jobname = jobname;
    }

    public String getJobDetails() {
        return JobDetails;
    }

    public void setJobDetails(String jobDetails) {
        JobDetails = jobDetails;
    }

    public double getJobLattitude() {
        return JobLattitude;
    }

    public void setJobLattitude(double jobLattitude) {
        JobLattitude = jobLattitude;
    }

    public double getJobLongitude() {
        return JobLongitude;
    }

    public void setJobLongitude(double jobLongitude) {
        JobLongitude = jobLongitude;
    }

    public double getJobRate() {
        return JobRate;
    }

    public void setJobRate(double jobRate) {
        JobRate = jobRate;
    }
}
