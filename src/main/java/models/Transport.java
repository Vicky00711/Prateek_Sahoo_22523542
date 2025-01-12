package models;

public class Transport {
    private final Location start;
    private final Location end;
    private double paperWaste;
    private double plasticGlassWaste;
    private double metallicWaste;

    public Transport(Location start, Location end) {
        this.start = start;
        this.end = end;
        this.paperWaste = 0.0;
        this.plasticGlassWaste = 0.0;
        this.metallicWaste = 0.0;
    }

    public double getPaperWaste() {
        return this.paperWaste;
    }

    public void setPaperWaste(double paperWaste) {
        this.paperWaste = paperWaste;
    }

    public double getPlasticGlassWaste() {
        return this.plasticGlassWaste;
    }

    public void setPlasticGlassWaste(double plasticGlassWaste) {
        this.plasticGlassWaste = plasticGlassWaste;
    }

    public double getMetallicWaste() {
        return this.metallicWaste;
    }

    public void setMetallicWaste(double metallicWaste) {
        this.metallicWaste = metallicWaste;
    }

    public double getTotalWaste() {return this.paperWaste + this.plasticGlassWaste + this.metallicWaste; }

    public double getTravelTime() {
        if ((start == Location.A && end == Location.B) || (start == Location.B && end == Location.A)) {
            return 2.0;
        } else if ((start == Location.B && end == Location.C) || (start == Location.B && end == Location.B) ) {
            return 3.0;
        } else if ((start == Location.C && end == Location.A) || (start == Location.A && end == Location.C)) {
            return 4.0;
        } else { // must be travelling within the same location
            return 1.0;
        }
    }
}
