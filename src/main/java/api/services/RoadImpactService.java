package api.services;

import api.MontrealApiClient;
import models.RoadImpact;

import java.util.List;

public class RoadImpactService {
    private final MontrealApiClient montrealApiClient;

    public RoadImpactService() {
        this.montrealApiClient = new MontrealApiClient();
    }

    public List<RoadImpact> getRoadImpacts(String workId, String streetName) throws Exception {
        return montrealApiClient.getRoadImpacts(workId, streetName);
    }
}
