package evoalg.genotype;

import java.util.List;

import com.google.common.collect.ImmutableList;

import evoalg.Individual;
import evoalg.random.IRandomness;

/**
 * Main crossover class that uses crossover operators to mate two individuals
 * to produce a child individual.
 *
 * @param <T> genotype
 */
public class Crossover<T extends Genotype<T>> {

  private List<CrossoverOp<T>> operators;
  private final IRandomness random;

  public Crossover(List<CrossoverOp<T>> operators, IRandomness random) {
    this.operators = ImmutableList.copyOf(operators);
    this.random = random;
  }

  /**
   * Mates two individuals with a crossover operator and returns their child.
   * Crossover operator is selected randomly where every operator has the same
   * chance of being selected. If you want to have a higher chance to select
   * a certain operator, for now, you can just add multiple instance of that
   * operator in the operators list.
   *
   * @param ind1 parent
   * @param ind2 parent
   * @return child
   */
  public Individual<T> mate(Individual<T> ind1, Individual<T> ind2) {
    int randOperatorIndex = random.nextInt(operators.size());
    T croxGenotype = operators.get(randOperatorIndex).mate(ind1.getGenotype(), ind2.getGenotype());
    return new Individual<T>(ind1.getIevaluate(), croxGenotype);
  }

  /**
   * Immutable collection of crossover operators that will be used
   * for current genotype representation.
   *
   * @return immutable collection of crossover operators
   */
  public List<CrossoverOp<T>> getCrossoverOp() {
    return operators;
  }
}
