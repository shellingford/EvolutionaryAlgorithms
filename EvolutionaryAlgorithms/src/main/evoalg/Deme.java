package evoalg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.fitness.IEvaluate;
import evoalg.genotype.Genotype;

/**
 * Deme is a collection of individuals which is why it extends ArrayList of
 * individuals of a specified genotype.
 *
 * Population can contain one or more demes.
 *
 * @param <T> genotype
 */
public class Deme<T extends Genotype<T>> extends ArrayList<Individual<T>> {

  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_SIZE = 1;

  private final int nIndividuals;

  public Deme(IEvaluate<T> ievaluate, T genotype) {
    super(DEFAULT_SIZE);
    this.nIndividuals = DEFAULT_SIZE;
    initialize(genotype, ievaluate);
  }

  public Deme(IEvaluate<T> ievaluate, int nIndividuals, T genotype) {
    super(nIndividuals);
    this.nIndividuals = nIndividuals;
    initialize(genotype, ievaluate);
  }

  public Deme(List<Individual<T>> individuals) {
    super(individuals.size());
    this.nIndividuals = individuals.size();
    addAll(individuals);
  }

  /**
   * Initializes individuals with random data.
   *
   * @param genotype genotype
   * @param ievaluate evaluation function
   */
  private void initialize(T genotype, IEvaluate<T> ievaluate) {
    IntStream.range(0, nIndividuals).forEach(i -> add(new Individual<T>(ievaluate, genotype.initializeData())));
  }

  /**
   * Evaluates all individuals inside this deme.
   *
   * @return deme with evaluated individuals
   */
  public Deme<T> evaluate() {
    List<Individual<T>> evaluatedIndividuals = this.stream().map(ind -> ind.evaluate()).collect(Collectors.toList());
    Deme<T> deme = new Deme<>(evaluatedIndividuals);
    return deme;
  }
}
