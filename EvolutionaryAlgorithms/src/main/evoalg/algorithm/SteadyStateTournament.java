package evoalg.algorithm;

import java.util.ArrayList;
import java.util.List;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;
import evoalg.selection.SelectionOperator;

/**
 * Steady-state tournament individual selection.
 */
public class SteadyStateTournament<T extends Genotype<T>> extends Algorithm<T> {

  private int nTournament;
  private SelectionOperator<T> selRandomOp;
  private SelectionOperator<T> selWorstOp;

  public SteadyStateTournament(Mutation<T> mutation, Crossover<T> crossover) {
    super(mutation, crossover);
  }

  @Override
  public void initialize() {
    if (nTournament < 3) {
      String msg = "Error: SteadyStateTournament algorithm requires minimum tournament size of 3!";
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public Deme<T> advanceGeneration(Deme<T> deme) {
    List<Individual<T>> tournament = new ArrayList<Individual<T>>();
    Deme<T> newDeme = new Deme<>(deme.getIevaluate(), deme.getGenotype());

    for (int i = 0; i < deme.size(); i++) {
      tournament.clear();

      for (int j = 0; j < nTournament; j++) {
        tournament.add(selRandomOp.select(deme));
      }

      Individual<T> worst = selWorstOp.select(tournament);
      removeFrom(worst, tournament);

      Individual<T> child = mate(tournament.get(0), tournament.get(1));
      child = mutate(child);
      child = child.evaluate();
      newDeme.add(child);
    }

    return newDeme;
  }
}
