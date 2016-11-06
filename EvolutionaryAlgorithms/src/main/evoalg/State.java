package evoalg;

import lombok.Getter;
import evoalg.algorithm.Algorithm;
import evoalg.algorithm.IMilestone;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

/**
 * Class that represents current state of the algorithm. Used to start and stop algorithm
 * execution.
 *
 * @param <T> genotype
 */
@Getter
public class State<T extends Genotype<T>> {

  private Population<T> population;
  private final Algorithm<T> algorithm;
  private final T genotype;
  private final IMilestone<T> milestone;
  private final IEvaluate<T> evaluate;

  public State(T genotype, Algorithm<T> algorithm, Population<T> population, IMilestone<T> milestone,
      IEvaluate<T> evaluate) {
    this.genotype = genotype;
    this.algorithm = algorithm;
    this.population = population;
    this.milestone = milestone;
    this.evaluate = evaluate;
  }

  /**
   * Starts algorithm, advances generations and checks for milestone.
   */
  public void run() {
    population = population.evaluate();
    int generationNo = 0;
    long duration = 0;
    long start = System.currentTimeMillis();
    while (!milestone.reached(population, generationNo, duration)) {
      population = algorithm.advanceGeneration(population);
      generationNo++;
      duration = System.currentTimeMillis() - start;
    }
  }

  /**
   * Restarts algorithm.
   */
  public void restart() {
    population = population.reset(this.genotype, this.evaluate);
    run();
  }
}
