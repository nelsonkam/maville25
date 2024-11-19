package cli;

import models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;
    private final ApiClient apiClient;

    public MenuHandler(Scanner scanner, ApiClient apiClient) {
        this.scanner = scanner;
        this.apiClient = apiClient;
    }

    public void showMenu(User user) {
        if (user instanceof Resident) {
            showResidentMenu();
        } else if (user instanceof Intervenant) {
            showIntervenantMenu();
        }
    }

    private void showResidentMenu() {
        System.out.println("\nMenu Résident:");
        System.out.println("1. Voir les travaux en cours");
        System.out.println("2. Filtrer les travaux par arrondissement");
        System.out.println("3. Filtrer les travaux par type");
        System.out.println("4. Voir les entraves routières");
        System.out.println("5. Chercher les entraves par rue");
        System.out.println("6. Soumettre une demande de travaux");
        System.out.println("7. Voir mes demandes de travaux");
        System.out.println("0. Se déconnecter");
    }

    private void showIntervenantMenu() {
        System.out.println("\nMenu Intervenant:");
        System.out.println("1. Voir les demandes de travaux disponibles");
        System.out.println("2. Voir toutes les demandes de travaux");
        System.out.println("0. Se déconnecter");
    }

    public void handleMenuChoice(User user, int choice) {
        if (user instanceof Resident) {
            handleResidentChoice(user, choice);
        } else if (user instanceof Intervenant) {
            handleIntervenantChoice(choice);
        }
    }

    private void handleResidentChoice(User user, int choice) {
        Resident resident = (Resident) user;
        try {
            switch (choice) {
                case 0 -> handleLogout();
                case 1 -> viewCurrentWorks(null, null);
                case 2 -> {
                    System.out.print("Entrez le nom de l'arrondissement: ");
                    String borough = scanner.nextLine();
                    viewCurrentWorks(borough, null);
                }
                case 3 -> {
                    System.out.print("Entrez le type de travaux: ");
                    String type = scanner.nextLine();
                    viewCurrentWorks(null, type);
                }
                case 4 -> {
                    System.out.print("Entrez l'ID du travail (ou Entrée pour voir tous): ");
                    String workId = scanner.nextLine();
                    viewRoadImpacts(workId.isEmpty() ? null : workId, null);
                }
                case 5 -> {
                    System.out.print("Entrez le nom de la rue: ");
                    String streetName = scanner.nextLine();
                    viewRoadImpacts(null, streetName);
                }
                case 6 -> submitWorkRequest(resident);
                case 7 -> viewResidentWorkRequests(resident);
                default -> System.out.println("Option invalide");
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void handleIntervenantChoice(int choice) {
        try {
            switch (choice) {
                case 0 -> handleLogout();
                case 1 -> viewAvailableWorkRequests();
                case 2 -> viewAllWorkRequests();
                default -> System.out.println("Option invalide");
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void viewAllWorkRequests() {
        try {
            List<WorkRequest> requests = apiClient.getAllWorkRequests();
            if (requests.isEmpty()) {
                System.out.println("Aucune demande de travaux trouvée");
                return;
            }
            
            System.out.println("\nToutes les demandes de travaux:");
            for (WorkRequest request : requests) {
                System.out.println("\n" + request.toString());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des demandes: " + e.getMessage());
        }
    }

    private void handleLogout() {
        System.out.println("Déconnexion réussie");
        throw new LogoutException();
    }

    private void viewCurrentWorks(String borough, String type) throws Exception {
        List<ConstructionWork> works = apiClient.getCurrentWorks(borough, type);
        if (works.isEmpty()) {
            System.out.println("Aucun travaux trouvé");
            return;
        }
        
        System.out.println("\nTravaux en cours:");
        for (ConstructionWork work : works) {
            System.out.println("\n" + work.toString());
        }
    }

    private void viewRoadImpacts(String workId, String streetName) throws Exception {
        List<RoadImpact> impacts = apiClient.getRoadImpacts(workId, streetName);
        if (impacts.isEmpty()) {
            System.out.println("Aucune entrave trouvée");
            return;
        }
        
        System.out.println("\nEntraves routières:");
        for (RoadImpact impact : impacts) {
            System.out.println("\n" + impact.toString());
        }
    }

    private void submitWorkRequest(Resident resident) {
        try {
            System.out.println("\nSoumettre une nouvelle demande de travaux");
            
            System.out.print("Titre: ");
            String title = scanner.nextLine();
            
            System.out.print("Description: ");
            String description = scanner.nextLine();
            
            System.out.println("Type de travaux:");
            System.out.println("1. Voirie");
            System.out.println("2. Aqueduc");
            System.out.println("3. Égouts");
            System.out.println("4. Éclairage public");
            System.out.println("5. Signalisation");
            System.out.println("6. Autre");
            System.out.print("Choisissez une option: ");
            
            String workType = switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> "Voirie";
                case 2 -> "Aqueduc";
                case 3 -> "Égouts";
                case 4 -> "Éclairage public";
                case 5 -> "Signalisation";
                case 6 -> "Autre";
                default -> throw new IllegalArgumentException("Option invalide");
            };
            
            System.out.print("Date de début souhaitée (JJ/MM/AAAA): ");
            LocalDate startDate = LocalDate.parse(
                scanner.nextLine(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            
            WorkRequest request = new WorkRequest(
                title, description, workType, startDate, resident.getEmail()
            );
            
            apiClient.submitWorkRequest(request);
            System.out.println("Demande soumise avec succès!");
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la soumission: " + e.getMessage());
        }
    }

    private void viewResidentWorkRequests(Resident resident) {
        try {
            List<WorkRequest> requests = apiClient.getResidentWorkRequests(resident.getEmail());
            if (requests.isEmpty()) {
                System.out.println("Vous n'avez aucune demande de travaux");
                return;
            }
            
            System.out.println("\nVos demandes de travaux:");
            for (WorkRequest request : requests) {
                System.out.println("\n" + request.toString());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des demandes: " + e.getMessage());
        }
    }

    private void viewAvailableWorkRequests() {
        try {
            List<WorkRequest> requests = apiClient.getResidentWorkRequests(null);
            if (requests.isEmpty()) {
                System.out.println("Aucune demande de travaux disponible");
                return;
            }
            
            System.out.println("\nDemandes de travaux disponibles:");
            for (WorkRequest request : requests) {
                System.out.println("\n" + request.toString());
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}
