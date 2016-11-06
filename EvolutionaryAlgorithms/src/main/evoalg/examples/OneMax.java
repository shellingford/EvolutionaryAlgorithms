package evoalg.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import evoalg.Individual;
import evoalg.State;
import evoalg.algorithm.Algorithm;
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

public class OneMax implements IEvaluate<BitString> {
  private static final long serialVersionUID = 1L;

  @Override
  public Fitness<BitString> evaluate(Individual<BitString> individual) {
    BitString bitstring = individual.getGenotype();
    double value = bitstring.getData().stream().filter(b -> b == (byte) 1).count();
    return new FitnessMax<BitString>(value);
  }

  public void start() {
    BitString genotype = new BitString(10);

    List<CrossoverOp<BitString>> crxOperators = Arrays.asList(new BitStringCrsOnePoint());
    List<MutationOp<BitString>> mutOperators = Arrays.asList(new BitStringMutSimple());
    Crossover<BitString> crossover = new Crossover<>(crxOperators);
    Mutation<BitString> mutation = new Mutation<>(mutOperators);

    int tournamentSize = 4;
    Algorithm<BitString> algorithm = new SteadyStateTournament<>(mutation, crossover, tournamentSize);

    int populationSize = 5;
    int demeSize = 5;
    State<BitString> state = new State<>(genotype, algorithm, populationSize, demeSize, this);
    state.run();

    SelectionOperator<BitString> selector = new SelBestOp<BitString>();
    List<Individual<BitString>> allIndividuals = state.getPopulation().stream().flatMap(deme -> deme.stream()).collect(Collectors.toList());
    System.out.println("Best individual after last generation: " + selector.select(allIndividuals));
  }

  public static void main(String[] args) {
    OneMax test = new OneMax();
    test.start();
  }

}
