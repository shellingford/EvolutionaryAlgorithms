package evoalg.selection;

import java.util.List;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public class SelWorstOp<T extends Genotype<T>> extends SelectionOperator<T> {

  @Override
  public Individual<T> select(List<Individual<T>> pool) {
    return pool.stream().sorted((ind1, ind2) -> ind1.getFitness().compareTo(ind2.getFitness()))
               .reduce((a, b) -> b).orElse(null);
  }
}
