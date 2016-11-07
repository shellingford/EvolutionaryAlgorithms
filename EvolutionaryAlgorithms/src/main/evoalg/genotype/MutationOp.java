package evoalg.genotype;

import java.util.Random;

import lombok.Getter;

/**
 * Abstract class for mutation operator.
 *
 * Every mutation operator must extend this class to be used as a mutation operator
 * within the main algorithm.
 *
 * @param <T> genotype
 */
@Getter
public abstract class MutationOp<T extends Genotype<T>> {

  private Random random;

  public MutationOp() {
    this.random = new Random();
  }

  /**
   * Mutates individual and returns it.
   *
   * @param ind individual that will be mutated
   * @return mutated individual
   */
  public abstract T mutate(T ind);
}
