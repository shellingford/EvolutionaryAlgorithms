package evoalg.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import evoalg.Individual;
import evoalg.Population;
import evoalg.State;
import evoalg.SystemTime;
import evoalg.algorithm.Algorithm;
import evoalg.algorithm.IMilestone;
import evoalg.algorithm.SteadyStateTournament;
import evoalg.fitness.Fitness;
import evoalg.fitness.FitnessMax;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Crossover;
import evoalg.genotype.CrossoverOp;
import evoalg.genotype.Mutation;
import evoalg.genotype.MutationOp;
import evoalg.genotype.bitstring.BitString;
import evoalg.genotype.bitstring.BitStringCrsOnePoint;
import evoalg.genotype.bitstring.BitStringMutMix;
import evoalg.random.DefaultRandom;
import evoalg.selection.SelBestOp;
import evoalg.selection.SelRandomOp;
import evoalg.selection.SelWorstOp;

/**
 * Simple example for BitString genotype where the goal is to get
 * a bitstring individual with most 1s in it.
 */
public class OneMax implements IEvaluate<BitString>, IMilestone<BitString> {
  private static final int BITSTRING_SIZE = 15;

  @Override
  public Fitness<BitString> evaluate(Individual<BitString> individual) {
    BitString bitstring = individual.getGenotype();
    //count 1s in the bitstring
    double value = bitstring.getData().stream().filter(b -> b == (byte) 1).count();
    //use FitnessMax so that best individual will have most 1s
    return new FitnessMax<BitString>(value);
  }

  @Override
  public boolean reached(Population<BitString> population, int generationNo, long duration) {
    if (generationNo > 30) {
      System.out.println("Algorithm stopped after 30 generations.");
      return true;
    }
    //duration is in milliseconds
    if (duration > 5 * 1000) {
      System.out.println("Algorithm stopped after 5 seconds.");
      return true;
    }
    //check if there is an individual within population with best possible fitness value
    if (population.getDemes().stream().flatMap(deme -> deme.getIndividuals().stream())
                  .filter(ind -> ind.getFitness().getValue() == BITSTRING_SIZE).count() > 0) {
      System.out.println(String.format("Found best possible individual after %s generations!", generationNo));
      return true;
    }
    return false;
  }

  public void start() {
    State<BitString> state = setupState();
    state.run();

    SelBestOp<BitString> selector = new SelBestOp<BitString>();
    List<Individual<BitString>> allIndividuals =
        state.getPopulation().getDemes().stream().flatMap(deme -> deme.getIndividuals().stream()).collect(Collectors.toList());
    System.out.println("Best individual after last generation: " + selector.select(allIndividuals));
  }

  /**
   * Setups genotype, population, algorithm instance and then with that creates
   * state instance that will be used to start everything.
   *
   * @return fully setup state instance
   */
  private State<BitString> setupState() {
    BitString genotype = new BitString(BITSTRING_SIZE);
    Population<BitString> population = setupPopulation(genotype);
    Algorithm<BitString> algorithm = setupAlgorithm();

    State<BitString> state = new State<>(genotype, algorithm, population, this, this, new SystemTime());
    return state;
  }

  /**
   * Setup population and deme size and create population instance.
   *
   * @param genotype individual's genotype
   * @return fully setup population
   */
  private Population<BitString> setupPopulation(BitString genotype) {
    int populationSize = 5;
    int demeSize = 5;
    return new Population<>(this, genotype, populationSize, demeSize);
  }

  /**
   * Setup crossover and mutation operators, choose and create algorithm instance.
   *
   * @return fully setup algorithm instance
   */
  private Algorithm<BitString> setupAlgorithm() {
    List<CrossoverOp<BitString>> crxOperators = Arrays.asList(new BitStringCrsOnePoint(new DefaultRandom()));
    List<MutationOp<BitString>> mutOperators = Arrays.asList(new BitStringMutMix(new DefaultRandom()));
    Crossover<BitString> crossover = new Crossover<>(crxOperators, new DefaultRandom());
    Mutation<BitString> mutation = new Mutation<>(mutOperators, 0.35d, new DefaultRandom());

    int tournamentSize = 4;
    return new SteadyStateTournament<>(mutation, crossover, tournamentSize, new SelRandomOp<BitString>(),
          new SelWorstOp<BitString>());
  }

  public static void main(String[] args) {
    OneMax test = new OneMax();
    test.start();
  }

}
