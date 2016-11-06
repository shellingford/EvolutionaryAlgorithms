package evoalg;

import lombok.Getter;
import evoalg.fitness.Fitness;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

@Getter
public class Individual<T extends Genotype<T>> {

  private final Fitness<T> fitness;
  private final IEvaluate<T> ievaluate;
  private final T genotype;

  public Individual(Fitness<T> fitness, IEvaluate<T> ievaluate, T genotype) {
    this.ievaluate = ievaluate;
    this.fitness = fitness;
    this.genotype = genotype;
  }

  public Individual(IEvaluate<T> ievaluate, T genotype) {
    this.ievaluate = ievaluate;
    this.genotype = genotype;
    this.fitness = null;
  }

  public Individual<T> copy() {
    return new Individual<T>(fitness.copy(), ievaluate, genotype.copy());
  }

  public Individual<T> evaluate() {
    Fitness<T> newFitness = ievaluate.evaluate(this);
    return new Individual<T>(newFitness, ievaluate, genotype.copy());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((genotype == null) ? 0 : genotype.hashCode());
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Individual<T> other = (Individual<T>) obj;
    if (genotype == null) {
      if (other.genotype != null) {
        return false;
      }
    }
    else if (!genotype.equals(other.genotype)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Individual [genotype=" + genotype + " => fitness value:" + (getFitness() != null ? getFitness().getValue() + "" : "none") + "]";
  }
}
