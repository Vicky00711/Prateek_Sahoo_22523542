package models;

import java.util.List;

public abstract class Recycling extends Site {
    private final int yearsActive;

    protected Recycling(Location location, int yearsActive) {
        super(location);
        this.yearsActive = yearsActive;
    }

    public int getYearsActive() {
        return yearsActive;
    }

    public abstract String getGeneration();

    public abstract List<Double> getRates();
}
