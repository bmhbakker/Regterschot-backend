package nl.aim.regterschotracing.presentation.dto.graphs;


public class GaugeSettingsDTO {
  private int min;
  private int max;
  private int greenTo;
  private int yellowTo;

  public GaugeSettingsDTO(int min, int max, int greenTo, int yellowTo) {
    this.min = min;
    this.max = max;
    this.greenTo = greenTo;
    this.yellowTo = yellowTo;
  }


  public int getMin() {
    return min;
  }

  public void setMin(int min) {
    this.min = min;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public int getGreenTo() {
    return greenTo;
  }

  public void setGreenTo(int greenTo) {
    this.greenTo = greenTo;
  }

  public int getYellowTo() {
    return yellowTo;
  }

  public void setYellowTo(int yellowTo) {
    this.yellowTo = yellowTo;
  }
}
