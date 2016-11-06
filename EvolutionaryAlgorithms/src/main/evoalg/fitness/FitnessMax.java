package evoalg.fitness;

import evoalg.genotype.Genotype;

/**
 *  Fitness comparator that sorts individuals by their value from
 *  the one with highest value to the one with lowest.
 */
public class FitnessMax<T extends Genotype<T>> extends Fitness<T> {

  public FitnessMax(double value) {
    super(value);
  }

  @Override
  public FitnessMax<T> copy(double value) {
    return new FitnessMax<T>(value);
  }

  @Override
  public FitnessMax<T> copy() {
    return copy(getValue());
  }

  @Override
  public int compareTo(Fitness<T> fitness) {
    return Double.compare(getValue(), fitness.getValue()) * -1;
  }

}
