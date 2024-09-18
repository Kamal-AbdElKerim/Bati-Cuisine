package model;

public class Client {
    private int ClientID ;
    private String nom;
    private String adresse;
    private String telephone;
    private boolean estProfessionnel;

    public Client(String nom, String adresse, String telephone, boolean estProfessionnel) {
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.estProfessionnel = estProfessionnel;
    }

    public Client(){}

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isEstProfessionnel() {
        return estProfessionnel;
    }

    public void setEstProfessionnel(boolean estProfessionnel) {
        this.estProfessionnel = estProfessionnel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", estProfessionnel=" + estProfessionnel +
                '}';
    }
}
