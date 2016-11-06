package evoalg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

/**
 * Population represents a collection of Demes which is why it extends ArrayList
 * of Demes.
 *
 * @param <T> genotype
 */
@Getter
public class Population<T extends Genotype<T>> extends ArrayList<Deme<T>> {

  private static final long serialVersionUID = 1L;

  private final int demeSize;

  public Population(IEvaluate<T> ievaluate, T genotype, int populationSize, int demeSize) {
    super(populationSize);
    this.demeSize = demeSize;

    initialize(populationSize, genotype, ievaluate);
  }

  public Population(int demeSize, List<Deme<T>> demes) {
    super(demes.size());
    this.demeSize = demeSize;

    addAll(demes);
  }

  /**
   * Initializes population with specified number of demes inside it.
   *
   * @param populationSize number of demes inside population
   * @param genotype individual's genotype
   * @param ievaluate evaluation function
   */
  private void initialize(int populationSize, T genotype, IEvaluate<T> ievaluate) {
    for (int i = 0; i < populationSize; i++) {
      Deme<T> deme = new Deme<T>(ievaluate, demeSize, genotype);
      add(deme);
    }
  }

  /**
   * Resets population's demes.
   *
   * @param genotype genotype
   * @param ievaluate evaluation function
   * @return new population with reset demes
   */
  public Population<T> reset(T genotype, IEvaluate<T> ievaluate) {
    return new Population<>(ievaluate, genotype, size(), demeSize);
  }

  /**
   * Evaluates all individuals inside all demes that this population contains.
   *
   * @return updated population
   */
  public Population<T> evaluate() {
    List<Deme<T>> evaluatedDemes = this.stream().map(deme -> deme.evaluate()).collect(Collectors.toList());
    return new Population<>(demeSize, evaluatedDemes);
  }

}
