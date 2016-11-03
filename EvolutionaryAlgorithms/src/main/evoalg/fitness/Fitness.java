package evoalg.fitness;

import evoalg.Individual;
import evoalg.genotype.Genotype;

public abstract class Fitness<T extends Genotype<T>> implements Comparable<Fitness<T>> {

  private boolean valid = true;

  private double value;

  private Individual<T> individual;

  public abstract Fitness<T> copy();

  public void evaluate(IEvaluate<T> ievaluate) {
    ievaluate.evaluate(this);
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public Individual<T> getIndividual() {
    return individual;
  }

  public void setIndividual(Individual<T> individual) {
    this.individual = individual;
  }

}
