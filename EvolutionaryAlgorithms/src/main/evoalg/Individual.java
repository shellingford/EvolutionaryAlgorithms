package evoalg;

import lombok.Getter;
import evoalg.fitness.Fitness;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

/**
 * Class represents an individual inside a deme.
 *
 * Individuals has a certain genotype and fitness.
 *
 * @param <T> genotype
 */
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

  /**
   * Deep copy of current individual.
   *
   * @return individual's deep copy.
   */
  public Individual<T> copy() {
    if (fitness != null) {
      return new Individual<T>(fitness.copy(), ievaluate, genotype.copy());
    }
    else {
      return new Individual<T>(ievaluate, genotype.copy());
    }
  }

  /**
   * Evaluates current individual and returns new instance of it with fitness value.
   *
   * @return evaluated individual
   */
  public Individual<T> evaluate() {
    Fitness<T> newFitness = ievaluate.evaluate(this);
    return new Individual<T>(newFitness, ievaluate, genotype.copy());
  }

  @Override
  public String toString() {
    return "Individual [genotype=" + genotype + " => fitness value:" + (getFitness() != null ? getFitness().getValue() + "" : "none") + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fitness == null) ? 0 : fitness.hashCode());
    result = prime * result + ((genotype == null) ? 0 : genotype.hashCode());
    return result;
  }

  @SuppressWarnings("rawtypes")
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
    Individual other = (Individual) obj;
    if (fitness == null) {
      if (other.fitness != null) {
        return false;
      }
    }
    else if (!fitness.equals(other.fitness)) {
      return false;
    }
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
}
