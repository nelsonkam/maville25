import java.util.Scanner;
import java.util.ArrayList;

public class MaVille {

    public Scanner scanner = new Scanner(System.in);
    public byte choix = 0;

    public void menuAccueil(){
        String logo = """
            ...........................................................        
            *.##...##..........##..##..######..##......##......######.*
            *.###.###...####...##..##....##....##......##......##.....*
            *.##.#.##..##..##..##..##....##....##......##......####...*
            *.##...##..######...####.....##....##......##......##.....*
            *.##...##..##..##....##....######..######..######..######.*
            ...........................................................""";

        String menu = """
                        1. CONNECTION
                        2. QUITTER
                        Choix: """;

        System.out.print(logo + "\n" + menu);
        choix = scanner.nextByte();

        switch(choix){
            case 1:
                authentification();
                break;
            case 2:
                System.exit(0);

            default:
                System.out.print("----- Choix invalide. Veuillez recommencer ---\n");
                menuAccueil();
        }
    }
    
    public void authentification(){
        String utilisateur = "";
        String motDePasse = "";
        String prompt = "";

        prompt = "UTILISATEUR: ";
        System.out.print(prompt);
        utilisateur = scanner.next();

        prompt = "MOT DE PASSE: ";
        System.out.print(prompt);
        motDePasse = scanner.next();

        if(motDePasse.equals("maVille25")){
            switch(utilisateur.toLowerCase()){
                case "citoyen": 
                    menuCitoyen(utilisateur);
                    break;
    
                case "intervenant": 
                    menuIntervenant(utilisateur);
                    break;
            }
        }
        else{
            System.out.println("----- LA COMBINAISON UTILISATEUR/MOT DE PASSE EST INVALIDE. VEUILLEZ RECOMMENCER. ----");
            authentification();
        }

    }

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

        System.out.println("-----BIENVENUE " + utilisateur + "-----");
        System.out.print(menu);
        choix = scanner.nextByte();

        switch(choix){
            case 1:
                prnt("----- S'inscrire comme résident ----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 2:
                prnt("----- Consulter les travaux en cours ou à venir -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 3:
                prnt("----- Rechercher des travaux -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 4:
                prnt("----- Gérer les notifications personnalisées -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 5:
                prnt("----- Choisir une plage horaire pour les travaux -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 6:
                prnt("----- Donner son avis sur le déroulement des travaux -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 7:
                prnt("----- Consulter les préférences des autres résidents -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 8:
                prnt("----- Soumettre une requête de travail -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;

            case 9:
                prnt("----- Signaler un problème à la ville -----");
                prnt("*FONCTIONNALITÉ NON-DISPONIBLE* \n *Appuyer <o> et <Entrée> pour revenir au menu citoyen*");
                scanner.next();
                menuCitoyen(utilisateur);
                break;        
                
            case 10:
                menuCitoyen(utilisateur);
                break;

            case 11:
                menuAccueil();
                break;
        }
    }


    public void menuIntervenant(String utilisateur){

        String menu = """
            1. S'inscrire comme intervenant
            2. Consulter la liste des requêtes de travail
            3. Soumettre un nouveau projet de travaux
            4. Mettre à jour les informations d'un chantier

            5. Revenir au menu intervenant
            6. Revenir au menu d'accueil

            CHOIX: """;

        System.out.println("-----BIENVENUE " + utilisateur + "-----");
        System.out.print(menu);
        choix = scanner.nextByte();

        switch(choix){
            case 1:
                prnt("----- S'inscrire comme intervenant ----");
                break;

            case 2:
                prnt("----- Consulter la liste des requêtes de travail -----");
                consulterRequeteTravaux();
                break;

            case 3:
                prnt("----- Soumettre un nouveau projet de travaux -----");
                break;

            case 4:
                prnt("----- Mettre à jour les informations d'un chantier -----");
                break;

            case 5:
                menuIntervenant(utilisateur);
                break;


            case 6:
                menuAccueil();
                break;
        }
    }

    public void prnt(String a){
        System.out.println(a);
    }

    public void consulterRequeteTravaux(){
        /* Type de travaux, date début, date fin, localisation */
        
        ArrayList<Travaux> listeTravaux = new ArrayList<>();

        Travaux tr1 = new Travaux("Réfection Aqueduc", "2024-11-01", "2024-12-30", "Boulevard René-Levesque entre  rue Saint-Denis et rue Sainte-Élisabeth", "Pomerleau");
        
        Travaux tr2 = new Travaux("Réparation trottoir", "2024-05-01", "2025-01-31", "Rue Beaubien entre rue Casgrain et Avenue De Gaspé", "ROXBORO Excavation");

        Travaux tr3 = new Travaux("Patchage nid de poule", "2024-10-01", "2025-10-31", "Avenue Du Parc entre rue Laurier Ouest et rue Saint-Viateur","Bob le Bricoleur");

        listeTravaux.add(tr1);
        listeTravaux.add(tr2);
        listeTravaux.add(tr3);

        System.out.println(listeTravaux.toString());

        
    }

    public static void main(String[] args){
        MaVille maVille = new MaVille();
        maVille.menuAccueil();
    }
}
