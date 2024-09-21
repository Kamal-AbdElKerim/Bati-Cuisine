package validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    private Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public Validation() {
        this.scanner = new Scanner(System.in);
    }


    public  int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public String getValidInput(String validName) {
        scanner.nextLine();
        while (true) {
            System.out.print("Entrez le nom du " + validName + ": ");
            String name = scanner.nextLine().trim(); // This reads the input.
            if (!name.isEmpty()) {
                return name;
            }
            System.out.println(validName + " cannot be empty. Please try again.");
        }
    }
    

    public String getValidAddress() {
        while (true) {
            System.out.print("Entrez l'adresse du client : ");
            String address = scanner.nextLine().trim();
            if (!address.isEmpty()) {
                return address;
            }
            System.out.println("Address cannot be empty. Please try again.");
        }
    }

    public String getValidPhoneNumber() {
        while (true) {
            System.out.print("Entrez le numéro de téléphone du client :");
            String phoneNumber = scanner.nextLine().trim();
            if (isValidPhoneNumber(phoneNumber)) {
                return phoneNumber;
            }
            System.out.println("Invalid phone number format. Please try again.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+?[0-9]{10,15}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }

    public double getValidSurfaceArea() {
        while (true) {
            System.out.print("Entrez la surface de la cuisine (en m²) : ");
            String input = scanner.nextLine().trim(); // Read and trim the input
    
            // Check if input is empty
            if (input.isEmpty()) {
                System.out.println("Surface area cannot be empty. Please try again.");
                continue;
            }
    
            // Check if input is a valid numeric value
            try {
                double surfaceArea = Double.parseDouble(input);
                if (surfaceArea > 0) {
                    return surfaceArea; // Return if valid and positive
                } else {
                    System.out.println("Surface area must be positive. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }
    

    public  int getValidProjectId() {
        while (true) {
            System.out.print("Entrez l'ID du projet pour calculer son coût : ");
            if (scanner.hasNextInt()) {
                int projectId = scanner.nextInt();
                scanner.nextLine(); 
                if (projectId > 0) {
                    return projectId;
                } else {
                    System.out.println("L'ID du projet doit être un entier positif. Veuillez réessayer.");
                }
            } else {
                System.out.println("Entrée invalide. Veuillez entrer un entier valide.");
                scanner.nextLine(); 
            }
        }
    }

    public double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim(); // Read the input as a string and trim whitespace
    
            // Check if the input is empty
            if (input.isEmpty()) {
                System.out.println("L'entrée ne peut pas être vide. Veuillez réessayer.");
                continue;
            }
    
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value; // Return the value if it's valid and positive
                } else {
                    System.out.println("La valeur doit être un nombre positif. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre valide.");
            }
        }
    }
    

    public  String getValidStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            } else {
                System.out.println("le type is empty.");
            }
        }
    }

    public  LocalDate getValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String inputDate = scanner.nextLine().trim();

            try {
                // Try to parse the date
                LocalDate date = LocalDate.parse(inputDate, DATE_FORMATTER);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide. Veuillez entrer la date au format jj/mm/aaaa.");
            }
        }
    }
    

}
