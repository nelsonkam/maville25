public class Travaux{
    protected String typeTravaux;
    protected String dateDebut;
    protected String dateFin;
    protected String localisation;
    protected String intervenant;

    public Travaux(String typeTravaux, String dateDebut, String dateFin, String localisation, String intervenant){
        this.typeTravaux = typeTravaux;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.localisation = localisation;
        this.intervenant = intervenant;
    }

    @Override
    public String toString(){
        return "=====TRAVAUX EN COURS===== \n-"+ typeTravaux + " " + "\n-DU: "  + dateDebut + " AU: " + dateFin + "\n-" + localisation + "\n-EXÉCUTÉ PAR: " + intervenant + "\n==========================\n\n";
    }
/* 
    public static void main(String[] args){
        Travaux tr1 = new Travaux("Réfection Aqueduc", "2024-01-01", "2024-04-30", "Boulevard René-Levesque entre Saint-Denis et Sainte-Élisabeth", "Pomerleau");

        System.out.println(tr1);
    }
*/
}