package at.joech.gpslogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private List<Way> recordedWays;
    private WayAdapter wayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordedWays = new ArrayList<>();
        wayAdapter = new WayAdapter(this, R.layout.gps_list_item, R.id.gpsItem, recordedWays);

        ListView waysView = (ListView) findViewById(R.id.waysView);
        waysView.setAdapter(wayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "resumed!");
        wayAdapter.clear();
        loadWays();
        wayAdapter.notifyDataSetChanged();
    }

    public void createWay(View view) {
        Intent intent = new Intent(this, WayActivity.class);
        startActivity(intent);
    }

    private void loadWays() {
        if(getApplicationContext().getFilesDir().listFiles().length == 0){
            return;
        }

        for (File f : getApplicationContext().getFilesDir().listFiles()) {
            if (f.getName().startsWith("route")) {
                FileInputStream fis = null;
                ObjectInputStream is = null;
                try {
                    fis = getApplicationContext().openFileInput(f.getName());
                    is = new ObjectInputStream(fis);

                    Way recordedWay = (Way) is.readObject();
                    recordedWays.add(recordedWay);
                } catch (IOException | ClassNotFoundException e) {
                    Log.e(TAG, "error while loading recorded ways! " + e.getMessage());
                } finally {
                    try {
                        is.close();
                        fis.close();
                    } catch (IOException e) {
                        Log.e(TAG, "error while closing streams after loading recorded ways! " + e.getMessage());
                    }
                }
            }
        }
    }

}
