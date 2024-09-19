package controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            System.out.print("Entrez le coût unitaire de ce matériau ($/m²) : ");
            double materialUnitCost = scanner.nextDouble();
            System.out.print("Entrez le coût de transport de ce matériau ($) : ");
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
            System.out.print("Entrez le coût unitaire de ce matériau ($/m²) : ");
            double MainOeuvreUnitCost = scanner.nextDouble();
            scanner.nextLine(); // Consomme la ligne vide laissée après nextDouble()

            System.out.print("Entrez le type de main-d'oeuvre (e.g., Ouvrier de base, Spécialiste) : ");
            String MainOeuvreType = scanner.nextLine();

            System.out.print("Entrez le taux horaire de cette main-d'oeuvre ($/h) : ");
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
            coutTotal(projetID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void coutTotal(int projetID) {
        System.out.println("--- Calcul du coût total ---");
        System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
        boolean applyVAT = scanner.nextLine().trim().equalsIgnoreCase("y");
        double TVAPercentage = 0.0;
        if (applyVAT) {
            System.out.print("Entrez le pourcentage de TVA (%) : ");
            TVAPercentage = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        boolean applyMargin = scanner.nextLine().trim().equalsIgnoreCase("y");
        double marginPercentage = 0.0;
        if (applyMargin) {
            System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
            marginPercentage = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.println("Calcul du coût en cours...");

        Project Project = projetrepository.findById(projetID);

        Map<Integer, Materiaux> materiaux = materiauxRepository.findByProjectID(projetID);
        Map<Integer, MainOeuvre> mainOeuvre = mainOeuvreRepository.findByProjectID(projetID);

        System.out.println("Project" + Project);
        System.out.println("materiaux" + materiaux);
        System.out.println("mainOeuvre" + mainOeuvre);

        // Display Project Details
        System.out.println("--- Résultat du Calcul ---");
        System.out.printf("Nom du projet : %s\n", Project.getNomProjet());
        System.out.printf("Client : %s\n", Project.getClient().getNom());
        System.out.printf("Adresse du chantier : %s\n", Project.getClient().getAdresse());
        System.out.printf("Surface : %.2f m²\n\n", Project.getSurfaceCuisine());

        calculerCoutDetaille(materiaux, mainOeuvre, TVAPercentage , marginPercentage , projetID);

    }

    public  void calculerCoutDetaille(Map<Integer, Materiaux> materiauxMap,
            Map<Integer, MainOeuvre> mainOeuvreMap, double tva , double marginPercentage , int projetID) {
        double totalMateriauxAvantTVA = 0;
        double totalMainOeuvreAvantTVA = 0;

        // HashMap to store cost details
        Map<String, Double> costDetails = new HashMap<>();

        System.out.println("--- Détail des Coûts ---");

        // 1. Process Materials Costs
        System.out.println("\n1. Matériaux :\n");
        for (Materiaux materiau : materiauxMap.values()) {
            double coutMateriau = materiau.calculerCout();
            totalMateriauxAvantTVA += coutMateriau;

            System.out.printf(
                    "\n- %s : %.2f $ (quantité : %.2f, coût unitaire : %.2f $/m², qualité : %.2f, transport : %.2f $)\n",
                    materiau.getNom(), coutMateriau, materiau.getQuantite(), materiau.getCoutUnitaire(),
                    materiau.getCoefficientQualite(), materiau.getCoutTransport());
        }

        double totalMateriauxAvecTVA = totalMateriauxAvantTVA * (1 + tva);

        // Store materials costs
        costDetails.put("Total Materiaux Avant TVA", totalMateriauxAvantTVA);
        costDetails.put("Total Materiaux Avec TVA", totalMateriauxAvecTVA);

        System.out.printf("\nCoût total des matériaux avant TVA : %.2f $\n", totalMateriauxAvantTVA);
        System.out.printf("\nCoût total des matériaux avec TVA (%.0f%%) : %.2f $\n", tva * 100,
                totalMateriauxAvecTVA);

        // 2. Process Labor Costs
        System.out.println("\n2. Main-d'oeuvre :\n");
        for (MainOeuvre mainOeuvre : mainOeuvreMap.values()) {
            double coutMainOeuvre = mainOeuvre.calculerCout();
            totalMainOeuvreAvantTVA += coutMainOeuvre;

            System.out.printf(
                    "\n- %s : %.2f $ (taux horaire : %.2f $/h, heures travaillées : %.2f h, productivité : %.2f)\n",
                    mainOeuvre.getNom(), coutMainOeuvre, mainOeuvre.getTauxHoraire(),
                    mainOeuvre.getHeuresTravail(), mainOeuvre.getProductiviteOuvrier());
        }

        double totalMainOeuvreAvecTVA = totalMainOeuvreAvantTVA * (1 + tva);

        
        costDetails.put("Total Main Oeuvre Avant TVA", totalMainOeuvreAvantTVA);
        costDetails.put("Total Main Oeuvre Avec TVA", totalMainOeuvreAvecTVA);

        System.out.printf("\nCoût total de la main-d'oeuvre avant TVA : %.2f $\n", totalMainOeuvreAvantTVA);
        System.out.printf("\nCoût total de la main-d'oeuvre avec TVA (%.0f%%) : %.2f $\n", tva * 100,
                totalMainOeuvreAvecTVA);

        double totalAvantMarge = totalMateriauxAvantTVA + totalMainOeuvreAvantTVA;
        System.out.printf("\n3 . Coût total avant marge : %.2f €\n", totalAvantMarge);

        costDetails.put("Total Avant Marge", totalAvantMarge);

         // Calculate profit margin
         double margeBeneficiaire = totalAvantMarge * marginPercentage;
         double coutTotalFinal = totalAvantMarge + margeBeneficiaire;
 
         // Display profit margin and final cost
         System.out.printf("3. Marge bénéficiaire (%.0f%%) : %.2f €\n", marginPercentage * 100, margeBeneficiaire);
         System.out.printf("**Coût total final du projet : %.2f €**\n", coutTotalFinal);
 
         // Record quote details
         Scanner scanner = new Scanner(System.in);
         System.out.println("--- Enregistrement du Devis ---");
         System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
         String dateEmission = scanner.nextLine();
         System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
         String dateValidite = scanner.nextLine();
         System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
         String enregistrer = scanner.nextLine();
 
         if (enregistrer.equalsIgnoreCase("y")) {

            Project project = new Project(marginPercentage , coutTotalFinal ,tva);

            boolean updateProject = projetrepository.updateProject(project , projetID);

            Devis save = new Devis(coutTotalFinal , dateEmission , dateValidite , "false");

             System.out.println("Devis enregistré avec succès !");
         } else {
             System.out.println("Enregistrement du devis annulé.");
         }
 
         System.out.println("--- Fin du projet ---");
    }
}
