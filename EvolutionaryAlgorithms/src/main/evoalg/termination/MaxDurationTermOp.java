package evoalg.termination;

import evoalg.Population;
import evoalg.genotype.Genotype;

/**
 * Termination operator implementation that checks execution duration of the algorithm so far
 * and terminates algorithm if it is above set limit.
 *
 * @param <T> genotype
 */
public class MaxDurationTermOp<T extends Genotype<T>> implements ITerminationOperator<T> {

  private final long durationLimit;

  public MaxDurationTermOp(long durationLimit) {
    this.durationLimit = durationLimit;
  }

  @Override
  public boolean shouldTerminate(Population<T> population, int generationNo, long duration) {
    return duration > durationLimit;
  }

}
