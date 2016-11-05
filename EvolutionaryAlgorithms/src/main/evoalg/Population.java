package evoalg;

import java.util.ArrayList;

import lombok.Getter;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

public class Population<T extends Genotype<T>> extends ArrayList<Deme<T>> {

  private static final long serialVersionUID = 1L;

  @Getter
  private IEvaluate<T> ievaluate;
  @Getter
  private final T genotype;
  private int nDemes;
  private int myDemeIndex;
  private int nIndividuals;

  public Population(IEvaluate<T> ievaluate, T genotype) {
    this.ievaluate = ievaluate;
    this.genotype = genotype;

    initialize();
  }

  private void initialize() {
    for (int i = 0; i < nDemes; i++) {
      Deme<T> deme = new Deme<T>(ievaluate, nIndividuals, genotype);
      add(deme);
    }
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      s.append("Deme " + i + ":\n" + get(i) + "\n");
    }
    return s.toString();
  }

  public int getNoDemes() {
    return nDemes;
  }

  public int getMyDemeIndex() {
    return myDemeIndex;
  }
}
