package nl.aim.regterschotracing.presentation.resources;

import nl.aim.regterschotracing.domain.services.TabService;
import nl.aim.regterschotracing.presentation.dto.GraphsDTO;
import nl.aim.regterschotracing.presentation.dto.TabDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TabsResourceTest {
    private TabResource sut;

    @BeforeEach
    public void setup(){
        sut = new TabResource();

    }

    @Test
    void getTabsWithGoodJWT() {
        TabDTO tabDTO = new TabDTO();
        TabService mockTabsService = Mockito.mock(TabService.class);
        sut.setTabService(mockTabsService);

        tabDTO.setTabId(1);
        tabDTO.setGraphsDTO(new ArrayList<GraphsDTO>());
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0";
        tabDTO.setName("new");


        var http_code = 200;

        var testValue = sut.getTabs(token);

        assertEquals(http_code, testValue.getStatus());
    }

    @Test
    void getTabsWithBadJWT() {
        TabDTO tabDTO = new TabDTO();
        TabService mockTabsService = Mockito.mock(TabService.class);
        sut.setTabService(mockTabsService);
        tabDTO.setTabId(1);
        tabDTO.setGraphsDTO(new ArrayList<GraphsDTO>());
        String token = "WrongToken";
        tabDTO.setName("new");

        var http_code = 403;

        var testValue = sut.getTabs(token);

        assertEquals(http_code, testValue.getStatus());
    }

    @Test
    void createTabTest() {
        TabService mockTabsService = Mockito.mock(TabService.class);
        sut.setTabService(mockTabsService);
        String token = "eyJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9.ewogICAgIm5hbWUiOiAiRXJpayIKfQ==.2d2c39f777b16f75c701e851e9ec20d45f4134a281155e0c6f909876228d7d80";
        var testValue = sut.createTab(token, "testTab", 1).getStatus();

        var http_code = 200;

       assertEquals(http_code, testValue);
    }
    @Test
    void deleteTabTest() {
        TabService mockTabsService = Mockito.mock(TabService.class);
        sut.setTabService(mockTabsService);
        String token = "eyJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9.ewogICAgIm5hbWUiOiAiRXJpayIKfQ==.2d2c39f777b16f75c701e851e9ec20d45f4134a281155e0c6f909876228d7d80";
        var testValue = sut.deleteTab(token, 1).getStatus();
        var http_code = 200;

        assertEquals(http_code, testValue);
    }

    @Test
    void createTabTestBadJWT() {
        TabService mockTabsService = Mockito.mock(TabService.class);
        sut.setTabService(mockTabsService);
        String token = "yJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9.ewogICAgIm5hbWUiOiAiRXJpayIKfQ==.2d2c39f777b16f75c701e851e9ec20d45f4134a281155e0c6f909876228d7d80";
        var testValue = sut.createTab(token, "testTab", 1).getStatus();

        var http_code = 403;

        assertEquals(http_code, testValue);
    }
    @Test
    void deleteTabTestBadJWT() {
        TabService mockTabsService = Mockito.mock(TabService.class);
        sut.setTabService(mockTabsService);
        String token = "yJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9.ewogICAgIm5hbWUiOiAiRXJpayIKfQ==.2d2c39f777b16f75c701e851e9ec20d45f4134a281155e0c6f909876228d7d80";
        var testValue = sut.deleteTab(token, 1).getStatus();
        var http_code = 403;

        assertEquals(http_code, testValue);
    }
}