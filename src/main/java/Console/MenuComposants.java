package Console;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

import model.*;
import service.DevisService;
import service.MainOeuvreService;
import service.MateriauxService;
import service.ProjectService;
import validation.*;

public class MenuComposants {
    private Scanner scanner;
    private Validation validation;
    private ProjectService projectService;
    private MateriauxService materiauxService;
    private MainOeuvreService mainOeuvreService;
    private DevisService devisService;

    public MenuComposants(Connection connection, ProjectService projectService, MateriauxService materiauxService,
            MainOeuvreService mainOeuvreService, DevisService devisService, Validation validation) {
        this.scanner = new Scanner(System.in);
        this.projectService = projectService;
        this.materiauxService = materiauxService;
        this.mainOeuvreService = mainOeuvreService;
        this.devisService = devisService;
        this.validation = validation;

    }

    public void Materiau(Project project) {

        HashMap<String, Materiaux> materiauxs = new HashMap<>();
        HashMap<String, MainOeuvre> MainOeuvres = new HashMap<>();
        String continueMaterial;
        do {
            System.out.println("--- Ajout des matériaux ---");
            String materialName = validation.getValidInput("matériau");
            double materialQuantity = validation.getValidDoubleInput("Entrez la quantité de ce matériau (en m²) : ");

            double materialUnitCost = validation
                    .getValidDoubleInput("Entrez le coût unitaire de ce matériau ($/m²) : ");
            double materialTransportCost = validation
                    .getValidDoubleInput("Entrez le coût de transport de ce matériau ($) : ");

            double materialQualityCoefficient = validation.getValidDoubleInput(
                    "Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");

            double tauxTVA = validation.getValidDoubleInput("Souhaitez-vous appliquer une TVA (en %) : ");

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
            String MainOeuvreName = validation.getValidInput("MainOeuvre");
            double MainOeuvreQuantity = validation
                    .getValidDoubleInput("Entrez la quantité de ce MainOeuvre (en m²) : ");
            double MainOeuvreUnitCost = validation
                    .getValidDoubleInput("Entrez le coût unitaire de ce MainOeuvre ($/m²) : ");
            scanner.nextLine();

            String MainOeuvreType = validation
                    .getValidStringInput("Entrez le type de main-d'oeuvre (e.g., Ouvrier de base, Spécialiste) : ");
            double tauxHoraire = validation
                    .getValidDoubleInput("Entrez le taux horaire de cette main-d'oeuvre ($/h) : ");
            double heuresTravail = validation.getValidDoubleInput("Entrez le nombre d'heures travaillées : ");
            double MainOeuvreProductivityFactor = validation.getValidDoubleInput(
                    "Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
            double tauxTVA = validation.getValidDoubleInput("Souhaitez-vous appliquer une TVA : ");
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
        int projetID = projectService.createProject(project);
        try {
            materiauxService.insertMateriauxData(materiauxs, projetID);
            mainOeuvreService.insertMainOeuvreData(MainOeuvres, projetID);
            coutTotal(projetID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void coutTotal(int projetID) {
        Project Project = projectService.getProjectById(projetID);
        if (Project == null) {
            System.out.println("Le projet avec l'ID " + projetID + " n'existe pas.");
            return;
        }
        System.out.println("--- Calcul du coût total ---");
        System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
        boolean applyVAT = scanner.nextLine().trim().equalsIgnoreCase("y");
        double TVAPercentage = 0.0;
        if (applyVAT) {
            TVAPercentage = validation.getValidDoubleInput("Entrez le pourcentage de TVA (%) : ");
            scanner.nextLine();
        }

        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        boolean applyMargin = scanner.nextLine().trim().equalsIgnoreCase("y");
        double marginPercentage = 0.0;
        if (applyMargin) {
            marginPercentage = validation.getValidDoubleInput("Entrez le pourcentage de marge bénéficiaire (%) : ");
            scanner.nextLine();
        }

        System.out.println("Calcul du coût en cours...");

        Map<Integer, Materiaux> materiaux = materiauxService.getMateriauxByProjectId(projetID);
        Map<Integer, MainOeuvre> mainOeuvre = mainOeuvreService.getMainOeuvreByProjectId(projetID);

        // Display Project Details
        System.out.println("--- Résultat du Calcul ---");
        System.out.printf("Nom du projet : %s\n", Project.getNomProjet());
        System.out.printf("Client : %s\n", Project.getClient().getNom());
        System.out.printf("Adresse du chantier : %s\n", Project.getClient().getAdresse());
        System.out.printf("Surface : %.2f m²\n\n", Project.getSurfaceCuisine());

        calculerCoutDetaille(materiaux, mainOeuvre, TVAPercentage, marginPercentage, projetID);

    }

    public void calculerCoutDetaille(Map<Integer, Materiaux> materiauxMap,
            Map<Integer, MainOeuvre> mainOeuvreMap, double tva, double marginPercentage, int projetID) {

        // for (Materiaux materiau : materiauxMap.values()) {
        // double coutMateriau = materiau.calculerCout();
        // totalMateriauxAvantTVA += coutMateriau;

        // System.out.printf(
        // "\n- %s : %.2f $ (quantité : %.2f, coût unitaire : %.2f $/m², qualité : %.2f,
        // transport : %.2f $)\n",
        // materiau.getNom(), coutMateriau, materiau.getQuantite(),
        // materiau.getCoutUnitaire(),
        // materiau.getCoefficientQualite(), materiau.getCoutTransport());
        // }

        double totalMateriauxAvantTVA = materiauxMap.values().stream()
                .mapToDouble(Materiaux::calculerCout)
                .sum();

        System.out.println("--- Détail des Coûts ---");
        System.out.println("\n1. Matériaux :\n");
        materiauxMap.values().forEach(materiau -> {
            double coutMateriau = materiau.calculerCout();
            System.out.printf(
                    "\n- %s : %.2f $ (quantité : %.2f, coût unitaire : %.2f $/m², qualité : %.2f, transport : %.2f $)\n",
                    materiau.getNom(), coutMateriau, materiau.getQuantite(), materiau.getCoutUnitaire(),
                    materiau.getCoefficientQualite(), materiau.getCoutTransport());
        });

        double totalMateriauxAvecTVA = totalMateriauxAvantTVA * (1 + tva / 100);

        System.out.printf("\nCoût total des matériaux avant TVA : %.2f $\n", totalMateriauxAvantTVA);
        System.out.printf("\nCoût total des matériaux avec TVA (%.0f%%) : %.2f $\n", tva, totalMateriauxAvecTVA);

        double totalMainOeuvreAvantTVA = mainOeuvreMap.values().stream()
                .mapToDouble(MainOeuvre::calculerCout)
                .sum();

        System.out.println("\n2. Main-d'oeuvre :\n");
        mainOeuvreMap.values().forEach(mainOeuvre -> {
            double coutMainOeuvre = mainOeuvre.calculerCout();
            System.out.printf(
                    "\n- %s : %.2f $ (taux horaire : %.2f $/h, heures travaillées : %.2f h, productivité : %.2f)\n",
                    mainOeuvre.getNom(), coutMainOeuvre, mainOeuvre.getTauxHoraire(),
                    mainOeuvre.getHeuresTravail(), mainOeuvre.getProductiviteOuvrier());
        });

        double totalMainOeuvreAvecTVA = totalMainOeuvreAvantTVA * (1 + tva / 100);

        System.out.printf("\nCoût total de la main-d'oeuvre avant TVA : %.2f $\n", totalMainOeuvreAvantTVA);
        System.out.printf("\nCoût total de la main-d'oeuvre avec TVA (%.0f%%) : %.2f $\n", tva, totalMainOeuvreAvecTVA);

        double totalAvantMarge = totalMateriauxAvecTVA + totalMainOeuvreAvecTVA;
        System.out.printf("\n3. Coût total avant marge : %.2f $\n", totalAvantMarge);

        double margeBeneficiaire = totalAvantMarge * marginPercentage / 100;
        double coutTotalFinal = totalAvantMarge + margeBeneficiaire;

        System.out.printf("\n4. Marge bénéficiaire (%.0f%%) : %.2f $\n", marginPercentage, margeBeneficiaire);
        System.out.printf("\n**Coût total final du projet : %.2f $**\n", coutTotalFinal);

        EnregistrerDevis(projetID, margeBeneficiaire, coutTotalFinal, tva);
    }

    public void EnregistrerDevis(int projetID, double margeBeneficiaire, double coutTotalFinal, double tva) {
        System.out.println("--- Enregistrement du Devis ---");

        // Validation des dates
        LocalDate dateEmission = validation.getValidDate("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
        LocalDate dateValidite = validation
                .getValidDate("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");

        while (dateEmission.isAfter(dateValidite)) {
            System.out.println("La date d'émission ne peut pas être après la date de validité. Veuillez réessayer.");
            dateValidite = validation.getValidDate("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
        }

        System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
        String enregistrer = scanner.nextLine();

        if (enregistrer.equalsIgnoreCase("y")) {

            Project project = new Project(margeBeneficiaire, coutTotalFinal, tva);
            boolean updateProject = projectService.updateProject(project, projetID);

            if (updateProject) {
                Project projectByID = projectService.getProjectById(projetID);
                Devis devis = new Devis(coutTotalFinal, dateEmission, dateValidite, false, projectByID);

                System.out.println("Devis enregistré avec succès !");

                System.out.printf("Montant estime : %.2f $\n", devis.getMontantEstime());
                System.out.printf("Date d'émission : %s\n", devis.getDateEmission());
                System.out.printf("Date de validité : %s\n", devis.getDateValidite());

                System.out.println("acceptez-vous ou refusez-vous un devis ? (y/n) :");
                String accepterRefuser = scanner.nextLine();
                if (accepterRefuser.equalsIgnoreCase("y")) {
                    devis = new Devis(coutTotalFinal, dateEmission, dateValidite, true, projectByID);
                    devisService.saveDevis(devis);
                    System.out.println("Devis accepté avec succès!");
                } else {
                    devis = new Devis(coutTotalFinal, dateEmission, dateValidite, false, projectByID);
                    devisService.saveDevis(devis);
                    System.out.println("Devis refusé avec succès!");
                }
            }
        } else {

            System.out.println("Enregistrement du devis annulé.");
        }

        System.out.println("--- Fin du projet ---");
    }

}
