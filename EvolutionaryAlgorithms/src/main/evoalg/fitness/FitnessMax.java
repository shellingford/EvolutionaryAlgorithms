package evoalg.fitness;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public class FitnessMax<T extends Genotype<T>> extends Fitness<T> {

  public FitnessMax(Individual<T> individual, double value) {
    super(individual, value);
  }

  @Override
  public FitnessMax<T> copy(double value) {
    return new FitnessMax<T>(getIndividual(), getValue());
  }

  @Override
  public FitnessMax<T> copy() {
    return copy(getValue());
  }

  @Override
  public int compareTo(Fitness<T> fitness) {
    return Double.compare(getValue(), fitness.getValue());
  }

}
