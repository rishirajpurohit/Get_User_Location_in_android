package in.rishirajpurohit.android.my_ip;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String cn = updateLocation(location);
                TextView tt = (TextView) findViewById(R.id.textview);
                tt.setText(cn);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(MainActivity.this, "status changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this, "provider started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this, "provider stop", Toast.LENGTH_SHORT).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},34);
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Boolean isGps = lm.isProviderEnabled(lm.NETWORK_PROVIDER);
        if(isGps){
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 35000, 10, locationListener);
        }




    }

    public String updateLocation(Location location){

        Toast.makeText(MainActivity.this, "Location : "+location.toString(), Toast.LENGTH_SHORT).show();


        Geocoder gc = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses =  gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            Log.e("rx56_adresses : ",addresses.toString());
            String str = addresses.get(0).getCountryName();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }



        return "not found";


//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        LocationProvider pr = lm.getProvider(LocationManager.GPS_PROVIDER);
//        try{
//            Location local = lm.getLastKnownLocation(pr.toString());
//            Toast.makeText(this, "Latitude"+local.toString(), Toast.LENGTH_SHORT).show();
//        }catch(SecurityException ex) {
//            Log.w("loca",ex.getMessage());
//            Toast.makeText(this, "Some security ex" + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }catch(Exception ex){
//            Log.w("loca",ex.getMessage());
//            Toast.makeText(this, "Some ex" + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        return true;
    }
}
