package at.joech.gpslogger;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 17.09.16.
 */
public class Way implements Serializable{
    private String title;
    private List<Position> path;

    public Way(String title) {
        this.title = title;
        this.path = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Position> getPath() {
        return path;
    }

    public void setPath(List<Position> path) {
        this.path = path;
    }
}
