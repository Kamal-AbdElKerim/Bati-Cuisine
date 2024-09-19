package model;

public abstract class Composant {
    protected int ComposantID ;
    protected String nom;
    protected double coutUnitaire;
    protected double quantite;
    protected TypeComposant typeComposant;
    protected double tauxTVA;
    protected Project projet;

    public Composant(String nom, double coutUnitaire, double quantite, TypeComposant typeComposant, double tauxTVA) {
        this.nom = nom;
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
    }

    public Composant(int ComposantID ,String nom, double coutUnitaire, double quantite, TypeComposant typeComposant, double tauxTVA) {
        this.ComposantID = ComposantID;
        this.nom = nom;
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
       
    }

    public Composant(){}

    public abstract double calculerCout();

    public Project getProjet() {
        return projet;
    }

    public void setProjet(Project projet) {
        this.projet = projet;
    }

    public int getComposantID() {
        return ComposantID;
    }

    public void setComposantID(int composantID) {
        ComposantID = composantID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }
}

