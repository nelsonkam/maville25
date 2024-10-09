import java.util.Scanner;

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
            switch(utilisateur){
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

                10. Revenir au menu d'accueil
                """;

        System.out.println("-----BIENVENUE " + utilisateur + "-----");
        System.out.print(menu);
        choix = scanner.nextByte();

        switch(choix){
            case 1:
                prnt("----- S'inscrire comme résident ----");
                break;

            case 2:
                prnt("----- Consulter les travaux en cours ou à venir -----");
                break;

            case 3:
                prnt("----- Rechercher des travaux -----");
                break;

            case 4:
                prnt("----- Gérer les notifications personnalisées -----");
                break;

            case 5:
                prnt("----- Choisir une plage horaire pour les travaux -----");
                break;

            case 6:
                prnt("----- Donner son avis sur le déroulement des travaux -----");
                break;

            case 7:
                prnt("----- Consulter les préférences des autres résidents -----");
                break;

            case 8:
                prnt("----- Soumettre une requête de travail -----");
                break;

            case 9:
                prnt("----- Signaler un problème à la ville -----");
                break;        
                
            case 10:
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

            5. Revenir à l'authentification
            """;

        System.out.println("-----BIENVENUE " + utilisateur + "-----");
        System.out.print(menu);
        choix = scanner.nextByte();

        switch(choix){
            case 1:
                prnt("----- S'inscrire comme intervenant ----");
                break;

            case 2:
                prnt("----- Consulter la liste des requêtes de travail -----");
                break;

            case 3:
                prnt("----- Soumettre un nouveau projet de travaux -----");
                break;

            case 4:
                prnt("----- Mettre à jour les informations d'un chantier -----");
                break;

            case 5:
                prnt("----- Revenir au menu d'accueil -----");
                menuAccueil();
                break;
        }
    }

    public void prnt(String a){
        System.out.println(a);
    }

    public static void main(String[] args){
        MaVille maVille = new MaVille();
        maVille.menuAccueil();
    }
}
