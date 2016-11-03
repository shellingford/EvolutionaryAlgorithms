package evoalg;

import java.util.ArrayList;

import evoalg.fitness.Fitness;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

public class Individual<T extends Genotype<T>> extends ArrayList<T> {

  private static final long serialVersionUID = 1L;

  private Fitness<T> fitness;
  private int index;
  private IEvaluate<T> ievaluate;
  private State<T> state;

  public Individual(IEvaluate<T> ievaluate, State<T> state) {
    this.state = state;
    this.ievaluate = ievaluate;
  }

  public void initialize() {
    for (int i = 0; i < state.getGenotypes().size(); i++) {
      T gen = state.getGenotypes().get(i).copy();
      gen.initialize();
      add(gen);

    }
  }

  public Individual<T> copy() {
    Individual<T> novi = new Individual<T>(ievaluate, state);

    if (fitness != null) {
      novi.fitness = fitness.copy();
    }
    for (int i = 0; i < size(); i++) {
      novi.add(get(i).copy());
    }
    return novi;
  }

  public T getGenotype(int index) {
    return get(index);
  }

  public void evaluate() {
    fitness.evaluate(ievaluate);
  }

  public Fitness<T> getFitness() {
    return fitness;
  }

  public void setFitness(Fitness<T> fitness) {
    this.fitness = fitness;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    for (T gen : this) {
      String f = "null";
      if (fitness != null) {
        f = String.valueOf(fitness.getValue());
      }
      s.append(gen.toString() + ", fitness: " + f + "\t");
    }
    return s.toString();
  }
}
