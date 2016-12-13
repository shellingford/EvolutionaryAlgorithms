package evoalg.termination;

import evoalg.Population;
import evoalg.genotype.Genotype;

/**
 * Termination operator interface. Every custom termination operator must implement
 * this interface. <p>
 *
 * Termination operators checks if evolutionary algorithm should terminate. They can be
 * used to avoid implementing some of the more common termination conditions for each
 * problem separately. {@link evoalg.algorithm.IMilestone IMilestone} must still be
 * implemented for every problem, these operators are just more like helpers with that.
 *
 * @param <T> genotype
 */
public interface ITerminationOperator<T extends Genotype<T>> {

  /**
   * Checks if evolutionary algorithm should terminate.
   *
   * @param population current generation's population
   * @param generationNo current generation number
   * @param duration total duration of the algorithm till now
   * @return true if algorithm should terminate, false otherwise
   */
  boolean shouldTerminate(Population<T> population, int generationNo, long duration);
}
