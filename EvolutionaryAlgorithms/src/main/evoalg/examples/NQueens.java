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
import evoalg.genotype.permutation.Permutation;
import evoalg.genotype.permutation.PermutationCrxOX;
import evoalg.genotype.permutation.PermutationCrxPMX;
import evoalg.genotype.permutation.PermutationMutInv;
import evoalg.genotype.permutation.PermutationMutRot;
import evoalg.genotype.permutation.PermutationMutToggle;
import evoalg.random.DefaultRandom;
import evoalg.random.IRandomness;
import evoalg.selection.SelBestOp;
import evoalg.selection.SelRandomOp;
import evoalg.selection.SelWorstOp;
import evoalg.termination.ITerminationOperator;
import evoalg.termination.MaxGenTermOp;

/**
 * N-queens problem is problem of placing N queens on the NxN chess board so that no two queens
 * threaten each other. Thus, a solution requires that no two queens share the same row, column,
 * or diagonal.<p>
 *
 * Permutation genotype is pretty convenient for solving this problem, as we just need to check
 * for diagonal collisions of queens. That is because position in the list represents column
 * on the chess board, and number represents row, so if we don't have duplicate numbers in the
 * list and only one list, we automatically make row or column collisions impossible.
 */
public class NQueens implements IMilestone<Permutation>, IEvaluate<Permutation> {
  private static final int PERMUTATION_SIZE = 12;

  private final ITerminationOperator<Permutation> maxGenTermOp = new MaxGenTermOp<Permutation>(3000);

  @Override
  public Fitness<Permutation> evaluate(Individual<Permutation> individual) {
    Permutation permutation = individual.getGenotype();

    List<Integer> data = permutation.getData();
    int noCollisions = 0;
    for (int i = 0; i < permutation.size() - 2; i++) {
      for (int j = i + 1; j < permutation.size() - 1; j++) {
        if (Math.abs(data.get(i) - data.get(j)) == (j - i)) {
          noCollisions++;
        }
      }
    }

    return new FitnessMax<Permutation>(permutation.size() - noCollisions);
  }

  @Override
  public boolean reached(Population<Permutation> population, int generationNo, long duration) {
    return maxGenTermOp.shouldTerminate(population, generationNo, duration);
  }

  public void start() {
    State<Permutation> state = setupState();
    long start = System.currentTimeMillis();
    state.run();
    System.out.println("Duration: " + (System.currentTimeMillis() - start) + "ms");

    SelBestOp<Permutation> selector = new SelBestOp<Permutation>();
    List<Individual<Permutation>> allIndividuals =
        state.getPopulation().getDemes().stream().flatMap(deme -> deme.getIndividuals().stream()).collect(Collectors.toList());
    System.out.println("Best individual after last generation: " + selector.select(allIndividuals));
  }

  /**
   * Setups genotype, population, algorithm instance and then with that creates
   * state instance that will be used to start everything.
   *
   * @return fully setup state instance
   */
  private State<Permutation> setupState() {
    Permutation genotype = new Permutation(PERMUTATION_SIZE);
    Population<Permutation> population = setupPopulation(genotype);
    Algorithm<Permutation> algorithm = setupAlgorithm();

    State<Permutation> state = new State<>(genotype, algorithm, population, this, this, new SystemTime());
    return state;
  }

  /**
   * Setup population and deme size and create population instance.
   *
   * @param genotype individual's genotype
   * @return fully setup population
   */
  private Population<Permutation> setupPopulation(Permutation genotype) {
    int populationSize = 1;
    int demeSize = 30;
    return new Population<>(this, genotype, populationSize, demeSize);
  }

  /**
   * Setup crossover and mutation operators, choose and create algorithm instance.
   *
   * @return fully setup algorithm instance
   */
  private Algorithm<Permutation> setupAlgorithm() {
    IRandomness random = new DefaultRandom();
    List<CrossoverOp<Permutation>> crxOperators = Arrays.asList(new PermutationCrxPMX(random),
        new PermutationCrxOX(random));
    List<MutationOp<Permutation>> mutOperators = Arrays.asList(new PermutationMutInv(random),
        new PermutationMutToggle(random), new PermutationMutInv(random), new PermutationMutRot(random));
    Crossover<Permutation> crossover = new Crossover<>(crxOperators, random);
    Mutation<Permutation> mutation = new Mutation<>(mutOperators, 0.1d, random);

    int tournamentSize = 4;
    return new SteadyStateTournament<>(mutation, crossover, tournamentSize, new SelRandomOp<Permutation>(),
          new SelWorstOp<Permutation>());
  }

  public static void main(String[] args) {
    NQueens nqueens = new NQueens();
    nqueens.start();
  }

}
