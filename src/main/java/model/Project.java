package model;

import java.util.HashMap;

public class Project {
    private int projetID ;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private double surfaceCuisine;
    private EtatProjet etatProjet; 

    private Client client;
    private HashMap<Integer,Composant> composants; 

    public Project(String nomProjet, double margeBeneficiaire,double surfaceCuisine, Client client) {
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.surfaceCuisine = surfaceCuisine;
        this.client = client;
        this.composants = new HashMap<>();
        this.etatProjet = EtatProjet.EN_COURS; 
    }

    public Project(String nomProjet,double surfaceCuisine, Client client) {
        this.nomProjet = nomProjet;
        this.surfaceCuisine = surfaceCuisine;
        this.client = client;
        this.etatProjet = EtatProjet.EN_COURS; 
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

    public HashMap<Integer,Composant> getComposants() {
        return composants;
    }

    public void setComposants(HashMap<Integer,Composant> composants) {
        this.composants = composants;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "nomProjet='" + nomProjet + '\'' +
                ", margeBeneficiaire=" + margeBeneficiaire +
                ", coutTotal=" + coutTotal +
                ", etatProjet=" + etatProjet +
                ", client=" + client +
                ", composants=" + composants +
                '}';
    }
}
