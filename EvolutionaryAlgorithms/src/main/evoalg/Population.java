package evoalg;

import java.util.ArrayList;

import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

public class Population<T extends Genotype<T>> extends ArrayList<Deme<T>> {

  private static final long serialVersionUID = 1L;
  private IEvaluate<T> ievaluate;
  private int nDemes;
  private int myDemeIndex;
  private int nIndividuals;
  private State<T> state;

  public Population(State<T> state, IEvaluate<T> ievaluate) {
    this.state = state;
    this.ievaluate = ievaluate;
  }

  public void initialize() {
    for (int i = 0; i < nDemes; i++) {
      Deme<T> deme = new Deme<T>(ievaluate, nIndividuals);
      deme.initialize(state);
      add(deme);
    }
  }

  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
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
