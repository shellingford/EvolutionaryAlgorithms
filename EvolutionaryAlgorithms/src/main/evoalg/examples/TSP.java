package evoalg.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import evoalg.Individual;
import evoalg.Population;
import evoalg.State;
import evoalg.SystemTime;
import evoalg.algorithm.Algorithm;
import evoalg.algorithm.IMilestone;
import evoalg.algorithm.SteadyStateTournament;
import evoalg.fitness.Fitness;
import evoalg.fitness.FitnessMin;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Crossover;
import evoalg.genotype.CrossoverOp;
import evoalg.genotype.Mutation;
import evoalg.genotype.MutationOp;
import evoalg.genotype.permutation.Permutation;
import evoalg.genotype.permutation.PermutationCrxPMX;
import evoalg.genotype.permutation.PermutationMutInv;
import evoalg.random.DefaultRandom;
import evoalg.random.IRandomness;
import evoalg.selection.SelBestOp;
import evoalg.selection.SelRandomOp;
import evoalg.selection.SelWorstOp;
import evoalg.termination.MaxDurationTermOp;
import evoalg.termination.MaxGenTermOp;
import evoalg.termination.TerminatorManager;

/**
 * Given a collection of cities and the cost of travel between each pair of them, the travelling
 * salesman problem, or TSP for short, is to find the cheapest way of visiting all of the cities
 * and returning to your starting point. <br>
 *
 * Permutation genotype is used here as an order of cities salesman needs to visit. For TSP permutation
 * genotype can be used as other different representations of the path.<p>
 *
 * Data for 29 cities taken from:
 * <a href="http://www.math.uwaterloo.ca/tsp/world/wilog.html"> http://www.math.uwaterloo.ca/tsp/world/wilog.html </a>
 */
public class TSP implements IEvaluate<Permutation>, IMilestone<Permutation> {

  private Map<String, Integer> distanceMap;
  private int permutationSize;

  private final TerminatorManager<Permutation> terminatorManager;

  public TSP() {
    terminatorManager = new TerminatorManager<Permutation>(
        new MaxGenTermOp<Permutation>(10000),
        new MaxDurationTermOp<Permutation>(2000));
  }

  @Override
  public boolean reached(Population<Permutation> population, int generationNo, long duration) {
    return terminatorManager.shouldTerminate(population, generationNo, duration);
  }

  /**
   * Calculates sum of distances between all cities specified by permutation genotype
   * and returns FitnessMin with sum value. FitnessMin is used as we want to find
   * shortest path.
   */
  @Override
  public Fitness<Permutation> evaluate(Individual<Permutation> individual) {
    List<Integer> data = individual.getGenotype().getData();
    double value = 0;
    for (int i = 0; i < data.size() - 1; i++) {
      value += distanceMap.get(distanceMapKey(data.get(i), data.get(i + 1)));
    }
    value += distanceMap.get(distanceMapKey(data.get(0), data.get(data.size() - 1)));
    return new FitnessMin<Permutation>(value);
  }

  /**
   * Creates string that is combination of two point ids as follows:
   * if id1 < id2 then resulting string is "id1,id2", otherwise "id2,id1".
   *
   * @param id1 point1 id
   * @param id2 point2 id
   * @return if id1 < id2 then "id1,id2", otherwise "id2,id1"
   */
  private String distanceMapKey(int id1, int id2) {
    if (id1 > id2) {
      return id2 + "," + id1;
    }
    else {
      return id1 + "," + id2;
    }
  }

  public void start() {
    long start = System.currentTimeMillis();
    TspFileParser parser = new TspFileParser();
    distanceMap = parser.createDistanceMap("data/tsp/wi29.tsp");
    permutationSize = parser.getPointCount();
    System.out.println("Parsing duration: " + (System.currentTimeMillis() - start) + "msec");
    System.out.println("Starting algorithm...");

    State<Permutation> state = setupState();
    start = System.currentTimeMillis();
    state.run();
    System.out.println("Duration: " + (System.currentTimeMillis() - start) + "msec");

    System.out.println("Best individual after last generation: " + getBestIndividual(state.getPopulation()));
  }

  private Individual<Permutation> getBestIndividual(Population<Permutation> population) {
    SelBestOp<Permutation> selector = new SelBestOp<Permutation>();
    List<Individual<Permutation>> allIndividuals =
        population.getDemes().stream().flatMap(deme -> deme.getIndividuals().stream()).collect(Collectors.toList());
    return selector.select(allIndividuals);
  }

  /**
   * Setups genotype, population, algorithm instance and then with that creates
   * state instance that will be used to start everything.
   *
   * @return fully setup state instance
   */
  private State<Permutation> setupState() {
    Permutation genotype = new Permutation(permutationSize);
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
    List<CrossoverOp<Permutation>> crxOperators = Arrays.asList(new PermutationCrxPMX(random));
    List<MutationOp<Permutation>> mutOperators = Arrays.asList(new PermutationMutInv(random));
    Crossover<Permutation> crossover = new Crossover<>(crxOperators, random);
    Mutation<Permutation> mutation = new Mutation<>(mutOperators, 0.1d, random);

    int tournamentSize = 5;
    return new SteadyStateTournament<>(mutation, crossover, tournamentSize, new SelRandomOp<Permutation>(),
          new SelWorstOp<Permutation>());
  }

  public static void main(String[] args) {
    TSP tsp = new TSP();
    tsp.start();
  }

}
