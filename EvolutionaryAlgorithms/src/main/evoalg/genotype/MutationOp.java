package evoalg.genotype;

import lombok.Getter;
import evoalg.random.IRandomness;

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

  private final IRandomness random;

  public MutationOp(IRandomness random) {
    this.random = random;
  }

  /**
   * Mutates individual and returns it.
   *
   * @param individual individual that will be mutated
   * @return mutated individual
   */
  public abstract T mutate(T individual);
}
