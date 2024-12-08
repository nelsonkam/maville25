package api.services;

import api.MontrealApiClient;
import models.ConstructionWork;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Cette classe fournit des services pour gérer les travaux de construction.
 */
public class ConstructionWorkService {
    private final MontrealApiClient montrealApiClient;

    /**
     * Constructeur de la classe ConstructionWorkService.
     * Initialise le client API de Montréal.
     */
    public ConstructionWorkService() {
        this.montrealApiClient = new MontrealApiClient();
    }

    /**
     * Récupère les travaux de construction en cours, filtrés par arrondissement et type.
     *
     * @param borough L'arrondissement à filtrer (peut être null ou vide).
     * @param type Le type de travaux à filtrer (peut être null ou vide).
     * @return Une liste de travaux de construction filtrés.
     * @throws Exception Si une erreur survient lors de la récupération des données.
     */
    public List<ConstructionWork> getCurrentWorks(String borough, String type) throws Exception {
        List<ConstructionWork> works = montrealApiClient.getCurrentWorks();
        
        if (borough != null && !borough.isEmpty()) {
            works = works.stream()
                .filter(w -> w.getBoroughId().toLowerCase().contains(borough.toLowerCase()))
                .collect(Collectors.toList());
        }
        
        if (type != null && !type.isEmpty()) {
            works = works.stream()
                .filter(w -> w.getReasonCategory().toLowerCase().contains(type.toLowerCase()))
                .collect(Collectors.toList());
        }
        
        return works;
    }
}