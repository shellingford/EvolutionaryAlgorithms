package evoalg.fitness;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public class FitnessMin<T extends Genotype<T>> extends Fitness<T> {

  public FitnessMin(Individual<T> individual, double value) {
    super(individual, value);
  }

  @Override
  public FitnessMin<T> copy(double value) {
    return new FitnessMin<T>(getIndividual(), getValue());
  }

  @Override
  public FitnessMin<T> copy() {
    return copy(getValue());
  }

  @Override
  public int compareTo(Fitness<T> fitness) {
    return Double.compare(getValue(), fitness.getValue()) * -1;
  }

}
