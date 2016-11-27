package evoalg.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.fitness.FitnessMax;
import evoalg.fitness.FitnessMin;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;
import evoalg.random.IRandomness;
import evoalg.util.SearchUtil;

/**
 * Roulette wheel individual selection.
 * <p>
 * Algorithm is as follows: each member of the population is allocated a section of an
 * imaginary roulette wheel. Roulette wheel sections are different sizes, proportional
 * to the individual's fitness, such that the fittest candidate has the biggest slice
 * of the wheel and the weakest candidate has the smallest. The wheel is then spun and
 * the individual associated with the winning section is selected. The wheel is spun as many
 * times as is necessary to select the full set of parents for the next generation.
 * <p>
 * NOTE: should only be used with individuals that have specified <code>FitnessMax</code>
 * or <code>FitnessMin</code>
 *
 * @param <T> genotype
 */
public class RouletTeWheelSelection<T extends Genotype<T>> extends Algorithm<T> {

  private final IRandomness random;

  public RouletTeWheelSelection(Mutation<T> mutation, Crossover<T> crossover, IRandomness random) {
    super(mutation, crossover);
    this.random = random;
  }

  @Override
  public Deme<T> advanceGeneration(Deme<T> deme) {
    //create new list which we can manipulate (sorting, reversing order)
    List<Individual<T>> individuals = new ArrayList<>(deme.getIndividuals());

    //sort individuals by value, from higher value to lower
    individuals.sort((ind1, ind2) -> -Double.compare(ind1.getFitness().getValue(), ind2.getFitness().getValue()));

    List<Double> wheelSections = createRouletteWheelSections(individuals);
    List<Individual<T>> children = new ArrayList<>();

    IntStream.range(0, deme.size()).forEach(i -> {
      List<Individual<T>> parents = selectParents(wheelSections, individuals);
      children.add(createChild(parents));
    });

    return new Deme<>(children);
  }

  /**
   * Selects two parents from the list where the fitter an individual is the higher chance
   * it has to get selected, depending on the wheel sections.
   *
   * @param wheelSections wheel sections representing probability that one individual gets selected
   * @param individuals list of individuals
   * @return list with two individuals that represent parents
   */
  private List<Individual<T>> selectParents(List<Double> wheelSections, List<Individual<T>> individuals) {
    List<Individual<T>> parents = new ArrayList<>(2);
    IntStream.range(0, 2).forEach(i -> {
      //target wil be between 0 and the sum of all fitness values (which is last number of wheel sections)
      double target = random.nextDouble() * wheelSections.get(wheelSections.size() - 1);
      int index = SearchUtil.<Double>findFirstGreater(wheelSections, target);
      index--;
      parents.add(individuals.get(index));
    });
    return parents;
  }

  /**
   * Creates roulette wheel sections. Sections are different sizes, proportional
   * to the individual's fitness, such that the fittest candidate has the biggest
   * slice of the wheel and the weakest candidate has the smallest.
   *
   * @param individuals list of individuals
   * @return list of values that represent roulette wheel sections
   */
  private List<Double> createRouletteWheelSections(List<Individual<T>> individuals) {
    List<Double> wheelSections = new ArrayList<>();
    wheelSections.add(0d);
    if (individuals.get(0).getFitness() instanceof FitnessMax) {
      individuals.forEach(ind -> wheelSections.add(wheelSections.get(wheelSections.size() - 1) + ind.getFitness().getValue()));
    }
    else if (individuals.get(0).getFitness() instanceof FitnessMin) {
      //when fittest candidate has lowest value then we need to reverse sorted individuals because by default higher
      //fitness value gets bigger wheel section
      individuals = new ArrayList<>(individuals);
      Collections.reverse(individuals);
      individuals.forEach(ind -> wheelSections.add(wheelSections.get(wheelSections.size() - 1) + ind.getFitness().getValue()));
    }
    else {
      throw new IllegalArgumentException("RouletTeWheelSelection can only be used with individuals that have either " +
          "FitnessMax or FitnessMin specified.");
    }
    return wheelSections;
  }

  /**
   * Mates two parents to get a child, that we then mutate, evaluate and return.
   *
   * @param parents two parents
   * @return new child
   */
  private Individual<T> createChild(List<Individual<T>> parents) {
    Individual<T> child = mate(parents.get(0), parents.get(1));
    child = mutate(child);
    child = child.evaluate();
    return child;
  }

}
