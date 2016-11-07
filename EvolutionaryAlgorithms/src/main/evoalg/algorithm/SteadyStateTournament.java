package evoalg.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;
import evoalg.selection.SelRandomOp;
import evoalg.selection.SelWorstOp;
import evoalg.selection.ISelectionOperator;

/**
 * Steady-state tournament individual selection.
 *
 * @param <T> genotype
 */
public class SteadyStateTournament<T extends Genotype<T>> extends Algorithm<T> {

  private final int tournamentSize;
  private final ISelectionOperator<T> selRandomOp;
  private final ISelectionOperator<T> selWorstOp;

  public SteadyStateTournament(Mutation<T> mutation, Crossover<T> crossover, int tournamentSize) {
    super(mutation, crossover);
    this.tournamentSize = tournamentSize;
    selRandomOp = new SelRandomOp<T>();
    selWorstOp = new SelWorstOp<T>();
  }

  @Override
  public void initialize() {
    if (tournamentSize < 3) {
      String msg = "Error: SteadyStateTournament algorithm requires minimum tournament size of 3!";
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public Deme<T> advanceGeneration(Deme<T> deme) {
    List<Individual<T>> tournament = null;
    List<Individual<T>> children = new ArrayList<>();

    for (int i = 0; i < deme.size(); i++) {
      tournament = selectIndividuals(deme);
      tournament = removeWorst(tournament);

      Individual<T> child = createChild(tournament);
      children.add(child);
    }

    return new Deme<>(children);
  }

  /**
   * Selects randomly nTournament individuals from deme.
   *
   * @param deme deme
   * @return nTournament random individuals
   */
  private List<Individual<T>> selectIndividuals(Deme<T> deme) {
    return IntStream.range(0, tournamentSize).mapToObj(i -> selRandomOp.select(deme)).collect(Collectors.toList());
  }

  /**
   * Removes worst individual from the tournament pool.
   *
   * @param tournament tournament pool of individuals
   * @return tournament pool without the worst individual
   */
  private List<Individual<T>> removeWorst(List<Individual<T>> tournament) {
    Individual<T> worst = selWorstOp.select(tournament);
    tournament.remove(worst);
    return tournament;
  }

  /**
   * Mates two best individuals from tournament pool to get a child, that we then
   * mutate, evaluate and return.
   *
   * @param tournament tournament pool of individuals
   * @return new child
   */
  private Individual<T> createChild(List<Individual<T>> tournament) {
    Individual<T> child = mate(tournament.get(0), tournament.get(1));
    child = mutate(child);
    child = child.evaluate();
    return child;
  }
}
