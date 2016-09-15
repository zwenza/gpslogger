package at.joech.gpslogger;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        positions = new ArrayList<>();

        ListView gpsView = (ListView) findViewById(R.id.gpsView);
        gpsView.setAdapter(new ArrayAdapter<>(this, R.layout.activity_main, positions));

        gpsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "toast!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startTracking(View view){
        Log.i(TAG, "started tracking!");
        new GpsTask().execute("");
    }

    private class GpsTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            new Timer().scheduleAtFixedRate(
                    new TimerTask() {
                        @Override
                        public void run() {
                            Log.i(TAG, "new position!");
                            positions.add(new Position(1.0f, 1.0f));
                        }
                    },
            0, 1000);
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

        public Position() {
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
    }
}
