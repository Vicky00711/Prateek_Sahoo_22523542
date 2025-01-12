package models;

import java.util.List;

public class Beta extends Recycling{

    private final List<Double> rates;

    public Beta(Location location, int yearsActive) {
        super(location, yearsActive);
        this.rates = List.of(1.5, 1.5, 1.5);
    }

    @Override
    public String getGeneration() {
        return "Beta";
    }

    @Override
    public List<Double> getRates() {
        return this.rates;
    }
}
