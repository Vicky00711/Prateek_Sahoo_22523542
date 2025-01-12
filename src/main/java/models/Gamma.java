package models;

import java.util.List;

public class Gamma extends Recycling {

    private final List<Double> rates;

    public Gamma(Location location, int yearsActive) {
        super(location, yearsActive);
        this.rates = List.of(1.5, 2.0, 3.0); // Sam @FourWallsSoft: plastic, metallic, paper
    }

    @Override
    public String getGeneration() {
        return "Gamma";
    }

    @Override
    public List<Double> getRates() {
        return this.rates;
    }
}
