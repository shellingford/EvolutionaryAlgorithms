package evoalg.fitness;

import evoalg.genotype.Genotype;

public class FitnessMax<T extends Genotype<T>> extends Fitness<T> {

  @Override
  public FitnessMax<T> copy() {
    return new FitnessMax<T>();
  }

  @Override
  public int compareTo(Fitness<T> fitness) {
    if (getValue() > fitness.getValue()) {
      return 1;
    }
    if (getValue() < fitness.getValue()) {
      return -1;
    }
    return 0;
  }

}
