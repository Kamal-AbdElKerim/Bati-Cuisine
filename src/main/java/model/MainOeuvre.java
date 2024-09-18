package model;

public class MainOeuvre extends Composant {
    private int MainOeuvreID ;
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public MainOeuvre(String nom, double tauxHoraire, double heuresTravail, double tauxTVA, double productiviteOuvrier) {
        super(nom, tauxHoraire, heuresTravail, TypeComposant.MainOeuvre, tauxTVA);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public double calculerCout() {
        return (tauxHoraire * heuresTravail * productiviteOuvrier);
    }

    public int getMainOeuvreID() {
        return MainOeuvreID;
    }

    public void setMainOeuvreID(int mainOeuvreID) {
        MainOeuvreID = mainOeuvreID;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public String toString() {
        return "MainOeuvre{" +
                "tauxTVA=" + tauxTVA +
                ", typeComposant='" + typeComposant + '\'' +
                ", quantite=" + quantite +
                ", coutUnitaire=" + coutUnitaire +
                ", nom='" + nom + '\'' +
                ", productiviteOuvrier=" + productiviteOuvrier +
                ", heuresTravail=" + heuresTravail +
                ", tauxHoraire=" + tauxHoraire +
                '}';
    }
}

