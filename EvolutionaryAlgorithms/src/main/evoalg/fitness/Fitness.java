package evoalg.fitness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import evoalg.Individual;
import evoalg.genotype.Genotype;

@Getter
@AllArgsConstructor
public abstract class Fitness<T extends Genotype<T>> implements Comparable<Fitness<T>> {

  private Individual<T> individual;
  private double value;

  public abstract Fitness<T> copy();
  public abstract Fitness<T> copy(double value);

  public Fitness<T> evaluate(IEvaluate<T> ievaluate) {
    value = ievaluate.evaluate(this);
    return copy(value);
  }

}
