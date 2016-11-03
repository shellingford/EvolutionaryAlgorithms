package evoalg.fitness;

import evoalg.genotype.Genotype;

public class FitnessMin<T extends Genotype<T>> extends Fitness<T> {

  @Override
  public Fitness<T> copy() {
    return new FitnessMin<T>();
  }

  @Override
  public int compareTo(Fitness<T> fitness) {
    if (getValue() < fitness.getValue()) {
      return 1;
    }
    if (getValue() > fitness.getValue()) {
      return -1;
    }
    return 0;
  }

}
