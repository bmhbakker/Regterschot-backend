package nl.aim.regterschotracing.domain.services;

import nl.aim.regterschotracing.datasource.dao.GraphDAO;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.GraphsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GraphServiceTest {
    @Mock
    public GraphService sut;
    @InjectMocks
    public GraphDAO graphDAO;
    @Mock
    public GraphsDTO graphDTO;

    @BeforeEach
    public void setup() {
        sut = new GraphService();
        graphDAO = Mockito.mock(GraphDAO.class);
        sut.setGraphDAO(graphDAO);
        graphDTO = new GraphsDTO();
        graphDTO.setTabId(1);
        graphDTO.setId(1);
    }

    @Test
    void getAllGraphsTest() {
        //Arrange
        List<GraphsDTO> mockedData = new ArrayList<>();
        mockedData.add(graphDTO);

        //Act
        Mockito.when(graphDAO.getGraphs(1)).thenReturn(mockedData);


        //Assert
        assertEquals(sut.getAll(1).get(0), graphDTO);

    }

    @Test
    void addGraph() throws DatabaseException {
        doThrow(DatabaseException.class).when(graphDAO).addGraph(anyInt(), anyInt(), anyString(),anyString());
        assertThrows(DatabaseException.class, () -> {
            sut.addGraph(1, 1, "linechart","Erik");
        });
        verify(graphDAO).addGraph(1, 1, "linechart","Erik");
    }

    @Test
    void deleteGraphs() throws DatabaseException {
        doThrow(DatabaseException.class).when(graphDAO).deleteGraph(anyInt(), anyInt(),anyString());
        assertThrows(DatabaseException.class, () -> {
            sut.deleteGraph(1, 1,"Erik");
        });
        verify(graphDAO).deleteGraph(1, 1,"Erik");
    }

}


