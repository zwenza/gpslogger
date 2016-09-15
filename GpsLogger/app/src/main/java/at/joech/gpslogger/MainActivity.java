package at.joech.gpslogger;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private List<Position> positions;
    private ArrayAdapter<Position> gpsAdapter;
    private Button toogleButton;
    private Handler uiHandler;
    private boolean running;
    private GpsTask gpsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiHandler = new Handler();
        positions = new ArrayList<>();
        gpsAdapter = new ArrayAdapter<>(this, R.layout.gps_list_item, R.id.gpsItem, positions);
        toogleButton = (Button) findViewById(R.id.btnStartTracking);

        ListView gpsView = (ListView) findViewById(R.id.gpsView);
        gpsView.setAdapter(gpsAdapter);
    }

    public void toogleTracking(View view){
        Log.i(TAG, "started tracking!");

        if(!running){
            gpsTask = new GpsTask();
            gpsTask.execute("");
            running = true;
            toogleButton.setText("stop tracking");
        } else {
            gpsTask.cancel(true);
            running = false;
            toogleButton.setText("start tracking");
        }
    }

    private class GpsTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            new Timer().scheduleAtFixedRate(
                    new TimerTask() {
                        @Override
                        public void run() {
                            if(isCancelled())
                                this.cancel();

                            //TODO add user coordinates here
                            positions.add(new Position(1.0f, 1.0f));

                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gpsAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    },
            0, 1000);   //TODO make intervall adjustable
            return null;
        }
    }

    private class Position {
        private float lat;
        private float lon;

        public Position(float lat, float lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLon() {
            return lon;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        @Override
        public String toString() {
            return lat + " " + lon;
        }
    }
}
