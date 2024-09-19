package controller;

import java.sql.Connection;
import java.util.Scanner;

import model.*;
import Repository.DAO.*;
import config.DatabaseConnection;

public class MenuPrincipal {

    private static Scanner scanner;
    private ClientRepository clientRepository;
    private Connection connection;
    private MenuComposants menucomposants;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.clientRepository = new ClientRepository(connection);
        this.menucomposants = new MenuComposants(connection);

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
            int choice = getChoice();

            switch (choice) {
                case 1:
                    manageClient();
                    break;
                case 2:
                    // displayProjects();
                    break;
                case 3:
                    // calculateProjectCost();
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void manageClient() {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");

        int clientChoice = getChoice();
        switch (clientChoice) {
            case 1:
                searchExistingClient();
                break;
            case 2:
                addNewClient();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                manageClient(); // Recursively call until a valid choice is made
        }
    }

    private void searchExistingClient() {
        scanner.nextLine();
        System.out.println("--- Recherche de client existant ---");
        System.out.print("Entrez le nom du client : ");
        String clientName = scanner.nextLine();

        Client client = clientRepository.findByName(clientName);
        System.out.println(client);

        if (client != null) {
            System.out.println("Client trouvé !");
            ClientTrouve(client.getNom(), client.getAdresse(), client.getTelephone());

            createNewProject(client.getClientID());

        } else {
            System.out.println("Client non trouvé.");
            manageClient();
        }

    }

    private void addNewClient() {
        scanner.nextLine();
        System.out.println("--- Ajout d'un nouveau client ---");
        System.out.print("Entrez le nom du client : ");
        String clientName = scanner.nextLine();
        System.out.print("Entrez l'adresse du client : ");
        String address = scanner.nextLine();
        System.out.print("Entrez le numéro de téléphone du client : ");
        String phone = scanner.nextLine();

        ClientTrouve(clientName, address, phone);

        Client client = new Client(clientName, address, phone, false);

        int idClient = clientRepository.save(client);

        System.out.println("Nouveau client ajouté avec succès !");

        createNewProject(idClient);

    }

    public void ClientTrouve(String clientName, String address, String phone) {

        System.out.println("Nom : " + clientName);
        System.out.println("Adresse : " + address);
        System.out.println("Numéro de téléphone : " + phone);

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
        System.out.print("Entrez le nom du projet : ");
        String projectName = scanner.nextLine();
        System.out.print("Entrez la surface de la cuisine (en m²) : ");
        double surfaceArea = scanner.nextDouble();
        scanner.nextLine();

        Client client = clientRepository.findById(idClient);

        System.out.println(client);

        Project project = new Project(projectName, surfaceArea, client);
        System.out.println(project);

        menucomposants.Materiau( project);

    }

}
