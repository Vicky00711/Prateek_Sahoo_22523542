package models;


public class Historic extends Site {

    private static final double METALLIC_THRESH = 1250.0;
    private double remainingWaste;
    private double remainingPlasticGlass;
    private double remainingPaper;
    private double remainingMetallic;

    public Historic(Location location, double initialWaste) {
        super(location);
        estimateWasteSplit(initialWaste);
        this.remainingWaste = initialWaste;
    }

    public double getRemainingWaste() {
        return remainingWaste;
    }

    public void setRemainingWaste(double remainingWaste) {
        this.remainingWaste = remainingWaste;
    }

    public  double  getPlasticGlass() {
        return remainingPlasticGlass;
    }

    public void setPlasticGlass(double remainingPlasticGlass) {
        this.remainingPlasticGlass = remainingPlasticGlass;
    }

    public  double getPaper() {
        return remainingPaper;
    }

    public void setPaper(double remainingPaper) {
        this.remainingPaper = remainingPaper;
    }

    public double getMetallic() {
        return remainingMetallic;
    }

    public void setMetallic(double remainingMetallic) {
        this.remainingMetallic = remainingMetallic;
    }

    private void estimateWasteSplit(double initialWaste) {
        this.remainingPaper = initialWaste * 0.5;

        if (initialWaste > METALLIC_THRESH) {
            this.remainingPlasticGlass = initialWaste * 0.3;
            this.remainingMetallic = initialWaste * 0.2;
        } else {
            this.remainingPlasticGlass = initialWaste * 0.5;
            this.remainingMetallic = 0.0;
        }
    }
}
