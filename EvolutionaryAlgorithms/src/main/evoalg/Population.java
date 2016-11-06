package evoalg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

@Getter
public class Population<T extends Genotype<T>> extends ArrayList<Deme<T>> {

  private static final long serialVersionUID = 1L;

  private final IEvaluate<T> ievaluate;
  private final int demeSize;

  public Population(IEvaluate<T> ievaluate, T genotype, int populationSize, int demeSize) {
    super(populationSize);
    this.ievaluate = ievaluate;
    this.demeSize = demeSize;

    initialize(populationSize, genotype);
  }

  public Population(IEvaluate<T> ievaluate, int demeSize, List<Deme<T>> demes) {
    super(demes.size());
    this.ievaluate = ievaluate;
    this.demeSize = demeSize;

    addAll(demes);
  }

  private void initialize(int populationSize, T genotype) {
    for (int i = 0; i < populationSize; i++) {
      Deme<T> deme = new Deme<T>(ievaluate, demeSize, genotype);
      add(deme);
    }
  }

  public Population<T> reset(T genotype) {
    return new Population<>(ievaluate, genotype, size(), demeSize);
  }

  public Population<T> evaluate() {
    List<Deme<T>> evaluatedDemes = this.stream().map(deme -> deme.evaluate()).collect(Collectors.toList());
    return new Population<>(ievaluate, demeSize, evaluatedDemes);
  }

}
