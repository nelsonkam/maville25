import java.util.Scanner;

public class MaVille {

    public Scanner scanner = new Scanner(System.in);
    public byte choix = 0;

    public void menuAccueil(){
        String logo = """
            *.##...##..........##..##..######..##......##......######.*
            *.###.###...####...##..##....##....##......##......##.....*
            *.##.#.##..##..##..##..##....##....##......##......####...*
            *.##...##..######...####.....##....##......##......##.....*
            *.##...##..##..##....##....######..######..######..######.*
            *.........................................................*""";

        String menu = """
                        1. CONNECTION
                        2. QUITTER
                        Choix:""";

        System.out.print(logo + "\n" + menu);
        choix = scanner.nextByte();

        switch(choix){
            case 1:
                authentification();
            case 2:
                System.exit(0);
            default:
                System.out.print("----- Choix invalide. Veuillez recommencer ---\n");
                menuAccueil();
                
        }
    }
    
    public void authentification(){

    }

    public static void main(String[] args){
        MaVille maVille = new MaVille();
        maVille.menuAccueil();
    }
}
