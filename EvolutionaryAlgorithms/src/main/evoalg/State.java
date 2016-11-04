package evoalg;

import java.util.ArrayList;
import java.util.List;

import evoalg.algorithm.Algorithm;
import evoalg.fitness.Fitness;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;

public class State<T extends Genotype<T>> {

  private Population<T> population;
  private Algorithm<T> algorithm;
  private Fitness<T> fitness;
  private Crossover<T> crossover;
  private Mutation<T> mutation;
  private List<T> genotype;
  private int generationNo;

  public State(IEvaluate<T> ievaluate) {
    population = new Population<T>(this, ievaluate);
    crossover = new Crossover<T>(this);
    mutation = new Mutation<T>(this);
    fitness = ievaluate.createFitness();
    genotype = new ArrayList<>();
  }

  public void initialize(String[] args) {
    // algorithm.setCrossover(crossover);
    // algorithm.setMutation(mutation);

    population.initialize();
    crossover.initialize();
    mutation.initialize();
  }

  public void run() {
    generationNo = 0;

    algorithm.initializePopulation();

    while (generationNo < 100) {
      algorithm.advanceGeneration();
      generationNo++;
    }
  }

  public int getGenerationNo() {
    return generationNo;
  }

  public Population<T> getPopulation() {
    return population;
  }

  public Algorithm<T> getAlgorithm() {
    return algorithm;
  }

  public Fitness<T> getFitness() {
    return fitness;
  }

  public List<T> getGenotypes() {
    return genotype;
  }
}
