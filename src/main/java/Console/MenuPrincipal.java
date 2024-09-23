package Console;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.*;
import service.ClientService;
import service.DevisService;
import service.MainOeuvreService;
import service.MateriauxService;
import service.ProjectService;
import validation.Validation;
import config.DatabaseConnection;

public class MenuPrincipal {

    private static Scanner scanner;
    private Connection connection;
    private MenuComposants menucomposants;
    private ProjectService projectService;
    private MateriauxService materiauxService;
    private MainOeuvreService mainOeuvreService;
    private DevisService devisService;
    private ClientService clientService;
    private Validation validation;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.projectService = new ProjectService(connection);
        this.materiauxService = new MateriauxService(connection);
        this.mainOeuvreService = new MainOeuvreService(connection);
        this.devisService = new DevisService(connection);
        this.clientService = new ClientService(connection);
        this.validation = new Validation();
        this.menucomposants = new MenuComposants(connection, projectService, materiauxService, mainOeuvreService,
                devisService, validation);

    }

    public void MenuProjet() {
        while (true) {
            System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = validation.getChoice();

            switch (choice) {
                case 1:
                    manageClient();
                    break;
                case 2:
                    displayProjects();
                    break;
                case 3:
                    calculateProjectCost();
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void manageClient() {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");

        int clientChoice = validation.getChoice();
        switch (clientChoice) {
            case 1:
                searchExistingClient();
                break;
            case 2:
                addNewClient();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                manageClient();
        }
    }

    private void searchExistingClient() {

        System.out.println("--- Recherche de client existant ---");

        String clientName = validation.getValidclient();

        Client client = clientService.getClientByName(clientName);

        if (client != null) {
            System.out.println("Client trouvé !");
            ClientTrouve(client.getNom(), client.getAdresse(), client.getTelephone() , client.isEstProfessionnel());

            createNewProject(client.getClientID());

        } else {
            System.out.println("Client non trouvé.");
            manageClient();
        }

    }

    private void addNewClient() {
        System.out.println("--- Ajout d'un nouveau client ---");
        
        String clientName = validation.getValidclient();
        
        String address = validation.getValidAddress();
        String phone = validation.getValidPhoneNumber();

        boolean isEstProfessionnel = validation.isEstProfessionnel();

        ClientTrouve(clientName, address, phone , isEstProfessionnel);

        Client client = new Client(clientName, address, phone, isEstProfessionnel);

        int idClient = clientService.saveClient(client);

        System.out.println("Nouveau client ajouté avec succès !");

        createNewProject(idClient);

    }

    public void ClientTrouve(String clientName, String address, String phone , boolean isEstProfessionnel) {

        System.out.println("\nNom : " + clientName);
        System.out.println("Adresse : " + address);
        System.out.println("Numéro de téléphone : " + phone);
        System.out.println("is Est Professionnel : " + (isEstProfessionnel ? "oui \n" : "non \n" ));

        System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
        String proceed = scanner.nextLine();

        if ("y".equalsIgnoreCase(proceed)) {
            System.out.println("Vous continuez avec " + clientName);

        } else {
            System.out.println("Retour à la gestion des clients.");
            manageClient();
        }

    }

    public void createNewProject(int idClient) {

        System.out.println("--- Création d'un Nouveau Projet ---");

        String projectName = validation.getValidInput("projet");
        double surfaceArea = validation.getValidSurfaceArea();

        Client client = clientService.getClientById(idClient);

        Project project = new Project(projectName, surfaceArea, client);

        menucomposants.Materiau(project);

    }

    private void displayProjects() {
        HashMap<Integer, Project> projects = projectService.getAllProjects();

        System.out.printf(" %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "Nom Projet", "Marge Beneficiaire", "Cout Total", "Surface Cuisine", "TVA", "Client",
                "Telephone de Client", "address de Client");

        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Project project : projects.values()) {
            System.out.printf(" %-20s %-20.2f %-20.2f %-20.2f %-20.2f %-20s %-20s %-10s\n",
                    project.getNomProjet(),
                    project.getMargeBeneficiaire(),
                    project.getCoutTotal(),
                    project.getSurfaceCuisine(),
                    project.getTVA(),
                    project.getClient().getNom(),
                    project.getClient().getTelephone(),
                    project.getClient().getAdresse()

            );

            // Fetch and display Devis
            Devis devis = devisService.getDevisByProjectID(project.getProjetID());
            if (devis != null) {
                System.out.println("\n  Devis:");
                System.out.printf("   -  Montant Estimé: %.2f, Date d'Émission: %s, Date Validite: %s, Accepté: %b\n",

                        devis.getMontantEstime(),
                        devis.getDateEmission(),
                        devis.getDateValidite() != null ? devis.getDateValidite() : "N/A",
                        devis.isAccepte());
            } else {
                System.out.println("  Devis: Aucun devis disponible.");
            }

            // Fetch and display Materiaux
            Map<Integer, Materiaux> materiaux = materiauxService.getMateriauxByProjectId(project.getProjetID());
            System.out.println("  Materiaux:");
            for (Materiaux mat : materiaux.values()) {
                System.out.printf(
                        "   -  Nom: %s, Cout Unitaire: %.2f, Quantite: %.2f, Type: %s, Cout Transport: %.2f, Coefficient Qualite: %.2f\n",

                        mat.getNom(),
                        mat.getCoutUnitaire(),
                        mat.getQuantite(),
                        mat.getTypeComposant().name(),
                        mat.getCoutTransport(),
                        mat.getCoefficientQualite());
            }

            // Fetch and display MainOeuvre
            Map<Integer, MainOeuvre> mainOeuvre = mainOeuvreService.getMainOeuvreByProjectId(project.getProjetID());
            System.out.println("  Main Oeuvre:");
            for (MainOeuvre mo : mainOeuvre.values()) {
                System.out.printf(
                        "   -  Nom: %s, Cout Unitaire: %.2f, Quantite: %.2f, Type: %s, Type Main Oeuvre: %s, Taux Horaire: %.2f, Heures Travail: %.2f, Productivite: %.2f\n",

                        mo.getNom(),
                        mo.getCoutUnitaire(),
                        mo.getQuantite(),
                        mo.getTypeComposant().name(),
                        mo.getMainOeuvreType(),
                        mo.getTauxHoraire(),
                        mo.getHeuresTravail(),
                        mo.getProductiviteOuvrier());
            }

            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private void calculateProjectCost() {

        List<Project> projects = projectService.getProjectsNoCout();

        if (projects.size() > 0) {
            projectService.displayProjectsInTable(projects);

            System.out.print("Entrez l'ID du projet pour calculer son coût : ");
            int projectId = validation.getValidProjectId();

            menucomposants.coutTotal(projectId);
        } else {
            System.out.println("Aucun projet disponible.");
        }

    }

}
