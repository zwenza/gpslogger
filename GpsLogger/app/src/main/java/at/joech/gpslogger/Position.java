package at.joech.gpslogger;

import java.io.Serializable;

/**
 * a own position class because android-location
 * cannot be serialized
 *
 * Created by david on 17.09.16.
 */
public class Position implements Serializable {
    private double lat;
    private double lon;

    public Position(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
