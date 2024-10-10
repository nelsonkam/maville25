import java.util.Scanner;
import java.util.ArrayList;

// Prototype de "MaVille, équipe 25"
// Ce programme démarre une application en ligne de commande et affiche des menus
// différents selon la connexion de l'utilisateur (citoyen ou intervenant).
// Plus tard, une intégration de base de données et des modules supplémentaires seront ajoutés.1

public class MaVille {

    // Scanner pour lire les entrées de l'utilisateur
    public Scanner scanner = new Scanner(System.in);
    public byte choix = 0; // Variable pour stocker le choix de menu de l'utilisateur

    // Menu principal affiché au démarrage
    public void menuAccueil(){
        // Logo en ASCII
        String logo = """
            ...........................................................        
            *.##...##..........##..##..######..##......##......######.*
            *.###.###...####...##..##....##....##......##......##.....*
            *.##.#.##..##..##..##..##....##....##......##......####...*
            *.##...##..######...####.....##....##......##......##.....*
            *.##...##..##..##....##....######..######..######..######.*
            ...........................................................""";

        // Menu principal avec options
        String menu = """
                        1. CONNEXION
                        2. QUITTER
                        Choix: """;

        System.out.print(logo + "\n" + menu);
        
        // Validation de l'entrée de l'utilisateur pour éviter des erreurs de type
        try {
            choix = scanner.nextByte(); 
        } catch (Exception e) {
            System.out.println("Erreur : entrée invalide. Veuillez entrer un numéro.");
            scanner.next(); // Pour vider le scanner de l'entrée non valide
            menuAccueil(); // Retour au menu d'accueil
            return;
        }

        // Gestion des choix de l'utilisateur
        switch(choix){
            case 1:
                authentification();
                break;
            case 2:
                System.exit(0); // Quitte l'application

            default:
                System.out.print("----- Choix invalide. Veuillez recommencer ---\n");
                menuAccueil();
        }
    }

    // Fonction pour authentifier l'utilisateur
    public void authentification(){
        String utilisateur = ""; // Nom d'utilisateur
        String motDePasse = "";  // Mot de passe

        System.out.print("UTILISATEUR: ");
        utilisateur = scanner.next();

        System.out.print("MOT DE PASSE: ");
        motDePasse = scanner.next();

        // Vérification du mot de passe
        if(motDePasse.equals("maVille25")){ 
            switch(utilisateur.toLowerCase()){
                case "citoyen": 
                    menuCitoyen(utilisateur);
                    break;

                case "intervenant": 
                    menuIntervenant(utilisateur);
                    break;

                default:
                    System.out.println("Utilisateur non reconnu. Veuillez recommencer.");
                    authentification();
            }
        }
        else{
            System.out.println("----- LA COMBINAISON UTILISATEUR/MOT DE PASSE EST INVALIDE. VEUILLEZ RECOMMENCER. ----");
            authentification(); // Recommence l'authentification
        }

    }

    // Menu spécifique pour les citoyens
    public void menuCitoyen(String utilisateur){
        String menu = """
                1. S'inscrire comme résident
                2. Consulter les travaux en cours ou à venir
                3. Rechercher des travaux
                4. Gérer les notifications personnalisées
                5. Choisir une plage horaire pour les travaux
                6. Donner son avis sur le déroulement des travaux
                7. Consulter les préférences des autres résidents
                8. Soumettre une requête de travail
                9. Signaler un problème à la ville

                10. Revenir au menu citoyen
                11. Revenir au menu d'accueil

                CHOIX: """;

        System.out.println("----- BIENVENUE " + utilisateur + " -----");
        System.out.print(menu);

        try {
            choix = scanner.nextByte();
        } catch (Exception e) {
            System.out.println("Erreur : entrée invalide.");
            scanner.next();
            menuCitoyen(utilisateur);
            return;
        }

        // Gestion des options du menu citoyen
        switch(choix){
            case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9:
                prnt("----- Fonctionnalité non-disponible -----");
                prnt("\n*Appuyer sur <o> + <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;
                
            case 10:
                menuCitoyen(utilisateur);
                break;

            case 11:
                menuAccueil();
                break;

            default:
                System.out.println("Choix invalide. Veuillez recommencer.");
                menuCitoyen(utilisateur);
        }
    }

    // Menu spécifique pour les intervenants
    public void menuIntervenant(String utilisateur){

        String menu = """
            1. S'inscrire comme intervenant
            2. Consulter la liste des requêtes de travail
            3. Soumettre un nouveau projet de travaux
            4. Mettre à jour les informations d'un chantier

            5. Revenir au menu intervenant
            6. Revenir au menu d'accueil

            CHOIX: """;

        System.out.println("----- BIENVENUE " + utilisateur + " -----");
        System.out.print(menu);

        try {
            choix = scanner.nextByte();
        } catch (Exception e) {
            System.out.println("Erreur : entrée invalide.");
            scanner.next();
            menuIntervenant(utilisateur);
            return;
        }

        // Gestion des options du menu intervenant
        switch(choix){
            case 1: case 3: case 4:
                prnt("----- Fonctionnalité non-disponible -----");
                prnt("\n*Appuyer sur <o> + <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuIntervenant(utilisateur);
                break;

            case 2:
                consulterRequeteTravaux();
                prnt("\n*Appuyer sur <o> + <Entrée> pour revenir au menu intervenant*");
                scanner.next();
                menuIntervenant(utilisateur);
                break;

            case 5:
                menuIntervenant(utilisateur);
                break;

            case 6:
                menuAccueil();
                break;

            default:
                System.out.println("Choix invalide. Veuillez recommencer.");
                menuIntervenant(utilisateur);
        }
    }

    // Méthode utilitaire pour afficher un message
    public void prnt(String message){
        System.out.println(message);
    }

    // Consultation des requêtes de travaux (juste un exemple)
    public void consulterRequeteTravaux(){
        // Création de la liste des travaux avec des objets fictifs
        ArrayList<Travaux> listeTravaux = new ArrayList<>();

        Travaux tr1 = new Travaux("Réfection Aqueduc", "2024-11-01", "2024-12-30", 
                                  "Boulevard René-Levesque entre rue Saint-Denis et rue Sainte-Élisabeth", 
                                  "Pomerleau");
        
        Travaux tr2 = new Travaux("Réparation trottoir", "2024-05-01", "2025-01-31", 
                                  "Rue Beaubien entre rue Casgrain et Avenue De Gaspé", 
                                  "ROXBORO Excavation");

        Travaux tr3 = new Travaux("Patchage nid de poule", "2024-10-01", "2025-10-31", 
                                  "Avenue Du Parc entre rue Laurier Ouest et rue Saint-Viateur",
                                  "Bob le Bricoleur");

        listeTravaux.add(tr1);
        listeTravaux.add(tr2);
        listeTravaux.add(tr3);

        System.out.println(listeTravaux.toString());

        
    }

    // Point d'entrée principal du programme
    public static void main(String[] args){
        MaVille maVille = new MaVille();
        maVille.menuAccueil();
    }
}

// Classe Travaux pour représenter un chantier
class Travaux {
    private String description;
    private String dateDebut;
    private String dateFin;
    private String localisation;
    private String entrepreneur;

    public Travaux(String description, String dateDebut, String dateFin, String localisation, String entrepreneur) {
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.localisation = localisation;
        this.entrepreneur = entrepreneur;
    }

    @Override
    public String toString() {
        return String.format("\n" + "Travaux: %s | Début: %s | Fin: %s | Lieu: %s | Entreprise: %s", 
                             description, dateDebut, dateFin, localisation, entrepreneur);
    }
}