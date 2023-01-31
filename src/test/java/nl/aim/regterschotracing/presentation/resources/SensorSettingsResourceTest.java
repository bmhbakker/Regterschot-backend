package nl.aim.regterschotracing.presentation.resources;

import nl.aim.regterschotracing.datasource.dao.GaugeGraphSettingsDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorSettingsResourceTest {

    private SensorSettingsResource sut;

    @BeforeEach
    public void setup() {
        sut = new SensorSettingsResource();
    }

    @Test
    void getSettingsWithGoodJWT() {
        GaugeGraphSettingsDAO mockGaugeGraphSettingsDAO = Mockito.mock(GaugeGraphSettingsDAO.class);
        sut.setGaugeGraphSettingsDAO(mockGaugeGraphSettingsDAO);
        var http_code = 200;
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0";

        var testValue = sut.getSettings(token, "Oil temperature");
        assertEquals(http_code, testValue.getStatus());
    }

    @Test
    void getSettingsWithWrongJWT() {
        GaugeGraphSettingsDAO mockGaugeGraphSettingsDAO = Mockito.mock(GaugeGraphSettingsDAO.class);
        sut.setGaugeGraphSettingsDAO(mockGaugeGraphSettingsDAO);
        var http_code = 403;

        var testValue = sut.getSettings("", "Oil temperature");
        assertEquals(http_code, testValue.getStatus());
    }
}
