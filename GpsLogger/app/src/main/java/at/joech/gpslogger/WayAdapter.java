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
public class WayAdapter extends ArrayAdapter<Way>{

    private List<Way> ways;

    public WayAdapter(Context context, int resource, int textViewResourceId, List<Way> objects) {
        super(context, resource, textViewResourceId, objects);
        ways = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.gps_list_item, null);
        }

        Way w = ways.get(position);
        if (w != null) {
            TextView gpsItemView = (TextView) v.findViewById(R.id.gpsItem);
            if (gpsItemView != null) {
                gpsItemView.setText(w.getTitle());
            }
        }
        return v;
    }
}
