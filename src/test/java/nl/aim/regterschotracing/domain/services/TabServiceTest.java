package nl.aim.regterschotracing.domain.services;

import nl.aim.regterschotracing.datasource.dao.TabDAO;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.TabDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class TabServiceTest {
    @Mock
    public TabService sut;
    @InjectMocks
    public TabDAO tabDAO;
    @Mock
    public TabDTO tabsDTO;

    public String user = "testUser";

    @BeforeEach
    public void setup() {
        sut = new TabService();
        tabDAO = Mockito.mock(TabDAO.class);
        sut.setTabDAO(tabDAO);
        tabsDTO = new TabDTO();
        tabsDTO.setName("test");
        tabsDTO.setTabId(1);
    }

    @Test
    void getAllTabsTest() {
        //Arrange
        List<TabDTO> mockedData = new ArrayList<>();
        mockedData.add(tabsDTO);

        //Act
        Mockito.when(tabDAO.getUserTabs(user)).thenReturn(mockedData);

        //Assert
        assertEquals(sut.getAll(user).get(0), tabsDTO);
    }

    @Test
    void deleteTabTest() throws DatabaseException {
        doThrow(DatabaseException.class).when(tabDAO).deleteTab(anyInt(),anyString());
        assertThrows(DatabaseException.class, () -> {
            sut.deleteTab(1,"Erik");
        });
        verify(tabDAO).deleteTab(1,"Erik");
    }

    @Test
    void createTabTest() throws DatabaseException {
        doThrow(DatabaseException.class).when(tabDAO).createTab(anyString(), anyString(), anyInt());
        assertThrows(DatabaseException.class, () -> {
            sut.createTab(user, "testTab", 1);
        });
        verify(tabDAO).createTab(user, "testTab", 1);
    }
}