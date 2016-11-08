package evoalg;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

/**
 * Population represents a collection of {@link Deme}.
 *
 * @param <T> genotype
 */
public class Population<T extends Genotype<T>> {

  private final int demeSize;
  @Getter
  private final List<Deme<T>> demes;

  public Population(IEvaluate<T> ievaluate, T genotype, int populationSize, int demeSize) {
    this.demeSize = demeSize;
    demes = ImmutableList.copyOf(initialize(populationSize, genotype, ievaluate));
  }

  public Population(int demeSize, List<Deme<T>> demes) {
    this.demeSize = demeSize;
    this.demes = ImmutableList.copyOf(demes);
  }

  /**
   * Initializes population with specified number of demes inside it.
   *
   * @param populationSize number of demes inside population
   * @param genotype individual's genotype
   * @param ievaluate evaluation function
   */
  private List<Deme<T>> initialize(int populationSize, T genotype, IEvaluate<T> ievaluate) {
    return IntStream.range(0, populationSize).mapToObj(i -> new Deme<T>(ievaluate, demeSize, genotype))
        .collect(Collectors.toList());
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
    List<Deme<T>> evaluatedDemes = demes.stream().map(deme -> deme.evaluate()).collect(Collectors.toList());
    return new Population<>(demeSize, evaluatedDemes);
  }

  public int size() {
    return demes.size();
  }

}
