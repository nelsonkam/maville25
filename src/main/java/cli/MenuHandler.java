package cli;

import models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import api.services.CandidatureService;
import api.services.NotificationService;
import api.services.ProjectService;
import api.repositories.CandidatureRepository;
import api.repositories.IntervenantRepository;
import api.repositories.NotificationRepository;
import api.repositories.ProjectRepository;
import api.repositories.WorkRequestRepository;
import api.DatabaseManager;

/**
 * Cette classe gère les menus et les interactions utilisateur pour l'application MaVille.
 */
public class MenuHandler {
    private final Scanner scanner;
    private final ApiClient apiClient;
    private final CandidatureService candidatureService;
    private final ProjectService projectService;

    /**
     * Constructeur de la classe MenuHandler.
     *
     * @param scanner Le scanner pour lire les entrées utilisateur.
     * @param apiClient Le client API pour les appels réseau.
     */
    public MenuHandler(Scanner scanner, ApiClient apiClient) {
        this.scanner = scanner;
        this.apiClient = apiClient;
        DatabaseManager db = DatabaseManager.getInstance();
        CandidatureRepository candidatureRepo = new CandidatureRepository(db);
        WorkRequestRepository workRequestRepo = new WorkRequestRepository(db);
        IntervenantRepository intervenantRepo = new IntervenantRepository(db);
        ProjectRepository projectRepo = new ProjectRepository(db);
        this.candidatureService = new CandidatureService(candidatureRepo, workRequestRepo, intervenantRepo);
        this.projectService = new ProjectService(projectRepo);
    }

    /**
     * Affiche le menu en fonction du type d'utilisateur.
     *
     * @param user L'utilisateur courant.
     */
    public void showMenu(User user) {
        if (user instanceof Resident) {
            showResidentMenu(user);
        } else if (user instanceof Intervenant) {
            showIntervenantMenu();
        }
    }

    /**
     * Affiche le menu pour les résidents.
     * 
     * @param user L'utilisateur courant.
     */
    private void showResidentMenu(User user) {
        // Afficher le nombre de notifications non lues
        try {
            List<Notification> unreadNotifications = apiClient.getUnreadNotifications(user.getEmail());
            if (!unreadNotifications.isEmpty()) {
                System.out.println("\n[" + unreadNotifications.size() + " nouvelle(s) notification(s)]");
            }
        } catch (Exception e) {
            System.out.println("\n[Erreur lors de la récupération des notifications]");
        }

        System.out.println("\nMenu Résident:");
        System.out.println("1. Voir les travaux en cours");
        System.out.println("2. Filtrer les travaux par arrondissement");
        System.out.println("3. Filtrer les travaux par type");
        System.out.println("4. Voir les entraves routières");
        System.out.println("5. Chercher les entraves par rue");
        System.out.println("6. Soumettre une demande de travaux");
        System.out.println("7. Voir mes demandes de travaux");
        System.out.println("8. Voir mes notifications");
        System.out.println("9. Suivi de candidatures pour mes travaux");
        System.out.println("0. Se déconnecter");
    }

    /**
     * Affiche le menu pour les intervenants.
     */
    private void showIntervenantMenu() {
        System.out.println("\nMenu Intervenant:");
        System.out.println("1. Voir les demandes de travaux disponibles");
        System.out.println("2. Voir toutes les demandes de travaux");
        System.out.println("3. Soumettre une candidature");
        System.out.println("4. Retirer une candidature");
        System.out.println("5. Suivre mes candidatures");
        System.out.println("6. Soumettre un projet");
        System.out.println("7. Voir les projets/Mise à jour d'un projet");
        System.out.println("0. Se déconnecter");
    }

    /**
     * Gère le choix du menu en fonction du type d'utilisateur.
     *
     * @param user L'utilisateur courant.
     * @param choice Le choix de l'utilisateur.
     */
    public void handleMenuChoice(User user, int choice) {
        if (user instanceof Resident) {
            handleResidentChoice(user, choice);
        } else if (user instanceof Intervenant) {
            handleIntervenantChoice((Intervenant) user, choice);
        }
    }

