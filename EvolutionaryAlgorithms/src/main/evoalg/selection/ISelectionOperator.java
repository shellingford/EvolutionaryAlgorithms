package evoalg.selection;

import java.util.List;

import evoalg.Individual;
import evoalg.genotype.Genotype;

/**
 * Selection operator interface. Every implementation of selection operator
 * must implement this interface.
 *
 * @param <T> genotype
 */
public interface ISelectionOperator<T extends Genotype<T>> {

  /**
   * Selects an individual from the pool.
   *
   * @param pool pool of individuals
   * @return single individual from the pool
   */
  Individual<T> select(List<Individual<T>> pool);
}
