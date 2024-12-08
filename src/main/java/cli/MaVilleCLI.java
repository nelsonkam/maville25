package cli;

import models.Intervenant;
import models.Resident;
import models.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Cette classe représente l'interface en ligne de commande (CLI) de l'application MaVille.
 */
public class MaVilleCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ApiClient apiClient = new ApiClient();
    private static User currentUser = null;

    /**
     * Point d'entrée principal de l'application MaVille (CLI).
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'application MaVille (CLI)");

        while (true) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    try {
                        menuHandler.showMenu(currentUser);
                        handleUserChoice();
                    } catch (LogoutException e) {
                        currentUser = null;
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
    }

    /**
     * Affiche le menu d'authentification.
     *
     * @throws Exception Si une erreur survient lors de l'authentification.
     */
    private static void showLoginMenu() throws Exception {
        System.out.println("\nMenu d'authentification:");
        System.out.println("1. Se connecter en tant que résident");
        System.out.println("2. Se connecter en tant qu'intervenant");
        System.out.println("3. S'inscrire en tant que résident");
        System.out.println("4. S'inscrire en tant qu'intervenant");
        System.out.println("5. Quitter");
        System.out.print("Choisissez une option: ");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> loginResident();
            case 2 -> loginIntervenant();
            case 3 -> registerResident();
            case 4 -> registerIntervenant();
            case 5 -> {
                System.out.println("Au revoir!");
                System.exit(0);
            }
            default -> System.out.println("Option invalide");
        }
    }

    /**
     * Authentifie un résident.
     *
     * @throws Exception Si une erreur survient lors de l'authentification.
     */
    private static void loginResident() throws Exception {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();

        currentUser = apiClient.loginResident(email, password);
        System.out.println("Connexion réussie!");
    }

    /**
     * Authentifie un intervenant.
     *
     * @throws Exception Si une erreur survient lors de l'authentification.
     */
    private static void loginIntervenant() throws Exception {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();

        currentUser = apiClient.loginIntervenant(email, password);
        System.out.println("Connexion réussie!");
    }

    /**
     * Inscrit un nouveau résident.
     *
     * @throws Exception Si une erreur survient lors de l'inscription.
     */
    private static void registerResident() throws Exception {
        System.out.print("Nom: ");
        String name = scanner.nextLine();

        LocalDate dateOfBirth = null;
        while (dateOfBirth == null) {
            System.out.print("Date de naissance (JJ/MM/AAAA): ");
            try {
                dateOfBirth = LocalDate.parse(
                    scanner.nextLine(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                );
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide");
            }
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();
        System.out.print("Téléphone (optionnel): ");
        String phone = scanner.nextLine();
        System.out.print("Adresse: ");
        String address = scanner.nextLine();

        Resident resident = phone.isEmpty() ?
            new Resident(name, dateOfBirth, email, password, address) :
            new Resident(name, dateOfBirth, email, password, phone, address);

        apiClient.registerResident(resident);
        System.out.println("Inscription réussie!");
    }

    /**
     * Inscrit un nouvel intervenant.
     *
     * @throws Exception Si une erreur survient lors de l'inscription.
     */
    private static void registerIntervenant() throws Exception {
        System.out.print("Nom: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();

        System.out.println("Type d'intervenant:");
        System.out.println("1. Entreprise publique");
        System.out.println("2. Entrepreneur privé");
        System.out.println("3. Particulier");
        System.out.print("Choisissez une option: ");
        
        String type = switch (Integer.parseInt(scanner.nextLine())) {
            case 1 -> "Entreprise publique";
            case 2 -> "Entrepreneur privé";
            case 3 -> "Particulier";
            default -> throw new IllegalArgumentException("Type invalide");
        };

        String cityIdentifier;
        do {
            System.out.print("Identifiant ville (8 chiffres): ");
            cityIdentifier = scanner.nextLine();
        } while (!cityIdentifier.matches("\\d{8}"));

        Intervenant intervenant = new Intervenant(name, email, password, type, cityIdentifier);
        apiClient.registerIntervenant(intervenant);
        System.out.println("Inscription réussie!");
    }

    private static final MenuHandler menuHandler = new MenuHandler(scanner, apiClient);

    /**
     * Gère le choix de l'utilisateur dans le menu.
     */
    private static void handleUserChoice() {
        System.out.print("Choisissez une option: ");
        int choice = Integer.parseInt(scanner.nextLine());
        
        if (choice == 0) {
            currentUser = null;
            System.out.println("Déconnexion réussie");
        } else {
            menuHandler.handleMenuChoice(currentUser, choice);
        }
    }
}