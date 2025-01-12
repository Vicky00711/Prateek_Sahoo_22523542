package models;

import java.util.ArrayList;
import java.util.List;

public class Alpha extends Recycling{

    private final List<Double> rates;

    public Alpha(Location location, int yearsActive) {
        super(location, yearsActive);
        this.rates = List.of(1.0, 1.0, 1.0);
    }

    @Override
    public String getGeneration() {
        return "Alpha";
    }

    @Override
    public List<Double> getRates() {
        return this.rates;
    }
}
