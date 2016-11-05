package evoalg;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

public class Deme<T extends Genotype<T>> extends ArrayList<Individual<T>> {

  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_SIZE = 1;

  @Getter
  private final IEvaluate<T> ievaluate;
  @Getter
  private final T genotype;
  private final int nIndividuals;

  public Deme(IEvaluate<T> ievaluate, T genotype) {
    super(DEFAULT_SIZE);
    this.ievaluate = ievaluate;
    this.nIndividuals = DEFAULT_SIZE;
    this.genotype = genotype;
    initialize();
  }

  public Deme(IEvaluate<T> ievaluate, int nIndividuals, T genotype) {
    super(nIndividuals);
    this.ievaluate = ievaluate;
    this.nIndividuals = nIndividuals;
    this.genotype = genotype;
    initialize();
  }

  private void initialize() {
    IntStream.range(0, nIndividuals).forEach(i -> add(new Individual<T>(ievaluate, genotype)));
  }

  public void replace(int index, Individual<T> newInd) {
    set(index, newInd);
  }

  public Deme<T> evaluate() {
    Deme<T> evaluatedDeme = new Deme<>(ievaluate, nIndividuals, genotype);
    evaluatedDeme.addAll(this.stream().map(ind -> ind.evaluate()).collect(Collectors.toList()));
    return evaluatedDeme;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      s.append("Jedinka " + i + ": " + get(i).toString() + "\n");
    }
    return s.toString();
  }
}
