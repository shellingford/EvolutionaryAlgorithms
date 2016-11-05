package evoalg.fitness;

import evoalg.Individual;
import evoalg.genotype.Genotype;

/**
 *  Main interface for implementing fitness evaluation function for individuals
 *  of a certain genotype.
 *
 *  User of this framework will have to implement their evaluation function.
 *
 * @param <T> individual's genotype
 */
public interface IEvaluate<T extends Genotype<T>> {

  /**
   * Fitness evaluation function.
   *
   * Should use individual's data to produce a single double value.
   *
   * @param fitness individual's fitness comparator instance
   * @return individual's fitness value
   */
  double evaluate(Individual<T> individual);
}
