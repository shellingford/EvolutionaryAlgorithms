package evoalg;

import java.util.ArrayList;

import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

public class Deme<T extends Genotype<T>> extends ArrayList<Individual<T>> {

  private static final long serialVersionUID = 1L;

  private IEvaluate<T> ievaluate;
  private int nIndividuals = 1;

  public Deme(IEvaluate<T> ievaluate) {
    this.ievaluate = ievaluate;
  }

  public Deme(IEvaluate<T> ievaluate, int brojJedinki) {
    this.ievaluate = ievaluate;
    this.nIndividuals = brojJedinki;
  }

  public void initialize(State<T> state) {
    for (int i = 0; i < nIndividuals; i++) {
      Individual<T> ind = new Individual<T>(ievaluate, state);
      ind.setIndex(size());
      ind.initialize();
      add(ind);
    }
  }

  public void replace(int index, Individual<T> newInd) {
    newInd.setIndex(index);
    set(index, newInd);
  }

  public int getSize() {
    return nIndividuals;
  }

  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    for (int i = 0; i < size(); i++) {
      s.append("Jedinka " + i + ": " + get(i).toString() + "\n");
    }
    return s.toString();
  }
}
