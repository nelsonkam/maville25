package api.controllers;

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import api.services.WorkRequestService;
import api.repositories.WorkRequestRepository;
import api.DatabaseManager;

import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SubmitWorkRequestTest {
    @Test
    public void testSubmitWorkRequest() {

        // Créer une requête fictive
        WorkRequest mockRequest = new WorkRequest("New Work", "Work Description", "Type1", LocalDate.now(),
                "resident@example.com");

        // Simuler le comportement du repository
        WorkRequestRepository mockRepository = Mockito.mock(WorkRequestRepository.class);
        Mockito.doNothing().when(mockRepository).save(Mockito.any(WorkRequest.class));

        // Utiliser le service avec le mock
        WorkRequestService service = new WorkRequestService(mockRepository);
        service.submitRequest(mockRequest);

        // Vérifier que la méthode save a été appelée
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockRequest);
    }

}
