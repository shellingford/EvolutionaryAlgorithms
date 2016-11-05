package evoalg.fitness;

import evoalg.genotype.Genotype;

public interface IEvaluate<T extends Genotype<T>> {

  double evaluate(Fitness<T> fitness);
}
