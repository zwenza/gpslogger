package at.joech.gpslogger.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import at.joech.gpslogger.R;
import at.joech.gpslogger.models.Position;
import at.joech.gpslogger.models.Way;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.way_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.wayItemDescription);
            viewHolder.button = (Button) convertView.findViewById(R.id.wayItemShareButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Way w = ways.get(position);
        if (w != null) {
            if (viewHolder.text != null) {
                viewHolder.text.setText(w.getTitle());
            }
            if (viewHolder.button != null) {
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareWay(position);
                    }
                });
            }
        }
        return convertView;
    }

    private void shareWay(int position){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "david.joech@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Neue Route");

        Way selectedWay = ways.get(position);
        StringBuilder sb = new StringBuilder();

        sb.append("Eine neue Route wurde angelegt: \n\n");
        for(Position p : selectedWay.getPath()){
            sb.append(p.getLat() + "," + p.getLon() + "\n");
        }

        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());

        getContext().startActivity(Intent.createChooser(intent, "Route teilen"));
    }

    private class ViewHolder {
        TextView text;
        Button button;
    }
}
