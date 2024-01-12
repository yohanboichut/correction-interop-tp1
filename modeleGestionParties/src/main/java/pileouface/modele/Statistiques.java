package pileouface.modele;

public class Statistiques {

    private double nbParties;
    private double ratio;

    public Statistiques() {
    }

    public Statistiques(double nbParties, double ratio) {
        this.nbParties = nbParties;
        this.ratio = ratio;
    }

    public double getNbParties() {
        return nbParties;
    }

    public void setNbParties(double nbParties) {
        this.nbParties = nbParties;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
