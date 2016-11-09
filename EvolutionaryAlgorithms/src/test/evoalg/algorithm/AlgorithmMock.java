package evoalg.algorithm;

import java.util.List;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.Population;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;

/**
 * Mock class used only for testing methods implemented within {@link evoalg.algorithm.Algorithm Algorithm} class,
 * abstract methods will be tested as part of implementation class testing.
 */
public class AlgorithmMock<T extends Genotype<T>> extends Algorithm<T> {

  public AlgorithmMock(Mutation<T> mutation, Crossover<T> crossover) {
    super(mutation, crossover);
  }

  @Override
  public Population<T> advanceGeneration(Population<T> population) {
    return super.advanceGeneration(population);
  }

  @Override
  protected Individual<T> evaluate(Individual<T> ind) {
    return super.evaluate(ind);
  }

  @Override
  protected List<Individual<T>> mutate(List<Individual<T>> pool) {
    return super.mutate(pool);
  }

  @Override
  protected Individual<T> mutate(Individual<T> ind) {
    return super.mutate(ind);
  }

  @Override
  protected Individual<T> mate(Individual<T> ind1, Individual<T> ind2) {
    return super.mate(ind1, ind2);
  }

  //ignore methods under here
  @Override
  public Deme<T> advanceGeneration(Deme<T> deme) {
    return null;
  }

}
