package evoalg;

import java.util.ArrayList;
import java.util.List;
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
  private final int nIndividuals;

  public Deme(IEvaluate<T> ievaluate, T genotype) {
    super(DEFAULT_SIZE);
    this.ievaluate = ievaluate;
    this.nIndividuals = DEFAULT_SIZE;
    initialize(genotype);
  }

  public Deme(IEvaluate<T> ievaluate, int nIndividuals, T genotype) {
    super(nIndividuals);
    this.ievaluate = ievaluate;
    this.nIndividuals = nIndividuals;
    initialize(genotype);
  }

  public Deme(IEvaluate<T> ievaluate, List<Individual<T>> individuals) {
    super(individuals.size());
    this.ievaluate = ievaluate;
    this.nIndividuals = individuals.size();
    addAll(individuals);
  }

  private void initialize(T genotype) {
    IntStream.range(0, nIndividuals).forEach(i -> add(new Individual<T>(ievaluate, genotype.initializeData())));
  }

  public Deme<T> evaluate() {
    List<Individual<T>> evaluatedIndividuals = this.stream().map(ind -> ind.evaluate()).collect(Collectors.toList());
    Deme<T> deme = new Deme<>(ievaluate, evaluatedIndividuals);
    return deme;
  }
}
