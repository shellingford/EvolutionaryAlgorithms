package evoalg.algorithm;

import java.util.List;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.Population;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;

public abstract class Algorithm<T extends Genotype<T>> {

  private final Crossover<T> crossover;

  private final Mutation<T> mutation;

  public Algorithm(Mutation<T> mutation, Crossover<T> crossover) {
    this.mutation = mutation;
    this.crossover = crossover;
  }

  public abstract void initialize();

  public Population<T> initializePopulation(Population<T> population) {
    Population<T> newPopulation = new Population<>(population.getIevaluate(), population.getGenotype());
    for (int iDeme = 0; iDeme < population.size(); iDeme++) {
      Deme<T> deme = population.get(iDeme);
      newPopulation.add(deme.evaluate());
    }
    return newPopulation;
  }

  public Population<T> advanceGeneration(Population<T> population) {
    Population<T> newPopulation = new Population<>(population.getIevaluate(), population.getGenotype());
    for (int iDeme = 0; iDeme < population.size(); iDeme++) {
      Deme<T> activeDeme = population.get(iDeme);
      Deme<T> newDeme = advanceGeneration(activeDeme);
      newPopulation.add(newDeme);
    }

    return newPopulation;
  }

  public abstract Deme<T> advanceGeneration(Deme<T> deme);

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

  protected Individual<T> copy(Individual<T> source) {
    return source.copy();
  }

  protected boolean removeFrom(Individual<T> victim, List<Individual<T>> pool) {
    int index = 0;
    while (index < pool.size() && pool.get(index) != victim) {
      index++;
    }
    if (index == pool.size()) {
      return false;
    }
    pool.remove(index);
    return true;
  }

  protected boolean isMember(Individual<T> single, List<Individual<T>> pool) {
    return pool.contains(single);
  }
}
