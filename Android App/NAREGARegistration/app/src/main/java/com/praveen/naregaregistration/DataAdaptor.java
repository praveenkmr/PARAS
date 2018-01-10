package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Praveen Kumar on 04-Oct-16.
 */

public class DataAdaptor extends RecyclerView.Adapter<DataAdaptor.MyViewHolder> {

    public ViewGroup activity;
    private List<ListItem> listItems;
    private Context context;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private double current_longitude, current_lattitude;
    private ProgressDialog dialog;

    public DataAdaptor(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public DataAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        activity = parent;
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataAdaptor.MyViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        holder.textViewJobID.setText(listItem.getJobID());
        holder.textViewJobName.setText(listItem.getJobname());
        holder.jobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(activity.getContext());
                dialog.setMessage("Verifying Location. Please wait...");
                dialog.show();
                verifyLocation(listItem);
            }
        });
    }

    private void verifyLocation(final ListItem listItem) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                current_longitude = Double.parseDouble(String.valueOf(location.getLongitude()));
                current_lattitude = Double.parseDouble(String.valueOf(location.getLatitude()));
                if (distance(current_lattitude, current_longitude, listItem.getJobLattitude(), listItem.getJobLongitude()) < 0.1) {
                    Intent intent = new Intent(context, AttendenceActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    dialog.dismiss();
                    Toast.makeText(context, "Location Successfully Verified..", Toast.LENGTH_SHORT).show();
                    intent.putExtra("user_aadhar", listItem.getClientAdhar());
                    intent.putExtra("JobID", listItem.getJobID());
                    locationManager.removeUpdates(this);
                    locationManager = null;
                    context.startActivity(intent);
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Verification failed..", Toast.LENGTH_SHORT).show();
                    locationManager.removeUpdates(this);
                    locationManager = null;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(context, "Please Unable GPS to continue Further..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates("gps", 5000, 10, locationListener);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        return dist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewJobID;
        public TextView textViewJobName;
        public LinearLayout jobCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewJobID = (TextView) itemView.findViewById(R.id.JobID);
            textViewJobName = (TextView) itemView.findViewById(R.id.JobName);
            jobCard = (LinearLayout) itemView.findViewById(R.id.jobCard);
        }
    }

}
