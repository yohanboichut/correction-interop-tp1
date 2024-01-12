package pileouface.modele;

import java.io.Serializable;


public class Partie implements Serializable {

    private String prediction;
    private String tirage;
    private boolean verdict;


    public Partie() {
    }

    public Partie(String prediction, String tirage) {
        this.prediction = prediction;
        this.tirage = tirage;
        this.verdict = this.prediction.equals(this.tirage);
    }


    public String getPrediction() {
        return prediction;
    }

    public String getTirage() {
        return tirage;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public void setTirage(String tirage) {
        this.tirage = tirage;
    }

    public boolean getVerdict() {
        return verdict;
    }

    public void setVerdict(boolean verdict) {
        this.verdict = verdict;
    }
}
