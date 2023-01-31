package nl.aim.regterschotracing.presentation.dto;

public class RaceDTO {
  private int id;
  private String raceName;
  private String car;
  private String date;

  public RaceDTO(int id, String raceName, String date) {
    this.id = id;
    this.raceName = raceName;
    this.car = "BMW 320 4fl E46";
    this.date = date;
  }

  /**
   * Gets the id of the race.
   *
   * @return this race id.
   */
  public int getId() {
    return id;
  }

  /**
   * Changes or sets the id of a race.
   *
   * @param id Should be a race id.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the car of the race.
   *
   * @return this car.
   */
  public String getCar() {
    return car;
  }

  /**
   * Changes or sets the car of the race.
   *
   * @param car Should be a car.
   */
  public void setCar(String car) {
    this.car = car;
  }

  /**
   * Gets the date of the race.
   *
   * @return this race date.
   */
  public String getDate() {
    return date;
  }

  /**
   * Changes or sets the date of a race.
   *
   * @param date Should be a date.
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * Gets the name of the race.
   *
   * @return this race name.
   */
  public String getRaceName() {
    return raceName;
  }

  /**
   * Changes or sets the name of the race.
   *
   * @param raceName Should be a race name.
   */
  public void setRaceName(String raceName) {
    this.raceName = raceName;
  }

}
