package evoalg.selection;

import java.util.List;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public class SelBestOp<T extends Genotype<T>> extends SelectionOperator<T> {

  @Override
  public Individual<T> select(List<Individual<T>> pool) {
    Individual<T> najbolji = pool.get(0);
    for (int i = 1; i < pool.size(); i++) {
      Individual<T> jedinka = pool.get(i);
      if (jedinka.getFitness().compareTo(najbolji.getFitness()) > 0) {
        najbolji = jedinka;
      }
    }
    return najbolji;
  }

}
