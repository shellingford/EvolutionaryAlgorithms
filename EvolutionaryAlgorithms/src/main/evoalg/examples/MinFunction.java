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
import evoalg.algorithm.RouletTeWheelSelection;
import evoalg.fitness.Fitness;
import evoalg.fitness.FitnessMin;
import evoalg.fitness.IEvaluate;
import evoalg.genotype.Crossover;
import evoalg.genotype.CrossoverOp;
import evoalg.genotype.Mutation;
import evoalg.genotype.MutationOp;
import evoalg.genotype.floatingpoint.FloatingPoint;
import evoalg.genotype.floatingpoint.FloatingPointCrsOnePoint;
import evoalg.genotype.floatingpoint.FloatingPointMutSimple;
import evoalg.random.DefaultRandom;
import evoalg.random.IRandomness;
import evoalg.selection.SelBestOp;

/**
 * Simple example for FloatingPoint genotype where the goal is to get
 * some function's min value.
 */
public class MinFunction implements IEvaluate<FloatingPoint>, IMilestone<FloatingPoint> {
  private static final int FLOATING_POINT_SIZE = 5;
  private static final double FP_MIN = -5.12;
  private static final double FP_MAX = 5.12;

  @Override
  public boolean reached(Population<FloatingPoint> population, int generationNo, long duration) {
    return generationNo > 3000;
  }

  @Override
  public Fitness<FloatingPoint> evaluate(Individual<FloatingPoint> individual) {
    //Rastrigin's function https://en.wikipedia.org/wiki/Rastrigin_function
    double value = 10 * individual.getGenotype().size() +
                        individual.getGenotype().getData().stream()
                              .reduce(0d, (bit, fitness) -> bit * bit - 10 * Math.cos(2 * Math.PI * bit));
    return new FitnessMin<FloatingPoint>(value);
  }

  public void start() {
    State<FloatingPoint> state = setupState();
    long start = System.currentTimeMillis();
    state.run();
    System.out.println("Duration: " + (System.currentTimeMillis() - start));

    SelBestOp<FloatingPoint> selector = new SelBestOp<FloatingPoint>();
    List<Individual<FloatingPoint>> allIndividuals =
        state.getPopulation().getDemes().stream().flatMap(deme -> deme.getIndividuals().stream()).collect(Collectors.toList());
    System.out.println("Best individual after last generation: " + selector.select(allIndividuals));
  }

  /**
   * Setups genotype, population, algorithm instance and then with that creates
   * state instance that will be used to start everything.
   *
   * @return fully setup state instance
   */
  private State<FloatingPoint> setupState() {
    FloatingPoint genotype = new FloatingPoint(FLOATING_POINT_SIZE, FP_MIN, FP_MAX);
    Population<FloatingPoint> population = setupPopulation(genotype);
    Algorithm<FloatingPoint> algorithm = setupAlgorithm();

    State<FloatingPoint> state = new State<>(genotype, algorithm, population, this, this, new SystemTime());
    return state;
  }

  /**
   * Setup population and deme size and create population instance.
   *
   * @param genotype individual's genotype
   * @return fully setup population
   */
  private Population<FloatingPoint> setupPopulation(FloatingPoint genotype) {
    int populationSize = 1;
    int demeSize = 10;
    return new Population<>(this, genotype, populationSize, demeSize);
  }

  /**
   * Setup crossover and mutation operators, choose and create algorithm instance.
   *
   * @return fully setup algorithm instance
   */
  private Algorithm<FloatingPoint> setupAlgorithm() {
    IRandomness random = new DefaultRandom();
    List<CrossoverOp<FloatingPoint>> crxOperators = Arrays.asList(new FloatingPointCrsOnePoint(random));
    List<MutationOp<FloatingPoint>> mutOperators = Arrays.asList(new FloatingPointMutSimple(random));
    Crossover<FloatingPoint> crossover = new Crossover<>(crxOperators, random);
    Mutation<FloatingPoint> mutation = new Mutation<>(mutOperators, 0.9d, random);

    return new RouletTeWheelSelection<FloatingPoint>(mutation, crossover, random);
  }

  public static void main(String[] args) {
    MinFunction minFunction = new MinFunction();
    minFunction.start();
  }

}
