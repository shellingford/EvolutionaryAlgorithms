package evoalg;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

/**
 * Deme is a collection of {@link Individual} of a specified genotype. Default deme size is 1. <p>

 * {@link Population} can contain one or more demes.
 *
 * @param <T> genotype
 */
public class Deme<T extends Genotype<T>> {

  private static final int DEFAULT_SIZE = 1;
  @Getter
  private final List<Individual<T>> individuals;

  private final int nIndividuals;

  public Deme(IEvaluate<T> ievaluate, T genotype) {
    this.nIndividuals = DEFAULT_SIZE;
    individuals = ImmutableList.copyOf(initialize(genotype, ievaluate));
  }

  public Deme(IEvaluate<T> ievaluate, int nIndividuals, T genotype) {
    this.nIndividuals = nIndividuals;
    individuals = ImmutableList.copyOf(initialize(genotype, ievaluate));
  }

  public Deme(List<Individual<T>> individuals) {
    this.nIndividuals = individuals.size();
    this.individuals = ImmutableList.copyOf(individuals);
  }

  /**
   * Initializes individuals with random data.
   *
   * @param genotype genotype
   * @param ievaluate evaluation function
   */
  private List<Individual<T>> initialize(T genotype, IEvaluate<T> ievaluate) {
    return IntStream.range(0, nIndividuals).mapToObj(i -> new Individual<T>(ievaluate, genotype.initializeData()))
                    .collect(Collectors.toList());
  }

  /**
   * Evaluates all individuals inside this deme.
   *
   * @return deme with evaluated individuals
   */
  public Deme<T> evaluate() {
    List<Individual<T>> evaluatedIndividuals = individuals.stream().map(ind -> ind.evaluate()).collect(Collectors.toList());
    return new Deme<>(evaluatedIndividuals);
  }

  public int size() {
    return nIndividuals;
  }

}
