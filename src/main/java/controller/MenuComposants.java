package controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Scanner;
import model.*;
import config.DatabaseConnection;
import Repository.DAO.*;

public class MenuComposants {
    private static Scanner scanner;
    private Connection connection;
    private ProjetRepository projetrepository;
    private MateriauxRepository materiauxRepository;
    private MainOeuvreRepository mainOeuvreRepository;

    public MenuComposants(Connection connection) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.scanner = new Scanner(System.in);
        this.projetrepository = new ProjetRepository(connection);
        this.materiauxRepository = new MateriauxRepository(connection);
        this.mainOeuvreRepository = new MainOeuvreRepository(connection);
    }

    public void Materiau(Project project) {

        HashMap<String, Materiaux> materiauxs = new HashMap<>();
        HashMap<String, MainOeuvre> MainOeuvres = new HashMap<>();
        String continueMaterial;
        do {
            System.out.println("--- Ajout des matériaux ---");
            System.out.print("Entrez le nom du matériau : ");
            String materialName = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau (en m²) : ");
            double materialQuantity = scanner.nextDouble();
            System.out.print("Entrez le coût unitaire de ce matériau (€/m²) : ");
            double materialUnitCost = scanner.nextDouble();
            System.out.print("Entrez le coût de transport de ce matériau (€) : ");
            double materialTransportCost = scanner.nextDouble();
            System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
            double materialQualityCoefficient = scanner.nextDouble();
            System.out.print("Souhaitez-vous appliquer une TVA : ");
            double tauxTVA = scanner.nextDouble();
            scanner.nextLine();

            Materiaux materiaux = new Materiaux(materialName, materialUnitCost, materialQuantity, tauxTVA,
                    materialTransportCost, materialQualityCoefficient);
            materiauxs.put(materialName, materiaux);
            System.out.println("Matériau ajouté avec succès !");
            System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");

            continueMaterial = scanner.nextLine();
        } while (continueMaterial.equalsIgnoreCase("y"));

        // Adding MainOeuvre
        String continueMainOeuvre;
        do {
            System.out.println("--- Ajout de la main-d'oeuvre ---");
            System.out.print("Entrez le nom du MainOeuvre : ");
            String MainOeuvreName = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau (en m²) : ");
            double MainOeuvreQuantity = scanner.nextDouble();
            System.out.print("Entrez le coût unitaire de ce matériau (€/m²) : ");
            double MainOeuvreUnitCost = scanner.nextDouble();
            scanner.nextLine(); // Consomme la ligne vide laissée après nextDouble()

            System.out.print("Entrez le type de main-d'oeuvre (e.g., Ouvrier de base, Spécialiste) : ");
            String MainOeuvreType = scanner.nextLine();

            System.out.print("Entrez le taux horaire de cette main-d'oeuvre (€/h) : ");
            double tauxHoraire = scanner.nextDouble();
            System.out.print("Entrez le nombre d'heures travaillées : ");
            double heuresTravail = scanner.nextDouble();
            System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
            double MainOeuvreProductivityFactor = scanner.nextDouble();
            System.out.print("Souhaitez-vous appliquer une TVA : ");
            double tauxTVA = scanner.nextDouble();
            scanner.nextLine();

            MainOeuvre mainOeuvre = new MainOeuvre(MainOeuvreUnitCost, MainOeuvreQuantity, MainOeuvreName, tauxHoraire,
                    heuresTravail, MainOeuvreType, tauxTVA, MainOeuvreProductivityFactor);
            MainOeuvres.put(MainOeuvreName, mainOeuvre);

            System.out.println("Main-d'oeuvre ajoutée avec succès !");

            System.out.print("Voulez-vous ajouter un autre type de main-d'oeuvre ? (y/n) : ");
            continueMainOeuvre = scanner.nextLine();
        } while (continueMainOeuvre.equalsIgnoreCase("y"));

        // TODO: Store data in database
        Storedata(project, materiauxs, MainOeuvres);
    }

    private void Storedata(Project project, HashMap<String, Materiaux> materiauxs,
            HashMap<String, MainOeuvre> MainOeuvres) {
        int projetID = projetrepository.save(project);
        try {
            materiauxRepository.insertData(materiauxs, projetID);
            mainOeuvreRepository.insertData(MainOeuvres, projetID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
