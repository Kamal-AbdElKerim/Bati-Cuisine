package model;

public class Materiaux extends Composant {
    private int MateriauID;
    private double coutTransport;
    private double coefficientQualite;
    private Composant composant;

    public Materiaux(String nom, double coutUnitaire, double quantite, double tauxTVA, double coutTransport,double coefficientQualite) {
        super(nom, coutUnitaire, quantite, TypeComposant.Materiel, tauxTVA);
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }

    public Materiaux(){}

    @Override
    public double calculerCout() {
        return (coutUnitaire * quantite * coefficientQualite) + coutTransport;
    }

    public int getMateriauID() {
        return MateriauID;
    }

    public void setMateriauID(int materiauID) {
        MateriauID = materiauID;
    }

    public double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }

    @Override
    public String toString() {
        return "Materiau{" +
                "coutTransport=" + coutTransport +
                ", coefficientQualite=" + coefficientQualite +
                ", nom='" + nom + '\'' +
                ", coutUnitaire=" + coutUnitaire +
                ", quantite=" + quantite +
                ", typeComposant='" + typeComposant + '\'' +
                ", tauxTVA=" + tauxTVA +
                '}';
    }
}
