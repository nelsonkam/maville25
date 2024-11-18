package api.services;

import api.MontrealApiClient;
import models.ConstructionWork;

import java.util.List;
import java.util.stream.Collectors;

public class ConstructionWorkService {
    private final MontrealApiClient montrealApiClient;

    public ConstructionWorkService() {
        this.montrealApiClient = new MontrealApiClient();
    }

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
