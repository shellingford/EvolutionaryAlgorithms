package evoalg.algorithm;

import evoalg.Population;
import evoalg.genotype.Genotype;

/**
 * Interface for implementing algorithm stoppage.
 *
 * You can use generation number, total duration time and/or population.
 * It is invoked for every generation.
 *
 * @param <T> genotype
 */
public interface IMilestone<T extends Genotype<T>> {

  /**
   * Checks if algorithm needs to stop.
   *
   * @param population current generation's population
   * @param generationNo current generation number
   * @param duration total duration of the algorithm till now
   * @return true if algorithm should stop, false otherwise
   */
  boolean reached(Population<T> population, int generationNo, long duration);
}
