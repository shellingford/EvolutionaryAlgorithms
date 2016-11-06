package evoalg.fitness;

import java.io.Serializable;

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
public interface IEvaluate<T extends Genotype<T>> extends Serializable {

  /**
   * Fitness evaluation function.
   *
   * Should use individual's data to produce a single double value and return
   * Fitness instance with the calculated value.
   *
   * @param fitness individual's fitness comparator instance
   * @return individual's fitness
   */
  Fitness<T> evaluate(Individual<T> individual);
}
