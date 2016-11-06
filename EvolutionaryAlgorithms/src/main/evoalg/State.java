package evoalg;

import lombok.Getter;
import evoalg.algorithm.Algorithm;
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
  private int generationNo;

  public State(T genotype, Algorithm<T> algorithm, int populationSize, int demeSize, IEvaluate<T> ievaluate) {
    this.genotype = genotype;
    this.algorithm = algorithm;
    this.population = new Population<T>(ievaluate, genotype, populationSize, demeSize);
  }

  public void run() {
    generationNo = 0;
    population = population.evaluate();

    while (generationNo < 30) {
      population = algorithm.advanceGeneration(population);
      generationNo++;
    }
  }

  public void restart() {
    population = population.reset(this.genotype);
    run();
  }
}
