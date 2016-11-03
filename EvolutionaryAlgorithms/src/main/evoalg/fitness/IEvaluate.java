package evoalg.fitness;

import evoalg.genotype.Genotype;

public interface IEvaluate<T extends Genotype<T>> {

  void evaluate(Fitness<T> fitness);

  Fitness<T> createFitness();
}
