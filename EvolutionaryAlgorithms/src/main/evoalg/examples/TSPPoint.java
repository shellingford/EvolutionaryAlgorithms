package evoalg.examples;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container class for TSP point data.
 */
@AllArgsConstructor
@Getter
public class TSPPoint {

  private final int id;
  private final double x;
  private final double y;
  @Override
  public String toString() {
    return "[id=" + id + ", x=" + x + ", y=" + y + "]";
  }
}
