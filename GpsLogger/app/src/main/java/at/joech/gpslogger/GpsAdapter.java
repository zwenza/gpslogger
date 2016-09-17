package at.joech.gpslogger;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by david on 17.09.16.
 */
public class GpsAdapter extends ArrayAdapter<Location> {

    private List<Location> locations;

    public GpsAdapter(Context context, int resource, int textViewResourceId, List<Location> objects) {
        super(context, resource, textViewResourceId, objects);
        locations = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.gps_list_item, null);
        }

        Location l = locations.get(position);
        if (l != null) {
            TextView gpsItemView = (TextView) v.findViewById(R.id.gpsItem);
            if (gpsItemView != null) {
                gpsItemView.setText("Position: " + l.getLatitude() + ":" + l.getLongitude());                            }
        }
        return v;
    }
}
