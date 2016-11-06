package evoalg.fitness;

import evoalg.genotype.Genotype;

/**
 *  Fitness comparator that sorts individuals by their value from
 *  the one with lowest value to the one with highest.
 */
public class FitnessMin<T extends Genotype<T>> extends Fitness<T> {

  public FitnessMin(double value) {
    super(value);
  }

  @Override
  public FitnessMin<T> copy(double value) {
    return new FitnessMin<T>(getValue());
  }

  @Override
  public FitnessMin<T> copy() {
    return copy(getValue());
  }

  @Override
  public int compareTo(Fitness<T> fitness) {
    return Double.compare(getValue(), fitness.getValue());
  }

}
