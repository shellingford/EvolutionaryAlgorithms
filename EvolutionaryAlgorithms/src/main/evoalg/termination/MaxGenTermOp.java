package evoalg.termination;

import evoalg.Population;
import evoalg.genotype.Genotype;

/**
 * Termination operator implementation that checks current number of generation and terminates
 * algorithm if it is above set limit.
 *
 * @param <T> genotype
 */
public class MaxGenTermOp<T extends Genotype<T>> implements ITerminationOperator<T> {

  private final int generationLimit;

  public MaxGenTermOp(int generationLimit) {
    this.generationLimit = generationLimit;
  }

  @Override
  public boolean shouldTerminate(Population<T> population, int generationNo, long duration) {
    return generationNo > generationLimit;
  }

}
