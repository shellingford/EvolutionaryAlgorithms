package evoalg.algorithm;

import java.util.ArrayList;
import java.util.List;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.Population;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;

/**
 * Main abstract evolutionary algorithm class that contains some basic functionality
 * but should be extended by specific algorithm.
 *
 * @param <T> genotype
 */
public abstract class Algorithm<T extends Genotype<T>> {

  private final Crossover<T> crossover;
  private final Mutation<T> mutation;

  public Algorithm(Mutation<T> mutation, Crossover<T> crossover) {
    this.mutation = mutation;
    this.crossover = crossover;
  }

  /**
   * Advances generation for specified deme.
   *
   * @param deme deme used to get new generation deme
   * @return new generation deme
   */
  public abstract Deme<T> advanceGeneration(Deme<T> deme);

  /**
   * Advances generation for specified population, specifically advances generation of every
   * deme within the population.
   *
   * @param deme population used to get new generation population
   * @return new generation population
   */
  public Population<T> advanceGeneration(Population<T> population) {
    List<Deme<T>> demes = new ArrayList<>();
    for (int iDeme = 0; iDeme < population.size(); iDeme++) {
      Deme<T> activeDeme = population.getDemes().get(iDeme);
      Deme<T> newDeme = advanceGeneration(activeDeme);
      demes.add(newDeme);
    }

    return new Population<T>(demes.size(), demes);
  }

  /**
   * Evaluates individual.
   *
   * @param ind individual that will be evaluated
   * @return evaluated individual
   */
  protected Individual<T> evaluate(Individual<T> ind) {
    return ind.evaluate();
  }

  /**
   * Mutates all individuals within the pool.
   *
   * @param pool pool of individuals that will be mutated
   * @return pool of mutated individuals
   */
  protected List<Individual<T>> mutate(List<Individual<T>> pool) {
    return mutation.mutate(pool);
  }

  /**
   * Mutates individual.
   *
   * @param ind individual that will be mutated
   * @return mutated individual
   */
  protected Individual<T> mutate(Individual<T> ind) {
    return mutation.mutate(ind);
  }

  /**
   * Mates two individuals and returns their child.
   *
   * @param ind1 parent
   * @param ind2 parent
   * @return child
   */
  protected Individual<T> mate(Individual<T> ind1, Individual<T> ind2) {
    return crossover.mate(ind1, ind2);
  }
}
