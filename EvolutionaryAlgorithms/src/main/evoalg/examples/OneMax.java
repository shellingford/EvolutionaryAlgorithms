package evoalg.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import evoalg.Individual;
import evoalg.Population;
import evoalg.State;
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
import evoalg.genotype.bitstring.BitStringMutSimple;
import evoalg.selection.SelBestOp;
import evoalg.selection.SelectionOperator;

/**
 * Simple example for BitString genotype where the goal is to get
 * a bitstring individual with most 1s in it.
 */
public class OneMax implements IEvaluate<BitString>, IMilestone<BitString> {
  private static final long serialVersionUID = 1L;

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
    return false;
  }

  public void start() {
    State<BitString> state = setupState();
    state.run();

    SelectionOperator<BitString> selector = new SelBestOp<BitString>();
    List<Individual<BitString>> allIndividuals = state.getPopulation().stream().flatMap(deme -> deme.stream()).collect(Collectors.toList());
    System.out.println("Best individual after last generation: " + selector.select(allIndividuals));
  }

  private State<BitString> setupState() {
    BitString genotype = new BitString(10);
    Population<BitString> population = setupPopulation(genotype);
    Algorithm<BitString> algorithm = setupAlgorithm();

    State<BitString> state = new State<>(genotype, algorithm, population, this, this);
    return state;
  }

  private Population<BitString> setupPopulation(BitString genotype) {
    int populationSize = 5;
    int demeSize = 5;
    return new Population<>(this, genotype, populationSize, demeSize);
  }

  private Algorithm<BitString> setupAlgorithm() {
    List<CrossoverOp<BitString>> crxOperators = Arrays.asList(new BitStringCrsOnePoint());
    List<MutationOp<BitString>> mutOperators = Arrays.asList(new BitStringMutSimple());
    Crossover<BitString> crossover = new Crossover<>(crxOperators);
    Mutation<BitString> mutation = new Mutation<>(mutOperators);

    int tournamentSize = 4;
    return new SteadyStateTournament<>(mutation, crossover, tournamentSize);
  }

  public static void main(String[] args) {
    OneMax test = new OneMax();
    test.start();
  }

}
