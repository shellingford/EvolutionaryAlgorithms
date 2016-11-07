package evoalg.selection;

import java.util.List;
import java.util.Random;

import evoalg.Individual;
import evoalg.genotype.Genotype;

/**
 * Implementation of SelectionOperator that selects a random individual from the pool.
 *
 * @param <T> genotype
 */
public class SelRandomOp<T extends Genotype<T>> implements ISelectionOperator<T> {

  private Random random;

  public SelRandomOp() {
    random = new Random();
  }

  @Override
  public Individual<T> select(List<Individual<T>> pool) {
    return pool.get(random.nextInt(pool.size()));
  }
}
