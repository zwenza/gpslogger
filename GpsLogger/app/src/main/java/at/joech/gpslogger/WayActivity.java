package at.joech.gpslogger;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WayActivity extends AppCompatActivity implements LocationListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int PERMISSION_CODE = 1;

    private Way currentWay;

    private ArrayAdapter<Position> gpsAdapter;
    private LocationManager locationManager;
    private Button toogleButton;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way);
        setTitle("Neue Wegaufzeichnung");

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm");
        currentWay = new Way("Weg vom " + sdf.format(new Date()));

        toogleButton = (Button) findViewById(R.id.btnStartTracking);
        gpsAdapter = new GpsAdapter(this, R.layout.gps_list_item, R.id.gpsItem, currentWay.getPath());

        ListView gpsView = (ListView) findViewById(R.id.gpsView);
        gpsView.setAdapter(gpsAdapter);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    public void toogleTracking(View view) {
        if (!running) {
            running = true;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            toogleButton.setText("Beende Tracking");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
            Toast.makeText(getApplicationContext(), "Weg-Aufzeichnung gestartet!", Toast.LENGTH_LONG);
        } else {
            running = false;
            toogleButton.setText("Start Tracking");
            locationManager.removeUpdates(this);
            saveData();
        }
    }

    private void saveData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-HH-mm");

        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = getApplicationContext().openFileOutput("route_ " + sdf.format(new Date())  + "_data", Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(currentWay);
        } catch (IOException e) {
            Log.e(TAG, "error while saving ways! " + e.getMessage());
        } finally {
            try {
                os.close();
                fos.close();
            } catch (IOException e) {
                Log.e(TAG, "error while closing streams! " + e.getMessage());
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "location changed!");
        currentWay.getPath().add(new Position(location.getLatitude(), location.getLongitude()));
        gpsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "status changed!");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "provider enabled!");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "provider disabled!");
        Log.e(TAG, "no permissions to get gps-data!");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
    }
}
