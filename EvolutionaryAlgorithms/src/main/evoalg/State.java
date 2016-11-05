package evoalg;

import lombok.Getter;
import evoalg.algorithm.Algorithm;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

@Getter
public class State<T extends Genotype<T>> {

  private Population<T> population;
  private final Algorithm<T> algorithm;
  private final T genotype;
  private int generationNo;

  public State(IEvaluate<T> ievaluate, T genotype, Algorithm<T> algorithm) {
    this.genotype = genotype;
    this.algorithm = algorithm;
    this.population = new Population<T>(ievaluate, genotype);
  }

  public void run() {
    generationNo = 0;

    population = algorithm.initializePopulation(population);

    while (generationNo < 100) {
      population = algorithm.advanceGeneration(population);
      generationNo++;
    }
  }
}
