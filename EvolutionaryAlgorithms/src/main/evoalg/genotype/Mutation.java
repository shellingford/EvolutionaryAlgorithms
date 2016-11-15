package evoalg.genotype;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import evoalg.Individual;
import evoalg.random.IRandomness;

/**
 * Main mutation class that uses mutation operators to mutate an individual
 * or a list of individuals. If individual will be mutated depends on the
 * set probability.
 *
 * Mutation operator is selected randomly where every operator has the same
 * chance of being selected. If you want to have a higher chance to select
 * a certain operator, for now, you can just add multiple instance of that
 * operator in the operators list.
 *
 * @param <T> genotype
 */
public class Mutation<T extends Genotype<T>> {

  private final List<MutationOp<T>> operators;
  private final IRandomness random;
  private final double indMutProb;

  public Mutation(List<MutationOp<T>> operators, double indMutProb, IRandomness random) {
    Preconditions.checkArgument(0d <= indMutProb && indMutProb <= 1d, "Mutation probability must be in range [0, 1]");
    this.operators = ImmutableList.copyOf(operators);
    this.random = random;
    this.indMutProb = indMutProb;
  }

  /**
   * Tries to mutate every individual from the pool. Checks for every individual
   * if mutation will occur.
   *
   * @param pool pool of individuals
   * @return pool with mutated individuals
   */
  public List<Individual<T>> mutate(List<Individual<T>> pool) {
    return pool.stream().map(ind -> mutate(ind)).collect(Collectors.toList());
  }

  /**
   * Mutates specified individual with mutation operator and random probability.
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
