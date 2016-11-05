package evoalg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import evoalg.fitness.Fitness;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

@Getter
@AllArgsConstructor
public class Individual<T extends Genotype<T>> {

  private final Fitness<T> fitness;
  private final IEvaluate<T> ievaluate;
  private final T genotype;

  public Individual(IEvaluate<T> ievaluate, T genotype) {
    this.ievaluate = ievaluate;
    this.genotype = genotype;
    this.fitness = null;
  }

  public Individual<T> copy() {
    return new Individual<T>(fitness.copy(), ievaluate, genotype.copy());
  }

  public Individual<T> evaluate() {
    Fitness<T> newFitness = fitness.evaluate(ievaluate);
    return new Individual<T>(newFitness, ievaluate, genotype.copy());
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(genotype.toString() + ", fitness: " + fitness + "\t");
    return s.toString();
  }
}
