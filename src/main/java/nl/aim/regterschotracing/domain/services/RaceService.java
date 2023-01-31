package nl.aim.regterschotracing.domain.services;

import jakarta.inject.Inject;

import nl.aim.regterschotracing.datasource.dao.RaceDAO;
import nl.aim.regterschotracing.presentation.dto.RaceDTO;

import java.util.List;

/**
 * Controls all the data of races.
 */
public class RaceService {
  private RaceDAO raceDAO;

  /**
   * Gets all races in the database.
   *
   * @return all races available.
   */
  public List<RaceDTO> getAllRaces() {
    return raceDAO.getAllRaces();
  }

  /**
   * Assigns raceDAO.
   * Sets a race data acces object.
   *
   * @param raceDAO Should be a RaceDAO type.
   */
  @Inject
  public void setRaceDAO(RaceDAO raceDAO) {
    this.raceDAO = raceDAO;
  }
}
