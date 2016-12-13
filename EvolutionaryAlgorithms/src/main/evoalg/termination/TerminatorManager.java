package evoalg.termination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import evoalg.Population;
import evoalg.genotype.Genotype;

/**
 * Terminator manager is helper class that can be used to combine different termination operators and check
 * them for termination condition. It checks every termination operator max once.
 *
 * @param <T> genotype
 */
public class TerminatorManager<T extends Genotype<T>> {

  private final Set<ITerminationOperator<T>> terminationOperators;

  @SafeVarargs
  public TerminatorManager(ITerminationOperator<T>... operators) {
    this.terminationOperators = new HashSet<>(Arrays.asList(operators));
  }

  /**
   * Checks every termination operator max once if termination condition is satisfies.
   * If one operator returns that termination condition is satisfied, then it immediately
   * returns true.
   *
   * @param population current generation's population
   * @param generationNo current generation number
   * @param duration total duration of the algorithm till now
   * @return true if any of the termination conditions are satisfied, false otherwise
   */
  public boolean shouldTerminate(Population<T> population, int generationNo, long duration) {
    return terminationOperators.stream().anyMatch(termOp -> termOp.shouldTerminate(population, generationNo, duration));
  }
}
