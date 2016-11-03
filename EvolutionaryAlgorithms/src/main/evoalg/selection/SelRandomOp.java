package evoalg.selection;

import java.util.List;
import java.util.Random;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public class SelRandomOp<T extends Genotype<T>> extends SelectionOperator<T> {

  private Random random;

  public SelRandomOp() {
    random = new Random();
  }

  @Override
  public Individual<T> select(List<Individual<T>> pool) {
    return pool.get(random.nextInt(pool.size()));
  }
}
