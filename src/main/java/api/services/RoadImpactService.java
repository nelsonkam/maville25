package api.services;

import api.MontrealApiClient;
import models.RoadImpact;

import java.util.List;

/**
 * Cette classe fournit des services pour gérer les impacts routiers.
 */
public class RoadImpactService {
    private final MontrealApiClient montrealApiClient;

    /**
     * Constructeur de la classe RoadImpactService.
     * Initialise le client API de Montréal.
     */
    public RoadImpactService() {
        this.montrealApiClient = new MontrealApiClient();
    }

    /**
     * Récupère les impacts routiers via l'API de Montréal.
     *
     * @param workId L'identifiant des travaux à filtrer (peut être null).
     * @param streetName Le nom de la rue à filtrer (peut être null).
     * @return Une liste d'impacts routiers.
     * @throws Exception Si une erreur survient lors de la récupération des données.
     */
    public List<RoadImpact> getRoadImpacts(String workId, String streetName) throws Exception {
        return montrealApiClient.getRoadImpacts(workId, streetName);
    }
}