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

  public abstract void initialize();

  public abstract Deme<T> advanceGeneration(Deme<T> deme);

  public Population<T> advanceGeneration(Population<T> population) {
    List<Deme<T>> demes = new ArrayList<>();
    for (int iDeme = 0; iDeme < population.size(); iDeme++) {
      Deme<T> activeDeme = population.get(iDeme);
      Deme<T> newDeme = advanceGeneration(activeDeme);
      demes.add(newDeme);
    }

    return new Population<T>(population.getIevaluate(), demes.size(), demes);
  }

  protected Individual<T> evaluate(Individual<T> ind) {
    return ind.evaluate();
  }

  protected List<Individual<T>> mutate(List<Individual<T>> pool) {
    return mutation.mutation(pool);
  }

  protected Individual<T> mutate(Individual<T> ind) {
    return mutation.mutate(ind);
  }

  protected Individual<T> mate(Individual<T> ind1, Individual<T> ind2) {
    return crossover.mate(ind1, ind2);
  }
}
