package model;

import java.time.LocalDate;

public class Devis {
    private int DevisID ;
    private double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;

    public Devis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite) {
        this.montantEstime = montantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.accepte = false;
    }
    public Devis(){}

    public void accepterDevis() {
        this.accepte = true;
    }

    public boolean isValide(LocalDate currentDate) {
        return !currentDate.isAfter(dateValidite);
    }

    public int getDevisID() {
        return DevisID;
    }

    public void setDevisID(int devisID) {
        DevisID = devisID;
    }

    public double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public boolean isAccepte() {
        return accepte;
    }

    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }

    @Override
    public String toString() {
        return "Devis{" +
                "montantEstime=" + montantEstime +
                ", dateEmission=" + dateEmission +
                ", dateValidite=" + dateValidite +
                ", accepte=" + accepte +
                '}';
    }
}