    /**
     * Gère les choix du menu pour les résidents.
     *
     * @param user Le résident courant.
     * @param choice Le choix du résident.
     */
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
                case 8 -> viewNotifications(resident);
                case 9 -> followCandidatures(resident);
                default -> System.out.println("Option invalide");
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    /**
     * Gère les choix du menu pour les intervenants.
     *
     * @param intervenant L'intervenant courant.
     * @param choice Le choix de l'intervenant.
     */
    private void handleIntervenantChoice(Intervenant intervenant, int choice) {
        try {
            switch (choice) {
                case 0 -> handleLogout();
                case 1 -> viewAvailableWorkRequests();
                case 2 -> viewAllWorkRequests();
                case 3 -> submitCandidature(intervenant);
                case 4 -> withdrawCandidature(intervenant);
                case 5 -> followCandidatures(intervenant);
                case 6 -> addProject();
                case 7 -> viewAndUpdateProject();
                default -> System.out.println("Option invalide");
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    /**
     * Affiche toutes les demandes de travaux.
     */
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

    /**
     * Soumet une candidature pour une demande de travaux.
     *
     * @param intervenant L'intervenant soumettant la candidature.
     */
    private void submitCandidature(Intervenant intervenant) {

        viewAllWorkRequests();

        System.out.print("Entrez l'ID de la WorkRequest: ");
        String input = scanner.nextLine();
        long workRequestId;
        try {
            workRequestId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return;
        }

        try {
            candidatureService.submitCandidature(workRequestId, intervenant.getEmail());
            System.out.println("Candidature soumise avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    /**
     * Retire une candidature pour une demande de travaux.
     *
     * @param intervenant L'intervenant retirant la candidature.
     */
    private void withdrawCandidature(Intervenant intervenant) {
        System.out.print("Entrez l'ID de la WorkRequest: ");
        String input = scanner.nextLine();
        long workRequestId;
        try {
            workRequestId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return;
        }

        try {
            candidatureService.withdrawCandidature(workRequestId, intervenant.getEmail());
            System.out.println("Candidature retirée avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    /**
     * Affiche les candidatures de l'intervenant.
     *
     * @param intervenant L'intervenant courant.
     */
    private void followCandidatures(Intervenant intervenant) {
        try {
            List<Candidature> candidatures = candidatureService.getCandidaturesByIntervenant(intervenant.getEmail());
            if (candidatures.isEmpty()) {
                System.out.println("Aucune candidature trouvée.");
            } else {
                for (Candidature c : candidatures) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    /**
     * Gère la déconnexion de l'utilisateur.
     */
    private void handleLogout() {
        System.out.println("Déconnexion réussie");
        throw new LogoutException();
    }

    /**
     * Affiche les travaux en cours.
     *
     * @param borough L'arrondissement à filtrer (peut être null).
     * @param type Le type de travaux à filtrer (peut être null).
     * @throws Exception Si une erreur survient lors de la récupération des travaux.
     */
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

    /**
     * Affiche les entraves routières.
     *
     * @param workId L'ID du travail à filtrer (peut être null).
     * @param streetName Le nom de la rue à filtrer (peut être null).
     * @throws Exception Si une erreur survient lors de la récupération des entraves.
     */
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

    /**
     * Soumet une nouvelle demande de travaux.
     *
     * @param resident Le résident soumettant la demande.
     */
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

    /**
     * Affiche les demandes de travaux du résident.
     *
     * @param resident Le résident courant.
     */
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

    /**
     * Affiche les demandes de travaux disponibles.
     */
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

    private void viewNotifications(Resident resident) {
        try {
            List<Notification> notifications = apiClient.getAllNotifications(resident.getEmail());
            if (notifications.isEmpty()) {
                System.out.println("Vous n'avez aucune notification");
                return;
            }

            System.out.println("\nVos notifications:");
            for (Notification notification : notifications) {
                System.out.println("\n" + notification.toString());
            }

            // Marquer toutes les notifications comme lues
            apiClient.markNotificationsAsRead(resident.getEmail());
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des notifications: " + e.getMessage());
        }
    }

    /**
     * Affiche les candidatures pour les travaux soumis par le résident en cours.
     *
     * @param resident Le résident courant.
     */

     private void followCandidatures(Resident resident) {
        try {
            List<Candidature> candidatures = candidatureService.getCandidaturesByWorkRequestAndResidentEmail(resident.getEmail());
            
            if (candidatures.isEmpty()) {
                System.out.println("Aucune candidature trouvée.");
            } else {
                for (Candidature c : candidatures) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    /**
     * Permet d'ajouter un projet.
     *
     */
    private void addProject() {
        try{
            System.out.println("\nSoumission d'un projet");

            System.out.print("Titre: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            /* Liste non exaustive des arrondissements de Montréal */
            System.out.println("Arrondissement: ");
            System.out.println("1. Le Plateau-Mont-Royal");
            System.out.println("2. Outremont");
            System.out.println("3. Rosemont-La-Petite-Patrie");
            System.out.println("4. Verdun");
            System.out.println("5. Villeray-Saint-Michel-Parc-Extension");
            System.out.print("Choisissez une option: ");  
            
            String borough = switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> "Le Plateau-Mont-Royal";
                case 2 -> "Outremont";
                case 3 -> "Rosemont-La-Petite-Patrie";
                case 4 -> "Verdun";
                case 5 -> "Villeray-Saint-Michel-Parc-Extension";
                default -> throw new IllegalArgumentException("Option invalide");
                };

            System.out.print("Date de début désirée (JJ/MM/AAAA): ");
            LocalDate startDate = LocalDate.parse(
                scanner.nextLine(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            Project project = new Project(title, description, borough, startDate);

            apiClient.submitProject(project);
            System.out.println("Projet soumis avec succès");
            
            } catch (Exception e){
                System.out.println("Erreur lors de la soumission: " + e.getMessage());
            }
        }
    
    
    /**
     * Permet de voir les projets et mettre à jour le statut d'un projet.
     *
     */
    private void viewAndUpdateProject() {

        try{
            List<Project> projects = apiClient.getAllProjects();

            if (projects.isEmpty()) {
                System.out.println("Aucun projet existant.");
                return;
            }

            for (Project project : projects) {
                System.out.println("\n" + project.toString());
            }

            System.out.println("Pour revenir au menu précédent, appuyer <ENTRÉE>.");
            System.out.print("Pour mettre à jour un projet, saisir le id: ");
            int input = scanner.nextInt();
        
            for (Project project : projects) {
                if(project.getId() == input) {

                    if (project.getProjectStatus() == ProjectStatus.CANCELLED || project.getProjectStatus() == ProjectStatus.COMPLETED){
                        System.out.println("Le projet " + project.getTitle() + " un statut Annulé ou Complété et ne peut être changé.");
                        return;
                    }

                    System.out.println("\nProjet sélectionné: "+ project.getTitle());
                    System.out.println("1. En cours");
                    System.out.println("2. Terminé");
                    System.out.println("3. Annulé");
                    System.out.print("Nouveau statut du projet: ");
                    scanner.nextLine();

                    String status = switch (Integer.parseInt(scanner.nextLine())) {
                        case 1 -> "IN_PROGRESS";
                        case 2 -> "COMPLETED";
                        case 3 -> "CANCELLED";
                        default -> throw new IllegalArgumentException("Option invalide.");
                    };

                    project.setProjectStatus(ProjectStatus.valueOf(status));
                    System.out.println(project);
                    projectService.updateProjectStatus(project);
                }
            }
        } catch (Exception e){
            System.out.println("Erreur lors de la récupération des projets: " + e.getMessage());
        }
        
    }
}




