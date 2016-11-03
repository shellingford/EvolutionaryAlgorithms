package evoalg.selection;

import java.util.List;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public class SelWorstOp<T extends Genotype<T>> extends SelectionOperator<T> {

  @Override
  public Individual<T> select(List<Individual<T>> pool) {
    Individual<T> worst = pool.get(0);
    for (int i = 1; i < pool.size(); i++) {
      Individual<T> individual = pool.get(i);
      if (individual.getFitness().compareTo(worst.getFitness()) < 0) {
        worst = individual;
      }
    }
    return worst;
  }
}
