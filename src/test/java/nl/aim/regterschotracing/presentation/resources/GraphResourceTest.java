package nl.aim.regterschotracing.presentation.resources;

import nl.aim.regterschotracing.domain.services.GraphService;
import nl.aim.regterschotracing.presentation.dto.GraphsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GraphResourceTest {
    @Mock
    private GraphResource sut;
    @InjectMocks
    GraphService graphService;

    String token;
    List<GraphsDTO> graphsDTOList;
    final int http_statuscode_ok = 200;

    @BeforeEach
    public void setup() {
        sut = new GraphResource();
        graphService = mock(GraphService.class);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0";
        sut.setGraphService(graphService);
        GraphsDTO graphsDTO1 = new GraphsDTO();
        GraphsDTO graphsDTO2 = new GraphsDTO();
        graphsDTO1.setId(1);
        graphsDTO1.setTabId(1);
        graphsDTO2.setId(2);
        graphsDTO2.setTabId(1);
        graphsDTOList = new ArrayList<>();
        graphsDTOList.add(graphsDTO1);
        graphsDTOList.add(graphsDTO2);
    }
    @Test
    void addGraphTest() {

        int testValue = sut.addGraph(1,1,"line",token).getStatus();
        assertEquals(http_statuscode_ok, testValue);
    }
    @Test
    void deleteGraphTest(){
        int testValue = sut.deleteGraph(1,1,token).getStatus();
        assertEquals(http_statuscode_ok, testValue);
    }

}
