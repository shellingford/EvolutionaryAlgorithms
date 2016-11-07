package evoalg.genotype;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import evoalg.Individual;

/**
 * Main crossover class that uses crossover operators to mate two individuals
 * to produce a child individual.
 *
 * @param <T> genotype
 */
public class Crossover<T extends Genotype<T>> {

  private List<CrossoverOp<T>> operators;
  private final Random random;

  public Crossover(List<CrossoverOp<T>> operators) {
    this.operators = operators;
    this.random = new Random();
  }

  /**
   * Mates two individuals with a random crossover operator and returns their child.
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
    return ImmutableList.copyOf(operators);
  }
}
