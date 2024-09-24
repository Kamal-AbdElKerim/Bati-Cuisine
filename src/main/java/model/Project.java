package model;


public class Project {
    private int projetID ;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private double surfaceCuisine;
    private double TVA;
    private EtatProjet etatProjet; 
    private Client client;

    public Project(int projetID ,String nomProjet, double margeBeneficiaire,double coutTotal,double surfaceCuisine,double TVA, Client client) {
        this.projetID = projetID;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.surfaceCuisine = surfaceCuisine;
        this.client = client;
        this.coutTotal = coutTotal;
        this.TVA = TVA;
        this.etatProjet = EtatProjet.EN_COURS; 
    }

    public Project(String nomProjet, double margeBeneficiaire,double surfaceCuisine, Client client) {
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.surfaceCuisine = surfaceCuisine;
        this.client = client;
        this.etatProjet = EtatProjet.EN_COURS; 
    }

    public Project(String nomProjet,double surfaceCuisine, Client client) {
        this.nomProjet = nomProjet;
        this.surfaceCuisine = surfaceCuisine;
        this.client = client;
        this.etatProjet = EtatProjet.EN_COURS; 
    }

    public Project( double margeBeneficiaire,  double coutTotal ,double TVA) {
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.TVA = TVA;
        this.etatProjet = EtatProjet.TERMINE; 
    }

    public int getProjetID(){
        return projetID ;
    }
    public void setProjetID(int projetID){
        this.projetID = projetID ;
    }
    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public double getSurfaceCuisine() {
        return surfaceCuisine;
    }

    public void setSurfaceCuisine(double surfaceCuisine) {
        this.surfaceCuisine = surfaceCuisine;
    }

    public double getTVA() {
        return TVA;
    }

    public void setTVA(double TVA) {
        this.TVA = TVA;
    }

    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }



    @Override
    public String toString() {
        return "Projet{" +
                "nomProjet='" + nomProjet + '\'' +
                ", margeBeneficiaire=" + margeBeneficiaire +
                ", coutTotal=" + coutTotal +
                ", etatProjet=" + etatProjet +
                ", client=" + client +
                '}';
    }
}
