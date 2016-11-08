package evoalg.genotype;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import evoalg.Individual;

/**
 * Main mutation class that uses mutation operators to mutate and individual
 * or a list of individuals.
 *
 * @param <T> genotype
 */
public class Mutation<T extends Genotype<T>> {

  private final List<MutationOp<T>> operators;
  private final Random random;
  private final double indMutProb;

  public Mutation(List<MutationOp<T>> operators, double indMutProb) {
    Preconditions.checkArgument(0d <= indMutProb && indMutProb <= 1d, "Mutation probability must be in range [0, 1]");
    this.operators = ImmutableList.copyOf(operators);
    this.random = new Random();
    this.indMutProb = indMutProb;
  }

  /**
   * Tries to mutate every individual from the pool.
   *
   * @param pool pool of individuals
   * @return pool with mutated individuals
   */
  public List<Individual<T>> mutation(List<Individual<T>> pool) {
    return pool.stream().map(ind -> mutate(ind)).collect(Collectors.toList());
  }

  /**
   * Mutates specified individual with random mutation operator and random probability.
   * If mutation will not happen then original individual is returned.
   *
   * @param ind individual that might be mutated
   * @return mutated or original individual.
   */
  public Individual<T> mutate(Individual<T> ind) {
    if (random.nextDouble() <= indMutProb) {
      int randOperatorIndex = random.nextInt(operators.size());
      T mutatedGenotype = operators.get(randOperatorIndex).mutate(ind.getGenotype());
      return new Individual<T>(ind.getIevaluate(), mutatedGenotype);
    }
    else {
      return ind;
    }
  }

  /**
   * Immutable collection of mutation operators that will be used
   * for current genotype representation.
   *
   * @return immutable collection of mutation operators
   */
  public List<MutationOp<T>> getMutationOp() {
    return operators;
  }

}
