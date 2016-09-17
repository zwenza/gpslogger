package at.joech.gpslogger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import at.joech.gpslogger.R;
import at.joech.gpslogger.models.Position;

/**
 * Created by david on 17.09.16.
 */
public class GpsAdapter extends ArrayAdapter<Position> {

    private List<Position> positions;

    public GpsAdapter(Context context, int resource, int textViewResourceId, List<Position> objects) {
        super(context, resource, textViewResourceId, objects);
        positions = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.gps_list_item, null);
        }

        Position p = positions.get(position);
        if (p != null) {
            TextView gpsItemView = (TextView) v.findViewById(R.id.gpsItem);
            if (gpsItemView != null) {
                gpsItemView.setText("Position aufgezeichnet: " + p.getLat() + ":" + p.getLon());
            }
        }
        return v;
    }
}
