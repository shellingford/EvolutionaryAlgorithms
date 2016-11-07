package evoalg.genotype;

import java.util.Random;

import lombok.Getter;

/**
 * Abstract class for crossover operator.
 *
 * Every crossover operator must extend this class to be used as a crossover operator
 * within the main algorithm.
 *
 * @param <T> genotype
 */
@Getter
public abstract class CrossoverOp<T extends Genotype<T>> {

  private Random random;

  public CrossoverOp() {
    this.random = new Random();
  }

  /**
   * Mates two parents to produce a child.
   *
   * @param parent1 parent
   * @param parent2 parent
   * @return child
   */
  public abstract T mate(T parent1, T parent2);
}
