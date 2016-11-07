package evoalg.selection;

import java.util.List;

import evoalg.Individual;
import evoalg.genotype.Genotype;

/**
 * Implementation of SelectionOperator that selects the worst individual from the pool.
 *
 * @param <T> genotype
 */
public class SelWorstOp<T extends Genotype<T>> implements ISelectionOperator<T> {

  @Override
  public Individual<T> select(List<Individual<T>> pool) {
    //after sort best individual is last in the list
    return pool.stream().sorted((ind1, ind2) -> ind1.getFitness().compareTo(ind2.getFitness()))
               .reduce((a, b) -> b).orElse(null);
  }
}
