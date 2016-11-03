package evoalg.selection;

import java.util.List;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public abstract class SelectionOperator<T extends Genotype<T>> {

  public abstract Individual<T> select(List<Individual<T>> pool);
}
